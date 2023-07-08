package com.dstz.base.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.dstz.base.common.utils.ToStringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.hutool.core.util.StrUtil;

/**
 * <pre>
 * 根据ab特性优化后的baomidou的Model类
 * 自带id主键属性，自带toString方法
 * </pre>
 *
 * @param <T>
 * @author aschs
 * @date 2022年2月22日
 * @owner 深圳市大世同舟信息科技有限公司
 */
public abstract class AbModel<T extends AbModel<?>> extends Model<T> implements IPersistentEntity {

    @Override
    public String toString() {
        return ToStringUtils.toString(this);
    }
    
    /**
     * <pre>
     * 判断id是否为空
     * </pre>	
     * @return
     */
    @JsonIgnore
    public boolean isIdEmpty() {
    	return StrUtil.isEmpty(this.getId());
    }
    
    /**
     * <pre>
     * 判断id是否为非空
     * </pre>	
     * @return
     */
    @JsonIgnore
    public boolean isIdNotEmpty() {
    	return StrUtil.isNotEmpty(this.getId());
    }
}
