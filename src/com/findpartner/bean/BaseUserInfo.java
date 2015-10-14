package com.findpartner.bean;

import java.io.Serializable;

public class BaseUserInfo implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private String phone;
	private String nickName;
	private String userIcon;
	private String selIntroduce;
	private int sex;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUserIcon() {
		return userIcon;
	}
	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}
	public String getSelIntroduce() {
		return selIntroduce;
	}
	public void setSelIntroduce(String selIntroduce) {
		this.selIntroduce = selIntroduce;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
}
