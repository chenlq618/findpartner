package com.findpartner.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized.Parameter;

import com.findpartner.bean.PushInfo;

public interface NotificationDao {
	public List<PushInfo> getNotification(@Param("phone") String phone,@Param("start") int start, @Param("size") int size);
	
	public void insertNotification(PushInfo pushInfo);
	
	public void dismissNotification(List<PushInfo> menbers);
	
	public void updateState(@Param("id") int id,@Param("state") int state);
}
