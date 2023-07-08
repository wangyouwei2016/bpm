package com.dstz.springboot.autoconfigure.idgen;


import cn.hutool.core.date.SystemClock;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;

/**
 * ID生成器配置
 *
 * @author wacxhs
 */
@ConfigurationProperties(prefix = "ab.id-generator")
public class IdGeneratorProperties {

    /**
     * 生成器类型
     */
    private IdGeneratorType type = IdGeneratorType.SNOWFLAKE;

    /**
     * 雪花算法配置
     */
    private Snowflake snowflake = new Snowflake();

    public IdGeneratorType getType() {
        return type;
    }

    public void setType(IdGeneratorType type) {
        this.type = type;
    }

    public Snowflake getSnowflake() {
        return snowflake;
    }

    public void setSnowflake(Snowflake snowflake) {
        this.snowflake = snowflake;
    }

    public static class Snowflake {

        /**
         * 初始化时间起点（null表示默认起始日期）,后期修改会导致id重复,如果要修改连workerId dataCenterId，慎用
         */
        private Date epochDate;

        /**
         * 数据中心ID
         */
        private Long dataCenterId;

        /**
         * 工作机器节点ID
         */
        private Long workerId;

        /**
         * 是否使用{@link SystemClock} 获取当前时间戳
         */
        private Boolean isUseSystemClock = Boolean.FALSE;

        /**
         * 默认回拨时间，2S
         */
        private Long timeOffset = cn.hutool.core.lang.Snowflake.DEFAULT_TIME_OFFSET;

        public Date getEpochDate() {
            return epochDate;
        }

        public void setEpochDate(Date epochDate) {
            this.epochDate = epochDate;
        }

        public Long getDataCenterId() {
            return dataCenterId;
        }

        public void setDataCenterId(Long dataCenterId) {
            this.dataCenterId = dataCenterId;
        }

        public Long getWorkerId() {
            return workerId;
        }

        public void setWorkerId(Long workerId) {
            this.workerId = workerId;
        }

        public Boolean getUseSystemClock() {
            return isUseSystemClock;
        }

        public void setUseSystemClock(Boolean useSystemClock) {
            isUseSystemClock = useSystemClock;
        }

        public Long getTimeOffset() {
            return timeOffset;
        }

        public void setTimeOffset(Long timeOffset) {
            this.timeOffset = timeOffset;
        }
    }
}
