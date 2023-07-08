package com.dstz.base.entity;

import java.util.Date;

/**
 * 持久化实体，所有实体应实现
 *
 * @author wacxhs
 * @since 2022-01-20
 */
public interface IPersistentEntity {

    /**
     * 设置ID
     *
     * @param id ID
     */
    void setId(String id);

    /**
     * 获取ID
     *
     * @return ID
     */
    String getId();

    /**
     * 设置创建者ID
     *
     * @param createBy 创建者ID
     */
    default void setCreateBy(String createBy) {
    }

    /**
     * 获取创建者ID
     *
     * @return 创建者ID
     */
    default String getCreateBy() {
        return null;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    default void setCreateTime(Date createTime) {
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    default Date getCreateTime() {
        return null;
    }

    /**
     * 设置更新者ID
     *
     * @param updateBy 更新者ID
     */
    default void setUpdateBy(String updateBy) {
    }

    /**
     * 获取更新者ID
     *
     * @return 更新者ID
     */
    default String getUpdateBy() {
        return null;
    }

    /**
     * 设置更新
     *
     * @param updater 更新者
     */
    default void setUpdater(String updater) {
    }

    /**
     * 获取更新者
     *
     * @return 更新者
     */
    default String getUpdater() {
        return null;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    default void setUpdateTime(Date updateTime) {
    }

    /**
     * 获取更新时间
     *
     * @return 更新时间
     */
    default Date getUpdateTime() {
        return null;
    }


    /**
     * 设置乐观锁版本号
     *
     * @param rev 乐观锁版本号
     */
    default void setRev(Integer rev) {
    }

    /**
     * 获取乐观锁版本号
     *
     * @return 乐观锁版本号
     */
    default Integer getRev() {
        return null;
    }
}
