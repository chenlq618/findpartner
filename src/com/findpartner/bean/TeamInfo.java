package com.findpartner.bean;

import java.sql.Timestamp;
import java.util.List;

import com.findpartner.util.Constants;

/**
 * Teaminfo entity. @author MyEclipse Persistence Tools
 */

public class TeamInfo implements java.io.Serializable {

	// Fields

	private Integer teamId;
	private String teamName;
	private String teamIcon;
	private Integer teamCount;
	private String releaseTime;// 发布时间
	private String startTime;
	private String endTime;
	private String location; 
	private String needing;
	private String additionNeading;
	private String pics;
	private String[] pics2;
	private Integer nowMemberCount;//当前通过审核的人数
	
	
	public String[] getPics2() {
		if(pics!=null && !"".equals(pics)){
			return pics.split("\\|");
		}else{
			return null;
		}
	}
	
	private String leader;
	private Integer state;
	private String type1;
	private String type2;
	
	//这些属性是计算离组队结束还有多长时间的
	private int leaveDay;
	private int leaveHour;
	private int leaveMinus;
	
	
	//这些属性用来分页
	private int pageNo=1;
	private int pageSize = Constants.PAGE_SIZE;
	private int start ;
	
	
	
	public int getStart() {
		return (pageNo-1)*pageSize;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	

	public String getTeamIcon() {
		return teamIcon;
	}

	public void setTeamIcon(String teamIcon) {
		this.teamIcon = teamIcon;
	}

	public String getPics() {
		return pics;
	}

	public void setPics(String pics) {
		this.pics = pics;
	}

	public Integer getTeamId() {
		return this.teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return this.teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	
	public String getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}
	public Integer getTeamCount() {
		return this.teamCount;
	}

	public void setTeamCount(Integer teamCount) {
		this.teamCount = teamCount;
	}

	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getNeeding() {
		return this.needing;
	}

	public void setNeeding(String needing) {
		this.needing = needing;
	}

	public String getAdditionNeading() {
		return this.additionNeading;
	}

	public void setAdditionNeading(String additionNeading) {
		this.additionNeading = additionNeading;
	}

	public String getLeader() {
		return this.leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	
	public Integer getNowMemberCount() {
		return nowMemberCount;
	}
	public void setNowMemberCount(Integer nowMemberCount) {
		this.nowMemberCount = nowMemberCount;
	}
	public String getType1() {
		return this.type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public String getType2() {
		return this.type2;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}


	public int getLeaveDay() {
		return leaveDay;
	}
	public void setLeaveDay(int leaveDay) {
		this.leaveDay = leaveDay;
	}
	public int getLeaveHour() {
		return leaveHour;
	}
	public void setLeaveHour(int leaveHour) {
		this.leaveHour = leaveHour;
	}
	public int getLeaveMinus() {
		return leaveMinus;
	}
	public void setLeaveMinus(int leaveMinus) {
		this.leaveMinus = leaveMinus;
	}
	
}