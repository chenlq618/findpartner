package com.findpartner.bean;

import java.io.Serializable;

public class Feedback implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String userId;
	private String feedback;
	private String fdate;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public String getFdate() {
		return fdate;
	}
	public void setFdate(String fdate) {
		this.fdate = fdate;
	}
	
}
