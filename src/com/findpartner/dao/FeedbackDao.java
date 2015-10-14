package com.findpartner.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.findpartner.bean.Feedback;

public interface FeedbackDao {
	
	public void insert(Feedback feedback);
	//public Feedback get(String a,String b);
	public Map select(@Param("id") Integer id, @Param("userId")String userId);
}
