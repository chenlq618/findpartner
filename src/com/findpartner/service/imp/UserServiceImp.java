package com.findpartner.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.findpartner.bean.BaseUserInfo;
import com.findpartner.bean.University;
import com.findpartner.bean.UserInfo;
import com.findpartner.dao.UserDao;
import com.findpartner.service.UserService;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	UserDao userDao;
	
	public void inserUser(UserInfo user) {
		userDao.inserUser(user);
	}

	public void updateUserInfo(UserInfo user) {
		userDao.updateUserInfo(user);
	}

	public UserInfo getUserInfo(String phone) {
		return userDao.getUserInfo(phone);
	}
	
	public List<University> getSchoolInfo(String universityId){
		return userDao.getSchoolInfo(universityId);
	}
	
	public BaseUserInfo getBaseUserInfo(String phone){
		return userDao.getBaseUserInfo(phone);
	}

}
