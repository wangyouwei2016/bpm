package com.dstz.base.mapper;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.dstz.base.mapper.methods.AbBulkCreate;
import com.dstz.base.mapper.methods.AbQuery;
import com.dstz.base.mapper.methods.AbQueryCursorByWrapper;
import com.dstz.base.mapper.methods.AbUpdateFullById;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * 覆盖mybatis-plus的每个mapper植入方法的时机，给每个mapper植入对应的query方法
 * </pre>
 * @author aschs
 * @date 2022年3月14日
 * @owner 深圳市大世同舟信息科技有限公司
 */
@Service
public class AbSqlInjector extends DefaultSqlInjector {
	@Override
	public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
		List<AbstractMethod> methods = super.getMethodList(mapperClass, tableInfo);
		methods.add(new AbQuery());//增加AB的query方法进入mapper
		// 增加批量创建
		methods.add(new AbBulkCreate());
		// 增加 根据ID完整性更新
		methods.add(new AbUpdateFullById());
		// 增加 根据查询条件获取游标
		methods.add(new AbQueryCursorByWrapper());
		return methods;
	}
}
