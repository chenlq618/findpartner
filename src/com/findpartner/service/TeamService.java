package com.findpartner.service;

import java.util.List;

import com.findpartner.bean.TeamInfo;
import com.findpartner.bean.TeamMenber;
import com.findpartner.bean.UserInfo;

public interface TeamService {
	//发起一支队
	public void addTeam(TeamInfo team);
	//申请加入一支队
	public int applyJoinTeam(TeamMenber menber);
	//申请加入一支队
	public String applyExitTeam(TeamMenber menber);
	
	public String leaderCheckApply(String leader,TeamMenber menber);
	//用户队的信息
	public List<TeamInfo> getTeamsInfo(TeamInfo team);
	
	public List<TeamMenber> getTeamMenbers(TeamMenber menber);
	
	//根据用户名，获得用户详细信息
	public UserInfo getMenberInfo(String phone);
	
	//先判断用户是否是队长，如果是，有权删除，如果不是，无权删除
	public String delTeam(TeamInfo team);
	
	//
	public List<TeamInfo> getTeamsInfoByUser(TeamInfo team);
	
	public int getApplicationState(TeamMenber menber);

	
}
