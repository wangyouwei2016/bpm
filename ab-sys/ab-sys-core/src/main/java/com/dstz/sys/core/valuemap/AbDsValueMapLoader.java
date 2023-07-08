package com.dstz.sys.core.valuemap;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.CharSequenceUtil;
import com.dstz.base.common.valuemap.AbValueMap;
import com.dstz.base.common.valuemap.AbValueMapLoader;
import com.dstz.base.common.valuemap.AbValueMapLoaderProvider;
import com.dstz.base.common.valuemap.AbValueMapType;
import com.dstz.base.query.impl.DefaultAbQueryFilter;
import com.dstz.sys.core.manager.SysDataSourceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 系统数据源属性映射器
 * </pre>
 * 
 * @author aschs
 * @date 2022年4月6日
 * @owner 深圳市大世同舟信息科技有限公司
 */
@Component
public class AbDsValueMapLoader implements AbValueMapLoader<String, Object> {
	private static final String DEFAULT_ATTR_NAME = "alias";

	@Autowired
	private SysDataSourceManager sysDataSourceManager;

	public AbDsValueMapLoader() {
		AbValueMapLoaderProvider.register(AbValueMapType.SYS_DATA_SOURCE, this);
	}

	@Override
	public Map<String, Object> loading(AbValueMap abValueMap, Collection<String> mapKeys) {
		List<Object> rows = sysDataSourceManager.query(new DefaultAbQueryFilter()).getRows();
		final String attrName = CharSequenceUtil.emptyToDefault(abValueMap.matchField(), DEFAULT_ATTR_NAME);
		return CollUtil.toMap(rows, null, o -> Convert.toStr(BeanUtil.getFieldValue(o, attrName)));
	}
}
