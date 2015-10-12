package com.findpartner.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.findpartner.bean.PushInfo;
import com.findpartner.bean.TeamInfo;
import com.findpartner.bean.TeamMenber;
import com.findpartner.bean.UserTeamMember;
import com.findpartner.dao.UserDao;
import com.findpartner.push.PushUtil;
import com.findpartner.service.TeamService;
import com.findpartner.util.Constants;
import com.findpartner.util.ImageCompress;
import com.findpartner.util.RonghubUtil;

@Controller
@RequestMapping(value = "team")
public class TeamController {
	@Autowired
	private TeamService teamService;
	@Autowired
	private UserDao userDao;

	private static SimpleDateFormat df;
	static {
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	}

	private static String dataFormat = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";

	@RequestMapping("addTeamL.json")
	@ResponseBody
	// 提出组队
	public Map<String, Object> addTeam(
			HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "myfiles", required = false) MultipartFile[] myfiles)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> info = new HashMap<String, String>();
		result.put("info", info);
		String leader = request.getParameter("phone");
		if (leader != null) {
			TeamInfo team = new TeamInfo();
			team.setLeader(leader);
			team.setTeamName(request.getParameter("teamName"));

			String teamCount = request.getParameter("teamCount");
			if (teamCount != null) {
				team.setTeamCount(Integer.parseInt(teamCount));
			}

			// team icon处理
			if (file != null) {
				// 图标统一放置位置
				String realPath = request.getSession().getServletContext()
						.getRealPath("/teamIcon");
				File path = new File(realPath);
				if (!path.exists()) {
					path.mkdirs();
				}
				String fileName = file.getOriginalFilename();
				if (fileName.toUpperCase().contains(".JPG")
						|| fileName.toUpperCase().contains(".PNG")) {
					if (fileName.toUpperCase().contains(".JPB")) {
						fileName = leader + System.currentTimeMillis() + ".jpg";
					} else {
						fileName = leader + System.currentTimeMillis() + ".png";
					}
					File destFile = new File(realPath, fileName);
					if (!destFile.exists()) {
						destFile.mkdirs();
					}
					file.transferTo(destFile);
					info.put("infoIconImage", "图标保存成功");
					String imgURL = Constants.TeamIcon + fileName;
					team.setTeamIcon(imgURL);
				} else {
					info.put("infoIconImage", "文件不是jpg或者png后缀的图片");
				}

			} else {
				team.setTeamIcon(userDao.getUserInfo(leader).getUserIcon());
			}

			// team image处理
			// System.out.println("文件原名: " + myfile.getOriginalFilename());
			if (myfiles != null) {
				String realPath2 = request.getSession().getServletContext()
						.getRealPath("/teamimgs");
				File path2 = new File(realPath2);
				if (!path2.exists()) {
					path2.mkdirs();
				}
				String pics = "";
				int count = 0;
				for (MultipartFile myfile : myfiles) {
					count++;
					if (!myfile.isEmpty()) {
						String fileName = myfile.getOriginalFilename();
						String fileName2 = null;
						if (fileName.toUpperCase().contains(".JPG")
								|| fileName.toUpperCase().contains(".PNG")) {
							if (fileName.toUpperCase().contains(".JPB")) {
								fileName = leader + System.currentTimeMillis()
										+ count; // + ".jpg";
								fileName2 = fileName + "compress" + ".jpg";
								fileName = fileName + ".jpg";
							} else {
								fileName = leader + System.currentTimeMillis()
										+ count;
								fileName2 = fileName + "compress" + ".png";
								fileName = fileName + ".png";
							}
							File destFile = new File(realPath2, fileName);
							if (!destFile.exists()) {
								destFile.mkdirs();
							}
							myfile.transferTo(destFile);

							ImageCompress.CompressByPercent(destFile, realPath2
									+ "/" + fileName2);
							info.put("infoIconImage", "图标保存成功");
							String imgURL = Constants.TeamImgs + fileName;
							if (pics.equals("")) {
								pics = imgURL;
							} else {
								pics = pics + "|" + imgURL;
							}

						}
					}
				}
				team.setPics(pics);
			}

			if (request.getParameter("startTime") == null
					|| "".equals(request.getParameter("startTime"))) {
				team.setStartTime(df.format(new Date()));
			} else {
				if (request.getParameter("startTime").matches(dataFormat)) {
					team.setStartTime(request.getParameter("startTime"));
				} else {
					info.put("startTimeInfo", "日期格式必须2011-01-01 01:01");
					return result;
				}
			}

			if (request.getParameter("endTime") != null
					&& request.getParameter("endTime").matches(dataFormat)) {
				team.setEndTime(request.getParameter("endTime"));
			} else {
				info.put("endTimeInfo", "日期格式必须2011-01-01 01:01");
				return result;
			}

			team.setNeeding(request.getParameter("needing"));
			team.setAdditionNeading(request.getParameter("additionNeading"));
			team.setState(1);
			team.setLocation(request.getParameter("location"));
			team.setType1(request.getParameter("type1"));
			team.setType2(request.getParameter("type2"));
			teamService.addTeam(team);
			info.put("addTeamInfo", "成功发起一个组队");

			// 创建群
			int teamId = teamService.getTeamId(team);
			info.put("teamId", "" + teamId);
			RonghubUtil.createGroup(teamId, team.getTeamName(),
					team.getLeader());
		} else {
			info.put("infoAddTeam", "leader不能为空");
		}

		return result;
	}

	// 申请加入队
	// 申请状态：1：申请成功，2:你的申请正在审核中或已经是组员了，不能再申请,3：人数已满，申请失败，4：申请失败，teamId不存,5:参数不正确（teamId或member为空）
	@RequestMapping("applyJoinTeamL.json")
	@ResponseBody
	public Map<String, Object> applyJoinTeam(HttpServletRequest request)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		TeamMenber menber = new TeamMenber();
		int teamId = 0;
		if (request.getParameter("teamId") != null
				&& !"".equals(request.getParameter("teamId"))) {
			try {
				teamId = Integer.parseInt(request.getParameter("teamId"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		menber.setTeamId(teamId);
		menber.setTeamName(request.getParameter("teamName"));
		menber.setMenber(request.getParameter("phone"));
		if (request.getParameter("phone") != null
				&& request.getParameter("teamId") != null) {
			int code = teamService.applyJoinTeam(menber);
			result.put("code", code);
			// 极光推送
			UserTeamMember utm = teamService.associate(teamId,
					menber.getMenber());

			PushInfo leaderPush = new PushInfo();
			leaderPush.setPhone(utm.getLeaderInfo().getPhone());
			leaderPush.setMenber(utm.getMemberInfo().getPhone());
			leaderPush.setMenberIcon(utm.getMemberInfo().getUserIcon());
			leaderPush.setMenberNickName(utm.getMemberInfo().getNickname());
			leaderPush.setMenberSex(utm.getMemberInfo().getSex());
			leaderPush.setTeamId(teamId);
			leaderPush.setTeamName(utm.getTeamInfo().getTeamName());
			leaderPush.setType(2);
			leaderPush.setInfo("【" + utm.getMemberInfo().getNickname()
					+ "】申请加入【" + utm.getTeamInfo().getTeamName() + "】");
			leaderPush.setApplyInfo(request.getParameter("applyInfo"));

			/*
			PushInfo memberPush = new PushInfo();
			memberPush.setPhone(utm.getMemberInfo().getPhone());
			memberPush.setTeamId(utm.getTeamInfo().getTeamId());
			memberPush.setTeamName(utm.getTeamInfo().getTeamName());

			String codeStr = null;
			switch (code) {
			case 1:
				codeStr ="【"+ utm.getLeaderInfo().getNickname()+"】同意你加入【"+utm.getTeamInfo().getTeamName()+"】";
				break;
			case 2:
				codeStr = "你的申请加入【"+utm.getTeamInfo().getTeamName()+"】正在审核中或已经是组员了，不能再申请";
				break;
			default:
				codeStr ="【"+ utm.getLeaderInfo().getNickname()+"】拒绝你加入【"+utm.getTeamInfo().getTeamName()+"】";
				break;
			}
			memberPush.setInfo(codeStr);
			memberPush.setType(1);
*/
			teamService.applyJoinPush(null, leaderPush);
			PushUtil.getInstance().applyJoin(leaderPush, null);
			// PushUtil.getInstance().applyJoin(code,
			// utm.getMemberInfo().getPhone(),
			// utm.getMemberInfo().getNickname(), utm.getTeamInfo().getLeader(),
			// teamId, utm.getTeamInfo().getTeamName());
		} else {
			result.put("code", 5);
		}
		return result;
	}

	// 退出队
	@RequestMapping("applyExitTeamL.json")
	@ResponseBody
	public Map<String, Object> applyExitTeam(HttpServletRequest request)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> info = new HashMap<String, String>();
		result.put("info", info);

		TeamMenber menber = new TeamMenber();
		Integer teamId = null;
		try {
			teamId = Integer.parseInt(request.getParameter("teamId"));
		} catch (Exception e) {
			info.put("notValiableTeamId", "teamId must be a number");
			return result;
		}
		menber.setTeamId(teamId);
		menber.setMenber(request.getParameter("phone"));
		if (request.getParameter("phone") != null
				&& request.getParameter("teamId") != null) {
			String infoJoinTeam = teamService.applyExitTeam(menber);
			info.put("infoExitTeam", infoJoinTeam);

			// 极光推送
			UserTeamMember utm = teamService.associate(teamId,
					menber.getMenber());
			PushInfo leaderPush = new PushInfo();
			leaderPush.setPhone(utm.getLeaderInfo().getPhone());
			leaderPush.setTeamId(utm.getTeamInfo().getTeamId());
			leaderPush.setTeamName(utm.getTeamInfo().getTeamName());
			leaderPush.setType(1);
			leaderPush.setInfo("【" + utm.getMemberInfo().getNickname() + "】退出【"
					+ utm.getTeamInfo().getTeamName() + "】");

			PushInfo memberPush = new PushInfo();
			memberPush.setPhone(utm.getMemberInfo().getPhone());
			memberPush.setTeamId(utm.getTeamInfo().getTeamId());
			memberPush.setTeamName(utm.getTeamInfo().getTeamName());
			memberPush.setType(1);
			memberPush
					.setInfo("你已经退出【" + utm.getTeamInfo().getTeamName() + "】");

			teamService.exitTeamPush(memberPush, leaderPush);
			PushUtil.getInstance().exitTeam(leaderPush, memberPush);

			// 从融云群里删除队员
			RonghubUtil.exitTeam(menber.getTeamId(), menber.getMenber(),utm.getMemberInfo().getNickname());
		} else {
			info.put("infoExitTeam", "teamId and menber can be null");
		}
		return result;
	}

	// 获得所有队的信息
	@RequestMapping("getTeamsInfo.json")
	@ResponseBody
	public Map<String, Object> getTeamsInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> info = new HashMap<String, String>();
		result.put("info", info);
		TeamInfo team = new TeamInfo();

		String pageNo = request.getParameter("pageNo");
		if (pageNo != null) {
			try {
				team.setPageNo(Integer.parseInt(pageNo));
			} catch (Exception e) {
				info.put("pageNo", pageNo + " 不是整数");
				e.printStackTrace();
			}
		}
		String pageSize = request.getParameter("pageSize");
		if (pageSize != null) {
			try {
				team.setPageSize(Integer.parseInt(pageSize));
			} catch (Exception e) {
				info.put("pageSize", pageSize + " 不是整数");
				e.printStackTrace();
			}
		}

		result.put("teams", teamService.getTeamsInfo(team));

		return result;
	}

	// 获得所组的所有队的信息
	@RequestMapping("getLeaderTeamsInfoL.json")
	@ResponseBody
	public Map<String, Object> getLeaderTeamsInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> info = new HashMap<String, String>();
		result.put("info", info);
		TeamInfo team = new TeamInfo();

		team.setLeader(request.getParameter("phone"));

		String state = request.getParameter("state");
		if (state != null) {
			try {
				team.setPageNo(Integer.parseInt(state));
			} catch (Exception e) {
				info.put("state", state + " 不是整数");
				e.printStackTrace();
			}
		}

		String pageNo = request.getParameter("pageNo");
		if (pageNo != null) {
			try {
				team.setPageNo(Integer.parseInt(pageNo));
			} catch (Exception e) {
				info.put("pageNo", pageNo + " 不是整数");
				e.printStackTrace();
			}
		}
		String pageSize = request.getParameter("pageSize");
		if (pageSize != null) {
			try {
				team.setPageSize(Integer.parseInt(pageSize));
				info.put("pageSize", pageSize + " 不是整数");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		result.put("teams", teamService.getTeamsInfo(team));

		return result;
	}

	// 获得所组的所有队的信息
	@RequestMapping("getJoinTeamsInfoL.json")
	@ResponseBody
	public Map<String, Object> getJoinTeamsInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> info = new HashMap<String, String>();
		result.put("info", info);

		TeamInfo team = new TeamInfo();
		team.setLeader(request.getParameter("phone"));

		String state = request.getParameter("state");
		if (state != null) {
			try {
				team.setState(Integer.parseInt(state));
			} catch (Exception e) {
				info.put("state", state + " 不是整数");
				e.printStackTrace();
			}
		}

		String pageNo = request.getParameter("pageNo");
		if (pageNo != null) {
			try {
				team.setPageNo(Integer.parseInt(pageNo));
			} catch (Exception e) {
				info.put("pageNo", pageNo + " 不是整数");
				e.printStackTrace();
			}
		}
		String pageSize = request.getParameter("pageSize");
		if (pageSize != null) {
			try {
				team.setPageSize(Integer.parseInt(pageSize));
				info.put("pageSize", pageSize + " 不是整数");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		result.put("teams", teamService.getTeamsInfoByUser(team));

		return result;
	}

	// 返回队员的相关信息
	@RequestMapping("getTeamMenbersL.json")
	@ResponseBody
	public Map<String, Object> getTeamMenbers(HttpServletRequest request)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> info = new HashMap<String, String>();
		result.put("info", info);

		TeamMenber menber = new TeamMenber();
		String phone = request.getParameter("phone");
		String teamId = request.getParameter("teamId");
		if (teamId != null && phone != null) {
			try {
				menber.setTeamId(Integer.parseInt(teamId));
				menber.setMenber(phone);

				// isCheck 当是1时，表示同意加入,当是2时表示等待审核，当是3时表示不同意加入,0表示所有
				String isCheck = request.getParameter("isCheck");
				if (isCheck != null) {
					menber.setIsCheck(Integer.parseInt(isCheck));
				}
				result.put("menbers", teamService.getTeamMenbers(menber));
				if (result.get("menbers") == null) {
					info.put("infoGetTeamMenbers", "成员为空，或者你不是成员，不能查看内部成员信息");
				}
			} catch (Exception e) {
				e.printStackTrace();
				info.put("teamId", teamId + " 不是整数");
			}
		} else {
			info.put("infoTeamId&phone", teamId + "和" + phone + " 不能为空");
		}

		return result;
	}

	@RequestMapping("getTeamMenbers.json")
	@ResponseBody
	public Map<String, Object> getTeamMenbers2(HttpServletRequest request)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> info = new HashMap<String, String>();
		result.put("info", info);

		TeamMenber menber = new TeamMenber();
		
		String teamId = request.getParameter("teamId");
		if (teamId != null) {
			try {
				menber.setTeamId(Integer.parseInt(teamId));
				// isCheck 当是1时，表示同意加入,当是2时表示等待审核，当是3时表示不同意加入,0表示所有
				String isCheck = request.getParameter("isCheck");
				if (isCheck != null) {
					menber.setIsCheck(Integer.parseInt(isCheck));
				}
				result.put("menbers", teamService.getTeamMenbers2(menber));
				if (result.get("menbers") == null) {
					info.put("infoGetTeamMenbers", "成员为空，或者你不是成员，不能查看内部成员信息");
				}
			} catch (Exception e) {
				e.printStackTrace();
				info.put("teamId", teamId + " 不是整数");
			}
		} else {
			info.put("infoTeamId&phone", "teamId 不能为空");
		}

		return result;
	}

	// 返回队员的相关信息
	@RequestMapping("getMenberInfo.json")
	@ResponseBody
	public Map<String, Object> getMenberInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> info = new HashMap<String, String>();
		result.put("info", info);

		String phone = request.getParameter("phone");
		if (phone != null) {
			result.put("menber", teamService.getMenberInfo(phone));
		} else {
			info.put("infoMenber", "phone参数不能为空");
		}
		return result;
	}

	@RequestMapping("delTeamL.json")
	@ResponseBody
	public Map<String, Object> delTeam(HttpServletRequest request)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> info = new HashMap<String, String>();
		result.put("info", info);

		String teamId = request.getParameter("teamId");
		String leader = request.getParameter("phone");
		String teamName = teamService.getTeamInfoById(Integer.parseInt(teamId))
				.getTeamName();
		if (teamId != null && leader != null) {
			try {
				TeamInfo team = new TeamInfo();
				team.setTeamId(Integer.parseInt(teamId));
				team.setLeader(leader);
				info.put("InfoDelTeam", teamService.delTeam(team));
				// 极光推送
				teamService.dismissTeamPush(Integer.parseInt(teamId), teamName);
				PushUtil.getInstance().dismissTeam(Integer.parseInt(teamId),
						teamName);
				// 融云删除队伍
				RonghubUtil.dismissTeam(team.getTeamId(), team.getLeader());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			info.put("InfoDelTeam", "TeamId 或 leader 参数不能为空");
		}

		return result;
	}

	@RequestMapping("getApply.json")
	@ResponseBody
	public Map<String, Object> getApply(HttpServletRequest request)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		return result;
	}

	@RequestMapping("leaderCheckApplyL.json")
	@ResponseBody
	public Map<String, Object> leaderCheckApplyL(HttpServletRequest request)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		int code = 0;

		String teamId = request.getParameter("teamId");
		String leader = request.getParameter("phone");
		String menber = request.getParameter("menber");
		if (teamId != null && leader != null && menber != null) {
			try {
				TeamMenber teamMenber = new TeamMenber();
				teamMenber.setTeamId(Integer.parseInt(teamId));
				teamMenber.setMenber(menber);
				String isCheck = request.getParameter("isCheck");
				if (isCheck != null
						&& (isCheck.equals("1") || isCheck.equals("3"))) {
					teamMenber.setIsCheck(Integer.parseInt(isCheck));
					code = teamService.leaderCheckApply(leader, teamMenber);

					// 极光推送
					UserTeamMember utm = teamService.associate(
							Integer.parseInt(teamId), menber);

					String stateStr = null;
					if (isCheck.equals("1")) {
						stateStr = "同意";
					} else {
						stateStr = "拒绝";
					}

					PushInfo leaderPush = new PushInfo();
					leaderPush.setPhone(utm.getLeaderInfo().getPhone());
					leaderPush.setTeamId(utm.getTeamInfo().getTeamId());
					leaderPush.setTeamName(utm.getTeamInfo().getTeamName());
					leaderPush.setType(1);
					leaderPush.setInfo("你" + stateStr + "【"
							+ utm.getMemberInfo().getNickname() + "】申请加入【"
							+ utm.getTeamInfo().getTeamName() + "】");

					PushInfo memberPush = new PushInfo();
					memberPush.setPhone(utm.getMemberInfo().getPhone());
					memberPush.setTeamId(utm.getTeamInfo().getTeamId());
					memberPush.setTeamName(utm.getTeamInfo().getTeamName());
					memberPush.setType(1);
					memberPush.setInfo("【" + utm.getLeaderInfo().getNickname()
							+ "】" + stateStr + "你申请加入【"
							+ utm.getTeamInfo().getTeamName() + "】");

					teamService.leaderCheckPush(memberPush, leaderPush);
					PushUtil.getInstance().leaderCheck(leaderPush, memberPush);
					// 如果加入成功，融云将用户加入聊天组
					if (code == 1) {
						RonghubUtil
								.joinTeam(teamMenber.getTeamId(), teamMenber
										.getMenber(), utm.getMemberInfo()
										.getNickname());
					}
					

				} else {
					code = 5;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			code = 0;
		}
		result.put("code", code);
		return result;
	}

	@RequestMapping("getApplicationStateL.json")
	@ResponseBody
	public Map<String, Object> getApplicationState(HttpServletRequest request)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> info = new HashMap<String, String>();
		result.put("info", info);
		String phone = request.getParameter("phone");
		TeamMenber menber = new TeamMenber();
		Integer teamId = null;
		try {
			teamId = Integer.parseInt(request.getParameter("teamId"));
		} catch (Exception e) {
			info.put("notValiableTeamId", "teamId must be a number");
			return result;
		}
		menber.setMenber(phone);
		menber.setTeamId(teamId);

		result.put("state", teamService.getApplicationState(menber));
		return result;
	}

	@RequestMapping("getNotificationL.json")
	@ResponseBody
	public Map<String, Object> getNotification(HttpServletRequest request)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		String phone = request.getParameter("phone");
		String startStr = request.getParameter("start");
		String sizeStr = request.getParameter("size");
		int start = 0;
		int size = 5;
		try {
			if(startStr!=null&& !"".equals(startStr)){
				start=Integer.parseInt(startStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(sizeStr!=null && !"".equals(sizeStr)){
				size=Integer.parseInt(sizeStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("notifications", teamService.getNotification(phone,start,size));
		return result;
	}

	@RequestMapping("getTeamInfoById.json")
	@ResponseBody
	public Map<String, Object> getTeamInfoById(HttpServletRequest request)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String teamIdStr = request.getParameter("teamId");

		int teamId = 0;
		if (teamIdStr != null && !"".equals(teamIdStr)) {
			try {
				teamId = Integer.parseInt(teamIdStr);
				TeamInfo teamInfo = teamService.getTeamInfoById(teamId);
				result.put("teamName", teamInfo.getTeamName());
				result.put("teamIcon", teamInfo.getTeamIcon());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return null;
		}

		return result;
	}
	
	@RequestMapping("updateNotificationStateL.json")
	@ResponseBody
	public Map<String, Object> updateNotificationState(HttpServletRequest request)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String idStr=request.getParameter("id");
		String stateStr=request.getParameter("state");
		int id=-1;
		int state=0;
		if(idStr!=null && !"".equals(idStr)){
			try{
				id=Integer.parseInt(idStr);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			result.put("info", "id不能为空");
			return result;
		}

		if(stateStr.equals("1")||stateStr.equals("2")){
			try{
				state=Integer.parseInt(stateStr);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			result.put("info", "state只能为1（表示同意），2（表示拒绝）");
			return result;
		}
		
		teamService.updateState(id, state);
		return result;
	}

}
