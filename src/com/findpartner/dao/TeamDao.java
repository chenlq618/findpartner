package com.findpartner.dao;

import java.util.List;

import com.findpartner.bean.TeamInfo;
import com.findpartner.bean.TeamMenber;
import com.findpartner.bean.UserInfo;

public interface TeamDao {


	//发起组队功能
	public void addTeam(TeamInfo team);
	
	
	//申请加入功能
	public int menberCounts(int teamId);
	public int isMenber(TeamMenber menber);
	public int isApply(TeamMenber menber);
	public TeamInfo getTeamInfoById(Integer teamId);
	public void addMenber(TeamMenber menber);
	
	
	//获得所有正在进行的队伍信息( 1：正在进行，2：已经结束，3：取消,0:指所有队)
	public List<TeamInfo> getTeamsInfo(TeamInfo team);
	
	//根据队id,获得队员信息
	public List<TeamMenber> getTeamMenbers(TeamMenber menber);
	
	public UserInfo getMenberInfo(String phone);
	
	//获得team,然后用于判断是否有权限删除
	public void delTeam(TeamInfo team);
	
	//更改menber在队中的状态   当是1时，表示同意加入,当是2时表示等待审核，当是3时表示不同意加入,当是4时表示退出
	public void updateTeamMenberState(TeamMenber menber);
	
	//查询用户参加的所有队
	public List<TeamInfo>  getTeamsInfoByUser(TeamInfo team);
	
	public void updateTeamState();

	
}
