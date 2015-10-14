package com.findpartner.bean;

import java.io.Serializable;

public class PushInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String phone;
	private String menber;
	private String menberNickName;
	private String menberIcon;
	private String menberSex;
	private int teamId;
	private String teamName;
	private String info;
	private String pushDate;
	private int type;
	private String applyInfo;
	private int state;
	
	
	public String getApplyInfo() {
		return applyInfo;
	}
	public void setApplyInfo(String applyInfo) {
		this.applyInfo = applyInfo;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMenber() {
		return menber;
	}
	public void setMenber(String menber) {
		this.menber = menber;
	}
	public String getMenberNickName() {
		return menberNickName;
	}
	public void setMenberNickName(String menberNickName) {
		this.menberNickName = menberNickName;
	}
	public String getMenberIcon() {
		return menberIcon;
	}
	public void setMenberIcon(String menberIcon) {
		this.menberIcon = menberIcon;
	}
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getPushDate() {
		return pushDate;
	}
	public void setPushDate(String pushDate) {
		this.pushDate = pushDate;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getMenberSex() {
		return menberSex;
	}
	public void setMenberSex(String menberSex) {
		this.menberSex = menberSex;
	}
	
}
