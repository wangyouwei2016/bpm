package com.dstz.sys.rest.model.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jinxia.hou
 * @Name TestHolidayConfDTO
 * @description:
 * @date 2022/3/2317:49
 */
public class TestHolidayConfDTO implements Serializable {
    private static final long serialVersionUID = -8791184722892342808L;
    /**
     * 开始时间
     */
    private Date startDay;

    /**
     * n 天
     */
    private Integer day;

    public Date getStartDay() {
        return startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }
}
