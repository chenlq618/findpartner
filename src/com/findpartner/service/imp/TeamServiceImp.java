package com.findpartner.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.findpartner.bean.TeamInfo;
import com.findpartner.bean.TeamMenber;
import com.findpartner.bean.UserInfo;
import com.findpartner.dao.TeamDao;
import com.findpartner.service.TeamService;

@Service
public class TeamServiceImp implements TeamService {

	@Autowired
	private TeamDao teamDao;

	public void addTeam(TeamInfo team) {
		teamDao.addTeam(team);
	}

	//申请状态：1：申请成功，2:你的申请正在审核中或已经是组员了，不能再申请,3：人数已满，申请失败，4：申请失败，teamId不存,5:参数不正确（teamId和member参数格式不对，或者为空）
	public int applyJoinTeam(TeamMenber menber) {
		int code=1;
		teamDao.updateTeamState();
		TeamInfo team = teamDao.getTeamInfoById(menber.getTeamId());
		if (team == null) {
			code=4;
		} else {
			// 判断是否为队长，队长不能再加入
			if (teamDao.isApply(menber) == 0) {
				if (team.getTeamCount() == -1
						|| teamDao.menberCounts(menber.getTeamId()) < team
								.getTeamCount()) {
					teamDao.addMenber(menber);
					code = 1;
				} else {
					code=3;
				}
			} else {
				code=2;
			}
		}
		return code;
	}
	

	public String applyExitTeam(TeamMenber menber) {
		menber.setIsCheck(1);
		if(teamDao.getTeamMenbers(menber).size()>0){
			menber.setIsCheck(4);
			teamDao.updateTeamMenberState(menber);
			return "退出成功";
		}
		menber.setIsCheck(2);
		if(teamDao.getTeamMenbers(menber).size()>0){
			menber.setIsCheck(4);
			teamDao.updateTeamMenberState(menber);
			return "退出成功";
		}
		
			return "你的申请无效，因为你不属于该队";
		
	}

	public String leaderCheckApply(String leader, TeamMenber menber) {
		if (leader != null) {
			if (teamDao.getTeamInfoById(menber.getTeamId()).getLeader()
					.equals(leader)) {
				teamDao.updateTeamMenberState(menber);
				if (menber.getIsCheck() == 1) {
					return "你已经同意" + menber.getMenber() + "的申请，ta已经是你的队员";
				} else {
					return "你已拒绝" + menber.getMenber() + "的申请";
				}
			} else {
				return leader + "不是队长";
			}
		} else {
			return "leader参数不能为空";
		}

	}

	public List<TeamInfo> getTeamsInfo(TeamInfo team) {
		teamDao.updateTeamState();
		return teamDao.getTeamsInfo(team);
	}

	public List<TeamMenber> getTeamMenbers(TeamMenber menber) {
		// 先要认证，member是否为队长或者队员
		//teamDao.updateTeamState();
		//if (teamDao.isMenber(menber)>0) {
			return teamDao.getTeamMenbers(menber);
		//}else{
		//	return null;
		//}
		
	}

	public UserInfo getMenberInfo(String phone) {
		
		return teamDao.getMenberInfo(phone);
	}

	public String delTeam(TeamInfo team) {
		if (teamDao.getTeamsInfo(team).size() > 0) {
			teamDao.delTeam(team);
			return "删除成功";
		} else {
			return "你不是leader，无权删除";
		}
	}

	public List<TeamInfo> getTeamsInfoByUser(TeamInfo team) {
		teamDao.updateTeamState();
		return teamDao.getTeamsInfoByUser(team);
	}

	public int getApplicationState(TeamMenber menber) {
		
		
		List<TeamMenber> t=teamDao.getTeamMenbers(menber);
		if(t.size()> 0){
			return t.get(0).getIsCheck();
		}else{
			if(teamDao.isMenber(menber) > 0){
				return 0;
			}else{
				return -1;
			}
		}
		
	}

}
