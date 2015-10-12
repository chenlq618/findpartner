package com.findpartner.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.findpartner.bean.MemberInfo;
import com.findpartner.bean.PushInfo;
import com.findpartner.bean.TeamInfo;
import com.findpartner.bean.TeamMenber;
import com.findpartner.bean.UserInfo;
import com.findpartner.bean.UserTeamMember;
import com.findpartner.dao.NotificationDao;
import com.findpartner.dao.TeamDao;
import com.findpartner.dao.UserDao;
import com.findpartner.service.TeamService;

@Service
public class TeamServiceImp implements TeamService {

	@Autowired
	private TeamDao teamDao;
	@Autowired
	private UserDao userDao;

	public void addTeam(TeamInfo team) {
		teamDao.addTeam(team);
	}

	// 申请状态：1：申请成功，2:你的申请正在审核中或已经是组员了，不能再申请,3：人数已满，申请失败，4：申请失败，teamId不存,5:参数不正确（teamId和member参数格式不对，或者为空）
	public int applyJoinTeam(TeamMenber menber) {
		int code = 1;
		teamDao.updateOutdatedTeamState();
		TeamInfo team = teamDao.getTeamInfoById(menber.getTeamId());
		if (team == null) {
			code = 4;
		} else {
			// 判断是否为队长，队长不能再加入
			if (teamDao.canApply(menber) < 1) {
				if (team.getTeamCount() < 1
						|| teamDao.menberCounts(menber.getTeamId()) < team
								.getTeamCount()) {
					if (teamDao.isExitOrUnCheck(menber) > 0) {
						menber.setIsCheck(2);
						teamDao.updateTeamMenberState(menber);
					} else {
						teamDao.addMenber(menber);
					}
					code = 1;

				} else {
					teamDao.refuseRestApply(menber.getTeamId());
					code = 3;
				}
			} else {
				code = 2;
			}
		}
		return code;
	}

	public String applyExitTeam(TeamMenber menber) {
		menber.setIsCheck(1);
		if (teamDao.getTeamFlowerMenbers(menber).size() > 0) {
			menber.setIsCheck(4);
			teamDao.updateTeamMenberState(menber);
			return "退出成功";
		}
		menber.setIsCheck(2);
		if (teamDao.getTeamFlowerMenbers(menber).size() > 0) {
			menber.setIsCheck(4);
			teamDao.updateTeamMenberState(menber);
			return "退出成功";
		}

		return "你的申请无效，因为你不属于该队";

	}

	// 0:有非空参数没设置什 ，1:同意加入,3:拒绝加入 2：人数已满，4：非队长，无权同意或拒绝, 5:isCheck
	// 只能是(1：表示同意加入；3：表示拒绝加入)
	public int leaderCheckApply(String leader, TeamMenber menber) {
		int code;
		if (leader != null) {
			// 只有队长，才有权处理申请
			if (teamDao.getTeamInfoById(menber.getTeamId()).getLeader()
					.equals(leader)) {
				if (menber.getIsCheck() == 1) {
					if (teamDao.getTeamInfoById(menber.getTeamId()).getTeamCount()<1 ||
							teamDao.menberCounts(menber.getTeamId()) < teamDao
							.getTeamInfoById(menber.getTeamId()).getTeamCount()) {
						teamDao.updateTeamMenberState(menber);
						code = 1;
					} else {
						teamDao.refuseRestApply(menber.getTeamId());
						code = 3;
					}

				} else {
					teamDao.updateTeamMenberState(menber);
					code = 3;
				}
			} else {
				code = 4;
			}
		} else {
			code = 0;
		}
		return code;

	}

	public List<TeamInfo> getTeamsInfo(TeamInfo team) {
		teamDao.updateOutdatedTeamState();
		return teamDao.getTeamsInfo(team);
	}

	public List<MemberInfo> getTeamMenbers(TeamMenber menber) {

		if (teamDao.isMenber(menber) > 0) {
			return teamDao.getTeamMenbers(menber.getTeamId());
		} else {
			return null;
		}

	}

	public List<MemberInfo> getTeamMenbers2(TeamMenber menber) {

		return teamDao.getTeamMenbers(menber.getTeamId());

	}

	public UserInfo getMenberInfo(String phone) {

		return teamDao.getMenberInfo(phone);
	}

	public String delTeam(TeamInfo team) {
		if (teamDao.getTeamInfoById(team.getTeamId()).getLeader()
				.equals(team.getLeader())) {
			teamDao.delTeam(team);
			return "删除成功";
		} else {
			return "你不是leader，无权删除";
		}
	}

	public List<TeamInfo> getTeamsInfoByUser(TeamInfo team) {
		teamDao.updateOutdatedTeamState();
		return teamDao.getTeamsInfoByUser(team);
	}

	// 获得申请状态(当是-1时，表示没进行任何申请;当是1时，表示同意加入;当是2时表示等待审核，当是3时表示不同意加入;当是4时表示退出；当是5)
	public int getApplicationState(TeamMenber menber) {

		List<TeamMenber> t = teamDao.getTeamFlowerMenbers(menber);
		if (t.size() > 0) {
			return t.get(0).getIsCheck();
		} else {
			if (teamDao.isMenber(menber) > 0) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	public int getTeamId(TeamInfo team) {
		return teamDao.getTeamId(team);
	}

	public TeamInfo getTeamInfoById(int teamId) {
		return teamDao.getTeamInfoById(teamId);
	}

	// 将team,user,member做一个关联
	public UserTeamMember associate(int teamId, String member) {
		UserTeamMember utm = new UserTeamMember();
		TeamInfo teamInfo = teamDao.getTeamInfoById(teamId);
		utm.setTeamInfo(teamInfo);
		if (teamInfo != null) {
			UserInfo leaderInfo = userDao.getUserInfo(teamInfo.getLeader());
			utm.setLeaderInfo(leaderInfo);
		}
		if (member != null) {
			utm.setMemberInfo(userDao.getUserInfo(member));
		}
		return utm;
	}

	@Autowired
	private NotificationDao notificationDao;

	// 推送管理
	public List<PushInfo> getNotification(String phone, int start, int size) {
		return notificationDao.getNotification(phone, start, size);
	}

	public void applyJoinPush(PushInfo member, PushInfo leader) {
		notificationDao.insertNotification(member);
		notificationDao.insertNotification(leader);
	}

	public void dismissTeamPush(int teamId, String teamName) {
		List<MemberInfo> members = teamDao.getTeamMenbers(teamId);
		PushInfo push = new PushInfo();
		push.setInfo("【" + teamName + "】已解散");
		push.setTeamId(teamId);
		push.setTeamName(teamName);
		push.setType(1);
		for (int i = 0; i < members.size(); i++) {
			push.setPhone(members.get(i).getPhone());
			notificationDao.insertNotification(push);
		}
	}

	public void exitTeamPush(PushInfo member, PushInfo leader) {
		notificationDao.insertNotification(member);
		notificationDao.insertNotification(leader);
	}

	public void leaderCheckPush(PushInfo member, PushInfo leader) {
		notificationDao.insertNotification(member);
		notificationDao.insertNotification(leader);
	}
	
	public void updateState(int id,int state){
		notificationDao.updateState(id, state);
	}

}
