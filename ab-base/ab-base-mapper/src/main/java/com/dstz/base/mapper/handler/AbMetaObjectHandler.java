package com.dstz.base.mapper.handler;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.dstz.base.common.utils.UserContextUtils;
import com.dstz.org.api.model.IGroup;
import com.dstz.org.api.model.IUser;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 业务实体通用字段处理器
 *
 * @author wacxhs
 */
@Component
public class AbMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		Optional<IUser> currentUser = UserContextUtils.getUser();
		Optional<IGroup> currentGroup = UserContextUtils.getGroup();
		// 创建者ID
		this.strictInsertFill(metaObject, "createBy", () -> currentUser.map(IUser::getUserId).orElse(StrUtil.EMPTY), String.class);
		// 创建者姓名
		this.strictInsertFill(metaObject, "creator", () -> currentUser.map(IUser::getFullName).orElse(StrUtil.EMPTY), String.class);
		// 创建时间
		this.strictInsertFill(metaObject, "createTime", Date::new, Date.class);
		// 创建着所属组织ID
		this.strictInsertFill(metaObject, "createOrgId", () -> currentGroup.map(IGroup::getGroupId).orElse(StrUtil.EMPTY), String.class);
		updateFill(metaObject, currentUser);
		// 乐关锁默认字段
		TableInfo tableInfo = TableInfoHelper.getTableInfo(AopUtils.getTargetClass(metaObject.getOriginalObject()));
		TableFieldInfo versionFieldInfo = tableInfo.getVersionFieldInfo();
		if (versionFieldInfo != null) {
			metaObject.setValue(versionFieldInfo.getField().getName(), getTypeDefaultValue(versionFieldInfo.getPropertyType()));
		}
	}

	private void updateFill(MetaObject metaObject, Optional<IUser> currentUser) {
		// 更新者ID
		this.strictUpdateFill(metaObject, "updateBy", () -> currentUser.map(IUser::getUserId).orElse(StrUtil.EMPTY), String.class);
		// 更新者
		this.strictUpdateFill(metaObject, "updater", () -> currentUser.map(IUser::getFullName).orElse(StrUtil.EMPTY), String.class);
		// 更新时间
		this.strictUpdateFill(metaObject, "updateTime", Date::new, Date.class);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		Optional<IUser> currentUser = UserContextUtils.getUser();
		updateFill(metaObject, currentUser);
	}
	
	@Override
	public MetaObjectHandler strictFillStrategy(MetaObject metaObject, String fieldName, Supplier<?> fieldVal) {
		Object obj = fieldVal.get();
		if (Objects.nonNull(obj)) {
			metaObject.setValue(fieldName, obj);
		}
		return this;
	}

	private Object getTypeDefaultValue(Class<?> typeClass) {
		if (ClassUtil.isBasicType(typeClass) || ClassUtil.isAssignable(BigDecimal.class, typeClass) || ClassUtil.isAssignable(BigInteger.class, typeClass)) {
			return Convert.convert(typeClass, "0");
		} else if (ClassUtil.isAssignable(java.sql.Date.class, typeClass)) {
			return new java.sql.Date(System.currentTimeMillis());
		} else if (ClassUtil.isAssignable(java.sql.Timestamp.class, typeClass)) {
			return new java.sql.Timestamp(System.currentTimeMillis());
		} else if (ClassUtil.isAssignable(Date.class, typeClass)) {
			return new Date();
		}
		throw new UnsupportedOperationException(String.format("类型(%s)不支持默认值", typeClass.getName()));
	}
}
