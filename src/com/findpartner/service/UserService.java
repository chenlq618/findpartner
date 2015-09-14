package com.findpartner.service;

import java.util.List;

import com.findpartner.bean.University;
import com.findpartner.bean.UserInfo;

public interface UserService {
	public void inserUser(UserInfo user);// 插入用户数据

	public void updateUserInfo(UserInfo user);// 修改用户资料

	public UserInfo getUserInfo(String phone);// 查询用户信息
	public List<University> getSchoolInfo(String universityId);
}
