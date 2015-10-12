package com.findpartner.dao;

import java.util.List;

import com.findpartner.bean.BaseUserInfo;
import com.findpartner.bean.University;
import com.findpartner.bean.UserInfo;



public interface UserDao {
	

	public void inserUser(UserInfo user);// 插入用户数据
	
	public void updateUserInfo(UserInfo user);// 修改用户资料

	public UserInfo getUserInfo(String phone);// 查询用户信息
	
	public List<University> getSchoolInfo(String universityId);//查询学校学院专业等信息
	
	public BaseUserInfo getBaseUserInfo(String phone);
}
