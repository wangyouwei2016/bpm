package com.dstz.sys.api;

import com.dstz.sys.api.vo.WorkCalendarVO;

import java.util.Date;
import java.util.List;

/**
 * @author jinxia.hou
 * @Name WorkCalendarApi
 * @description: 工作日历接口
 * @date 2022/3/1016:25
 */
public interface WorkCalendarApi {

    /**
     * 获取某一天日历信息
     * 可以判断是否为工作日，日历详情
     *
     * @param day
     * @return
     */
    WorkCalendarVO getWorkCalendarByDay(Date day);

    /**
     * 取某一天日历信息
     *
     * @param day
     * @param system
     * @return
     */
    WorkCalendarVO getWorkCalendarByDay(Date day, String system);


    /**
     * 通过时间区间返回
     *
     * @param startDay
     * @param endDay
     * @return
     */
    List<WorkCalendarVO> getWorkCalendars(Date startDay, Date endDay);

    /**
     * 通过时间区间返回
     *
     * @param startDay
     * @param endDay
     * @param system
     * @return
     */
    List<WorkCalendarVO> getWorkCalendars(Date startDay, Date endDay, String system);

    /**
     * 获取指定工作日，N天数后的工作日期
     *
     * @param startDay
     * @param days
     * @return
     */
    Date getEndWorkDay(Date startDay, int days);

    /**
     * 获取指定工作日，N天数后的工作日期
     *
     * @param startDay
     * @param days
     * @param system
     * @return
     */
    Date getEndWorkDay(Date startDay, int days, String system);

    /**
     * 获取多少分钟后的天数
     *
     * @param startDay
     * @param minute
     * @return
     */
    Date getEndWorkDayByMinute(Date startDay, int minute);
}
