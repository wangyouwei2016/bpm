package com.dstz.base.common.valuemap;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.SimpleCache;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.stream.StreamUtil;
import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.dstz.base.common.async.ContextCleanTaskDecorator;
import com.dstz.base.common.async.ContextDuplicationTaskDecorator;
import com.dstz.base.common.utils.TaskDecoratorUtils;
import com.dstz.base.common.utils.CastUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 注解处理器
 *
 * @author wacxhs
 */
public class AbValueMapAnnotationProcessor {

	private static final Logger logger = LoggerFactory.getLogger(AbValueMapAnnotationProcessor.class);

	/**
	 * 类注解字段缓存
	 */
	private final SimpleCache<Class<?>, List<BeanPropertyWriter>> classAnnotationFieldCache = new SimpleCache<>();

	private volatile ThreadPoolTaskExecutor threadPoolTaskExecutor;

	public static AbValueMapAnnotationProcessor getInstance() {
		return Holder.INSTANCE;
	}


	/**
	 * 获取线程任务池
	 *
	 * @return 线程任务池
	 */
	private ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
		if (threadPoolTaskExecutor == null) {
			synchronized (this) {
				if (threadPoolTaskExecutor == null) {
					ThreadPoolTaskExecutor localThreadPoolTaskExecutor = new ThreadPoolTaskExecutor();
					localThreadPoolTaskExecutor.setKeepAliveSeconds(30);
					localThreadPoolTaskExecutor.setMaxPoolSize(Math.max(Math.round(Runtime.getRuntime().availableProcessors() / 0.7f), 10));
					localThreadPoolTaskExecutor.setTaskDecorator(TaskDecoratorUtils.buildChainTaskDecorator(ContextCleanTaskDecorator.INSTANCE, ContextDuplicationTaskDecorator.INSTANCE));
					localThreadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
					localThreadPoolTaskExecutor.setThreadFactory(new NamedThreadFactory("ab-valuemap-", true));
					localThreadPoolTaskExecutor.initialize();
					this.threadPoolTaskExecutor = localThreadPoolTaskExecutor;
				}
			}
		}
		return threadPoolTaskExecutor;
	}


	private Map<Object, Object> loadMappedValue(Collection<Object> mapKeys, AbValueMap abValueMap) {
		return ObjectUtil.defaultIfNull(AbValueMapLoaderProvider.findAbValueMapLoader(abValueMap).loading(abValueMap, mapKeys), Collections.emptyMap());
	}

	public void prepareContainerMapValue(SerializerProvider serializerProvider, Object value) {
		Map<PropertyWriter, Collection<Object>> fieldGroupValues = Collections.emptyMap();
		if (value instanceof Iterable) {
			Iterable<Object> iterable = CastUtils.cast(value);
			fieldGroupValues = extractFieldGroupValues(StreamUtil.of(iterable, true), serializerProvider);
		} else if (value instanceof Map) {
			Map<Object, Object> map = CastUtils.cast(value);
			fieldGroupValues = extractFieldGroupValues(map.entrySet().parallelStream(), serializerProvider);
		} else if (ArrayUtil.isArray(value)) {
			fieldGroupValues = extractFieldGroupValues(Arrays.stream((Object[]) value).parallel(), serializerProvider);
		}
		if (MapUtil.isNotEmpty(fieldGroupValues)) {
			Map<String, Map<Object, Object>> fieldValueMap;
			if (fieldGroupValues.size() > 1) {
				// 利用多线程并行加载提升速度
				fieldValueMap = new ConcurrentHashMap<>((int) (fieldGroupValues.size() / MapUtil.DEFAULT_LOAD_FACTOR + 1));
				CountDownLatch countDownLatch = new CountDownLatch(fieldGroupValues.size());
				for (Map.Entry<PropertyWriter, Collection<Object>> entry : fieldGroupValues.entrySet()) {
					final AbValueMap abValueMap = entry.getKey().getAnnotation(AbValueMap.class);
					getThreadPoolTaskExecutor().submit(() -> {
						try {
							fieldValueMap.put(entry.getKey().getName(), loadMappedValue(entry.getValue(), abValueMap));
						} finally {
							countDownLatch.countDown();
						}
					});
				}
				// 等待所有线程完成
				try {
					countDownLatch.await();
				} catch (InterruptedException e) {
					ExceptionUtil.wrapAndThrow(e);
				}
			} else {
				Map.Entry<PropertyWriter, Collection<Object>> entry = CollUtil.getFirst(fieldGroupValues.entrySet());
				AbValueMap abValueMap = entry.getKey().getAnnotation(AbValueMap.class);
				fieldValueMap = ImmutableMap.of(entry.getKey().getName(), loadMappedValue(entry.getValue(), abValueMap));
			}
			pushStack(serializerProvider, fieldValueMap);
		} else {
			pushStack(serializerProvider, null);
		}
	}

	private void pushStack(SerializerProvider serializerProvider, Map<String, Map<Object, Object>> fieldValueMap) {
		LinkedList<Map<String, Map<Object, Object>>> stack = CastUtils.cast(serializerProvider.getAttribute(AbValueMap.class));
		if (stack == null) {
			serializerProvider.setAttribute(AbValueMap.class, stack = new LinkedList<>());
		}
		stack.push(fieldValueMap);
	}

	private LinkedList<Map<String, Map<Object, Object>>> stack(SerializerProvider serializerProvider) {
		return CastUtils.cast(serializerProvider.getAttribute(AbValueMap.class));
	}

	public void cleanContainerMapValue(SerializerProvider serializerProvider) {
		LinkedList<?> stack = stack(serializerProvider);
		if (CollUtil.isNotEmpty(stack)) {
			stack.poll();
		}
	}

	public void serialFieldMapped(Object pojo, JsonGenerator gen, SerializerProvider serializerProvider, PropertyWriter writer) throws Exception {
		// 无注解字段直接跳过
		if (writer.getAnnotation(AbValueMap.class) == null) {
			return;
		}
		AbValueMap abValueMap = writer.getAnnotation(AbValueMap.class);
		if (abValueMap == null) {
			return;
		}
		Object mappedObject = getPropertyMappedValue(pojo, serializerProvider, abValueMap, (BeanPropertyWriter) writer);
		if (mappedObject == null) {
			return;
		}
		writeMappedFieldValue(gen, writer.getName(), abValueMap, mappedObject);
	}

	private Object getPropertyMappedValue(Object pojo, SerializerProvider serializerProvider, AbValueMap abValueMap, BeanPropertyWriter propertyWriter) throws Exception {
		Object fieldValue = propertyWriter.get(pojo);
		// 字段值为空跳过
		if (fieldValue == null) {
			return null;
		}
		Map<Object, Object> valueMappedMap;
		Map<String, Map<Object, Object>> filedGroupValueMap = CollUtil.getFirst(stack(serializerProvider));
		// 上层来源不是容器
		if (filedGroupValueMap == null) {
			valueMappedMap = loadMappedValue(Collections.singleton(fieldValue), abValueMap);
		} else {
			valueMappedMap = filedGroupValueMap.get(propertyWriter.getName());
		}
		return CollUtil.isEmpty(valueMappedMap) ? null : valueMappedMap.get(fieldValue);
	}

	private void writeMappedFieldValue(JsonGenerator gen, String fieldNamePrefix, AbValueMap abValueMap, Object mappedObject) throws IOException {
		// map 类型数据处理
		if (mappedObject instanceof Map) {
			Map<String, Object> mappedMap = CastUtils.cast(mappedObject);
			if (ArrayUtil.isEmpty(abValueMap.attrMap())) {
				for (Map.Entry<String, Object> entry : mappedMap.entrySet()) {
					gen.writeFieldName(fieldNamePrefix + StrUtil.upperFirst(entry.getKey()));
					gen.writeObject(entry.getValue());
				}
			} else {
				for (AbValueMap.AttrMap attrMap : abValueMap.attrMap()) {
					gen.writeFieldName(StrUtil.isNotEmpty(attrMap.targetName()) ? attrMap.targetName() : fieldNamePrefix + StrUtil.upperFirst(attrMap.originName()));
					gen.writeObject(mappedMap.get(attrMap.originName()));
				}
			}
		} else {
			Class<?> mappedObjectClass = AopUtils.getTargetClass(mappedObject);
			if (ArrayUtil.isEmpty(abValueMap.attrMap())) {
				for (PropertyDescriptor propertyDescriptor : BeanUtil.getPropertyDescriptors(mappedObjectClass)) {
					Method readMethod = propertyDescriptor.getReadMethod();
					if (readMethod != null) {
						gen.writeFieldName(fieldNamePrefix + StrUtil.upperFirst(propertyDescriptor.getName()));
						gen.writeObject(ReflectUtil.invoke(mappedObject, readMethod));
					}
				}
			} else {
				Map<String, PropertyDescriptor> propertyDescriptorMap = BeanUtil.getPropertyDescriptorMap(mappedObjectClass, false);
				for (AbValueMap.AttrMap attrMap : abValueMap.attrMap()) {
					PropertyDescriptor propertyDescriptor = propertyDescriptorMap.get(attrMap.originName());
					Method readMethod = propertyDescriptor != null ? propertyDescriptor.getReadMethod() : null;
					if (readMethod != null) {
						gen.writeFieldName(StrUtil.isNotEmpty(attrMap.targetName()) ? attrMap.targetName() : fieldNamePrefix + StrUtil.upperFirst(attrMap.originName()));
						gen.writeObject(ReflectUtil.invoke(mappedObject, readMethod));
					}
				}
			}
		}
	}

	private Map<PropertyWriter, Collection<Object>> extractFieldGroupValues(Stream<?> stream, SerializerProvider serializerProvider) {
		Map<PropertyWriter, Collection<Object>> fieldGroupValues = MapUtil.newConcurrentHashMap();
		stream.forEach(obj -> {
			Class<Object> typeClass = ClassUtil.getClass(obj);
			for (BeanPropertyWriter propertyWriter : getAnnotationFields(serializerProvider, typeClass)) {
				try {
					Object fieldValue = propertyWriter.get(obj);
					if (fieldValue != null) {
						fieldGroupValues.computeIfAbsent(propertyWriter, k -> new ConcurrentHashSet<>()).add(fieldValue);
					}
				} catch (Exception e) {
					throw new UndeclaredThrowableException(e);
				}
			}
		});
		return fieldGroupValues;
	}

	private List<BeanPropertyWriter> getAnnotationFields(SerializerProvider serializerProvider, Class<?> typeClass) {
		if (typeClass == null) {
			return Collections.emptyList();
		}
		return classAnnotationFieldCache.get(typeClass, () -> {
			try {
				JsonSerializer<Object> typedValueSerializer = serializerProvider.findTypedValueSerializer(typeClass, true, null);
				if (typedValueSerializer instanceof BeanSerializer) {
					return StreamUtil.of(IterUtil.asIterable(typedValueSerializer.properties()))
					                 .filter(propertyWriter -> propertyWriter instanceof BeanPropertyWriter && Objects.nonNull(propertyWriter.getAnnotation(AbValueMap.class)))
					                 .map(BeanPropertyWriter.class::cast)
					                 .collect(Collectors.toList());
				}
			} catch (JsonMappingException ex) {
				logger.error(ex.getMessage(), ex);
			}
			return Collections.emptyList();
		});
	}

	private interface Holder {
		AbValueMapAnnotationProcessor INSTANCE = new AbValueMapAnnotationProcessor();
	}

}
