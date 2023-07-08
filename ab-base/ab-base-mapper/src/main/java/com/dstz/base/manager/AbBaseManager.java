package com.dstz.base.manager;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.base.entity.IPersistentEntity;
import com.dstz.base.query.AbQueryFilter;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * AB 通用业务处理，所有通用业务处理应实现
 *
 * @param <T> 实体类型
 * @author wacxhs
 * @since 2022-01-24
 */
public interface AbBaseManager<T extends IPersistentEntity> {

    /**
     * 批量创建默认大小
     */
    int DEFAULT_BULK_CRETE_SIZE = 1000;
    
    /**
     * 创建实体对象
     *
     * @param entity 实体
     * @return 影响行数
     */
    int create(T entity);

    /**
     * 按实体ID更新实体对象
     *
     * @param entity 实体
     * @return 影响行数
     */
    int update(T entity);

    /**
     * 按实体ID更新完整更新实例对象，对实体对象字段不做判空
     *
     * @param entity 实体
     * @return 影响行数
     */
    int updateFullById(T entity);

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity  实体对象 (set 条件值,可以为 null)
     * @param wrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     * @return 影响行数
     */
    int update(T entity, Wrapper<T> wrapper);

    /**
     * 创建或修改，如果实体ID存修改，不存在创建新记录
     *
     * @param entity 实体
     * @return 影响行数
     */
    int createOrUpdate(T entity);
    
    /**
     * 分页列表
     *
     * @param queryFilter  分页
     * @param  
     * @return 分页列表 
     */
    PageListDTO<T> query(AbQueryFilter queryFilter);

    /**
     * 分页列表
     *
     * @param baseQuery  分页查询条件
     * @param wrapper 构造条件
     * @return 分页列表
     */
     PageListDTO<T> query(QueryParamDTO baseQuery, Wrapper<T> wrapper);

    /**
     * 分页列表
     *
     * @param baseQuery  分页查询条件
     * @param wrapper 构造条件
     * @param clazz 返回的VO或DTO对象
     * @return 分页列表
     */
     <V> PageListDTO<V> query(QueryParamDTO baseQuery, Wrapper<T> wrapper, Class<V> clazz);
    /**
     * 按实体ID获取实体
     *
     * @param id 实体ID
     * @return 实体记录
     */
    T getById(Serializable id);

    /**
     * 按实体ID集获取实体
     *
     * @param ids 实体ID集
     * @return 实体记录
     */
    List<T> selectByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 entity 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return 实体记录
     */
    List<T> selectByWrapper(Wrapper<T> queryWrapper);

    /**
     * 分页列表
     *
     * @param page         分页对象
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return 分页信息
     */
    IPage<T> selectByPage(IPage<T> page, Wrapper<T> queryWrapper);

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return 记录数
     */
    Long selectCount(Wrapper<T> queryWrapper);


    /**
     * 根据 entity 条件，删除记录
     *
     * @param wrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     * @return 影响行数
     */
    int remove(Wrapper<T> wrapper);

    /**
     * 按实体ID删除对象
     *
     * @param id 实体ID
     * @return 影响行数
     */
    int removeById(Serializable id);

    /**
     * 按实体ID集删除记录
     *
     * @param ids 实体ID集
     */
    int removeByIds(Collection<? extends Serializable> ids);


    /**
     * 根据 Wrapper 条件，判断是否存在记录
     *
     * @param queryWrapper 实体对象封装操作类
     * @return 是否存在记录
     */
    boolean exists(Wrapper<T> queryWrapper);


    /**
     * 查找指定条件的对象
     * @param queryWrapper 条件
     * @return 对象实体
     */
     T selectOne(Wrapper<T> queryWrapper);

    /**
     * 获取所有对象集合
     *
     * @return 对象集合
     */
     List<T> list();

    /**
     * 批量创建，请注意对象大小
     *
     * @param list 列表
     * @return 影响行数
     */
    default void bulkCreate(Iterable<T> list) {
        bulkCreate(list, DEFAULT_BULK_CRETE_SIZE);
    }

    /**
     * 批量创建，对于大对象减少批量写入大小
     *
     * @param list 列表
     * @param size 批量大小
     * @return 影响行数
     */
    void bulkCreate(Iterable<T> list, int size);

    /**
     * <pre>
     * ab系统大部分业务都有根据编码获取对象
     * ps：使用者清楚知道自己操作的model是有code_字段才能用
     * </pre>
     *
     * @param code
     * @return
     */
    T getByCode(String code);

    /**
     * <pre>
     * 业务model存在编码时进行编码唯一性校验
     * </pre>
     *
     * @param entity
     * @param code
     * @return true：通过校验|false：校验失败
     */
    boolean checkCode(T entity, String code);
}
