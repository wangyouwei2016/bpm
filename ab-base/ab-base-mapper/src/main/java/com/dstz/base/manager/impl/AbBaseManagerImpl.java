package com.dstz.base.manager.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.base.common.utils.BeanCopierUtils;
import com.dstz.base.common.utils.CastUtils;
import com.dstz.base.entity.IPersistentEntity;
import com.dstz.base.manager.AbBaseManager;
import com.dstz.base.mapper.AbBaseMapper;
import com.dstz.base.query.AbQueryFilter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * AB 通用业务处理实现，所有通用业务处理应实现
 *
 * @param <T>
 * @author wacxhs
 * @since 2022-01-24
 */
public abstract class AbBaseManagerImpl<T extends IPersistentEntity> implements AbBaseManager<T> {

    @Autowired
    private AbBaseMapper<T> abBaseMapper;

    /**
     * 获取对应 entity 的 BaseMapper
     *
     * @return BaseMapper
     */
    protected AbBaseMapper<T> getBaseMapper() {
        return abBaseMapper;
    }

    @Override
    public int create(T entity) {
        return getBaseMapper().insert(entity);
    }

    @Override
    public int update(T entity) {
        return getBaseMapper().updateById(entity);
    }

    @Override
    public int updateFullById(T entity) {
        return getBaseMapper().updateFullById(entity);
    }

    @Override
    public int update(T entity, Wrapper<T> wrapper) {
        return getBaseMapper().update(entity, wrapper);
    }

    @Override
    public int createOrUpdate(T entity) {
        return StrUtil.isEmpty(entity.getId()) ? getBaseMapper().insert(entity) : getBaseMapper().updateFullById(entity);
    }

    @Override
    public T getById(Serializable id) {
        return getBaseMapper().selectById(id);
    }
    
    @Override
    public PageListDTO<T> query(AbQueryFilter queryFilter) {
         return getBaseMapper().query(queryFilter);
    }

    @Override
    public PageListDTO<T> query(QueryParamDTO baseQuery, Wrapper<T> wrapper) {
        return convertPageDto(getBaseMapper().selectPage(new Page<>(baseQuery.getCurrentPage(), baseQuery.getPageSize()), wrapper));
    }

    @Override
    public <V> PageListDTO<V> query(QueryParamDTO baseQuery, Wrapper<T> wrapper, Class<V> clazz) {
        IPage<V> iPage = getBaseMapper().selectPage(new Page<>(baseQuery.getCurrentPage(), baseQuery.getPageSize()), wrapper)
                .convert(s -> BeanCopierUtils.transformBean(s, clazz));
        return new PageListDTO<V>(iPage.getSize(), iPage.getCurrent(), iPage.getTotal(), iPage.getRecords());
    }

    private PageListDTO<T> convertPageDto(IPage<T> page) {
        return new PageListDTO<>(page.getSize(), page.getCurrent(), page.getTotal(), page.getRecords());
    }

    @Override
    public List<T> selectByIds(Collection<? extends Serializable> ids) {
        return getBaseMapper().selectBatchIds(ids);
    }

    @Override
    public List<T> selectByWrapper(Wrapper<T> queryWrapper) {
        return getBaseMapper().selectList(queryWrapper);
    }

    @Override
    public IPage<T> selectByPage(IPage<T> page, Wrapper<T> queryWrapper) {
        return getBaseMapper().selectPage(page, queryWrapper);
    }

    @Override
    public Long selectCount(Wrapper<T> queryWrapper) {
        return getBaseMapper().selectCount(queryWrapper);
    }

    @Override
    public int remove(Wrapper<T> wrapper) {
        return getBaseMapper().delete(wrapper);
    }


    @Override
    public int removeById(Serializable id) {
        return getBaseMapper().deleteById(id);
    }


    @Override
    public int removeByIds(Collection<? extends Serializable> ids) {
        return getBaseMapper().deleteBatchIds(ids);
    }

    @Override
    public boolean exists(Wrapper<T> queryWrapper) {
        return getBaseMapper().exists(queryWrapper);
    }

    @Override
    public T selectOne(Wrapper<T> queryWrapper) {
        return getBaseMapper().selectOne(queryWrapper);
    }

    @Override
    public List<T> list() {
        return getBaseMapper().selectList(null);
    }

    @Override
    public void bulkCreate(Iterable<T> iterable, int size) {
        // 能被hutool拆分列表
        if (iterable instanceof List) {
            List<T> list = CastUtils.cast(iterable);
            if (list.size() <= size) {
                getBaseMapper().bulkCreate(iterable);
            } else {
                ListUtil.split(list, size).forEach(getBaseMapper()::bulkCreate);
            }
        } else {
            List<T> bulkList = new ArrayList<>(size);
            for (T item : iterable) {
                bulkList.add(item);
                if (bulkList.size() >= size) {
                    getBaseMapper().bulkCreate(bulkList);
                    bulkList.clear();
                }
            }
            if(CollUtil.isNotEmpty(bulkList)) {
                getBaseMapper().bulkCreate(bulkList);
            }
        }
    }

    @Override
    public T getByCode(String code) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code_", code);
        return this.selectOne(queryWrapper);
    }

    @Override
    public boolean checkCode(T entity, String code) {
        T t = getByCode(code);
        if (t != null && !t.getId().equals(entity.getId())) {
            return false;
        }
        return true;
    }
}
