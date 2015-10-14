package com.findpartner.bean;

import java.io.Serializable;

public class MemberInfo implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private String phone;
	private String nickname;
	private String userIcon;
	private int sex;
	private String selIntroduce;
	private int role;

	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUserIcon() {
		return userIcon;
	}
	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getSelIntroduce() {
		return selIntroduce;
	}
	public void setSelIntroduce(String selIntroduce) {
		this.selIntroduce = selIntroduce;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


}
