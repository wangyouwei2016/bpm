package com.dstz.sys.api;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dstz.sys.api.vo.WorkCalendarVO;
@Service
public class WorkCalendarApiImpl implements WorkCalendarApi{

	@Override
	public WorkCalendarVO getWorkCalendarByDay(Date day) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkCalendarVO getWorkCalendarByDay(Date day, String system) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkCalendarVO> getWorkCalendars(Date startDay, Date endDay) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkCalendarVO> getWorkCalendars(Date startDay, Date endDay, String system) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getEndWorkDay(Date startDay, int days) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getEndWorkDay(Date startDay, int days, String system) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getEndWorkDayByMinute(Date startDay, int minute) {
		// TODO Auto-generated method stub
		return null;
	}

}
