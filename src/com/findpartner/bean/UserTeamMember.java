package com.findpartner.bean;

import java.io.Serializable;
import java.util.List;

public class UserTeamMember implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private TeamInfo teamInfo;
	private UserInfo leaderInfo;
	private UserInfo memberInfo;
	private List<UserInfo> membersInfo;
	public TeamInfo getTeamInfo() {
		return teamInfo;
	}
	public void setTeamInfo(TeamInfo teamInfo) {
		this.teamInfo = teamInfo;
	}
	public UserInfo getLeaderInfo() {
		return leaderInfo;
	}
	public void setLeaderInfo(UserInfo leaderInfo) {
		this.leaderInfo = leaderInfo;
	}
	public UserInfo getMemberInfo() {
		return memberInfo;
	}
	public void setMemberInfo(UserInfo memberInfo) {
		this.memberInfo = memberInfo;
	}
	public List<UserInfo> getMembersInfo() {
		return membersInfo;
	}
	public void setMembersInfo(List<UserInfo> membersInfo) {
		this.membersInfo = membersInfo;
	}
	
}
