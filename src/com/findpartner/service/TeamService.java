package com.findpartner.service;

import java.util.List;

import com.findpartner.bean.MemberInfo;
import com.findpartner.bean.PushInfo;
import com.findpartner.bean.TeamInfo;
import com.findpartner.bean.TeamMenber;
import com.findpartner.bean.UserInfo;
import com.findpartner.bean.UserTeamMember;

public interface TeamService {
	//发起一支队
	public void addTeam(TeamInfo team);
	//申请加入一支队
	public int applyJoinTeam(TeamMenber menber);
	//申请加入一支队
	public String applyExitTeam(TeamMenber menber);
	
	public int leaderCheckApply(String leader,TeamMenber menber);
	//用户队的信息
	public List<TeamInfo> getTeamsInfo(TeamInfo team);
	
	public List<MemberInfo> getTeamMenbers(TeamMenber menber);
	public List<MemberInfo> getTeamMenbers2(TeamMenber menber);
	
	//根据用户名，获得用户详细信息
	public UserInfo getMenberInfo(String phone);
	
	//先判断用户是否是队长，如果是，有权删除，如果不是，无权删除
	public String delTeam(TeamInfo team);
	
	//
	public List<TeamInfo> getTeamsInfoByUser(TeamInfo team);
	
	public int getApplicationState(TeamMenber menber);

	public int getTeamId(TeamInfo team);
	
	public List<PushInfo> getNotification(String phone,int start,int size);
	public void updateState(int id,int state);
	public TeamInfo getTeamInfoById(int teamId);
	public UserTeamMember associate(int teamId,String member);
	
	//每次创建队伍时，将通知信息插入到数据库
	public void applyJoinPush(PushInfo member,PushInfo leader);
	public void dismissTeamPush(int teamId,String teamName);
	public void exitTeamPush(PushInfo member,PushInfo leader);
	public void leaderCheckPush(PushInfo member,PushInfo leader);
	
}
