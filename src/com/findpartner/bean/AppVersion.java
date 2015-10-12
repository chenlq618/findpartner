package com.findpartner.bean;

import java.io.Serializable;

public class AppVersion  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int versionCode;
	private String versionName;
	private int subVersionCode;
	public int getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public int getSubVersionCode() {
		return subVersionCode;
	}
	public void setSubVersionCode(int subVersionCode) {
		this.subVersionCode = subVersionCode;
	}
	
	
	

}
