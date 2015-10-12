package com.findpartner.dao;

import java.util.List;

import com.findpartner.bean.MemberInfo;
import com.findpartner.bean.PushInfo;
import com.findpartner.bean.TeamInfo;
import com.findpartner.bean.TeamMenber;
import com.findpartner.bean.UserInfo;

public interface TeamDao {


	//发起组队功能
	public void addTeam(TeamInfo team);
	
	
	//申请加入功能
	public int menberCounts(int teamId);//计算teamId当前的申请人数
	public int isMenber(TeamMenber menber); //是否是队员（leader,ischeck=1)
	public int canApply(TeamMenber menber); //如果>0，则说明(leader,ischeck=1,2),即不能再申请
	public TeamInfo getTeamInfoById(Integer teamId);
	public void addMenber(TeamMenber menber);//增加队伍
	
	
	//获得所有正在进行的队伍信息( 1：正在进行，2：已经结束，3：取消,0:指所有队)
	public List<TeamInfo> getTeamsInfo(TeamInfo team);
	
	//根据队id,isCheck 获得队员信息
	public List<TeamMenber> getTeamFlowerMenbers(TeamMenber menber);
	
	//根据队id,获得成员信息
	public List<MemberInfo> getTeamMenbers(int teamId);
	
	//根据用户号，获取用户信息
	public UserInfo getMenberInfo(String phone);
	
	//获得team,然后用于判断是否有权限删除
	public void delTeam(TeamInfo team);
	
	//更改menber在队中的状态   当是1时，表示同意加入,当是2时表示等待审核，当是3时表示不同意加入,当是4时表示退出
	public void updateTeamMenberState(TeamMenber menber);
	
	//查询用户参加的所有队
	public List<TeamInfo>  getTeamsInfoByUser(TeamInfo team);
	
	//将更新过期任务的状态
	public void updateOutdatedTeamState();
	
	public int getTeamId(TeamInfo team);
	//如果大于0，说明isCheck=3 or 4
	public int isExitOrUnCheck(TeamMenber menber);
	
	//当人数满时，把余下的待处理申请变为拒绝
	public void refuseRestApply(int teamId);
	
}
