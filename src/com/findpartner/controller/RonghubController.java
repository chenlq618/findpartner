package com.findpartner.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;

import com.findpartner.util.Constants;
import com.findpartner.util.RonghubUtil;

@Controller
@RequestMapping(value = "rong")
public class RonghubController {

	static Jedis jedis;
	static {
		jedis = new Jedis(Constants.RedisIp);
	}

	// 获取token
	@RequestMapping(value = "getTalkToken.json")
	@ResponseBody
	public JSONObject getTalkToken(HttpServletRequest request) throws Exception {
		HashMap params = new HashMap();
		String userId = request.getParameter("userId");
		String name=request.getParameter("name");
		String portraitUri=request.getParameter("portraitUri");
		if (userId != null && !"".equals(userId)) {
			params.put("userId", userId);
			if(name != null && !"".equals(name)){
				params.put("name", name);
			}
			if(portraitUri != null && !"".equals(portraitUri)){
				params.put("portraitUri", portraitUri);
			}
			return RonghubUtil.post(
					"https://api.cn.ronghub.com/user/getToken.json", params);
		} else {
			return null;
		}
	}
	
	//更新用户信息
	@RequestMapping(value = "refresh.json")
	@ResponseBody
	public JSONObject refresh(HttpServletRequest request) throws Exception {
		HashMap params = new HashMap();
		String userId = request.getParameter("userId");
		String name=request.getParameter("name");
		String portraitUri=request.getParameter("portraitUri");
		if (userId != null && !"".equals(userId)) {
			params.put("userId", userId);
			if(name != null && !"".equals(name)){
				params.put("name", name);
			}
			if(portraitUri != null && !"".equals(portraitUri)){
				params.put("portraitUri", portraitUri);
			}
			return RonghubUtil.post(
					"https://api.cn.ronghub.com/user/refresh.json", params);
		} else {
			return null;
		}
	}

	// 是否在线
	@RequestMapping(value = "checkOnline.json")
	@ResponseBody
	public JSONObject checkOnline(HttpServletRequest request) throws Exception {
		HashMap params = new HashMap();
		String userId = request.getParameter("userId");
		if (userId != null && !"".equals(userId)) {
			params.put("userId", userId);
			return RonghubUtil.post(
					"https://api.cn.ronghub.com/user/checkOnline.json", params);
		} else {
			return null;
		}
	}

	// 发单聊消息
	@RequestMapping(value = "privatePublish.json")
	@ResponseBody
	public JSONObject privatePublish(HttpServletRequest request)
			throws Exception {
		HashMap params = new HashMap();
		String fromUserId = request.getParameter("fromUserId");
		String toUserId = request.getParameter("toUserId");
		String objectName = request.getParameter("objectName");
		String content = request.getParameter("content");
		if (fromUserId != null && !"".equals(fromUserId) && toUserId != null
				&& !"".equals(toUserId) && objectName != null
				&& !"".equals(objectName) && content != null
				&& !"".equals(content)) {
			params.put("fromUserId", fromUserId);
			params.put("toUserId", toUserId);
			params.put("objectName", objectName);
			params.put("content", content);
			return RonghubUtil.post(
					"https://api.cn.ronghub.com/message/private/publish.json",
					params);
		} else {
			return null;
		}
	}

	// 发群消息
	@RequestMapping(value = "groupPublish.json")
	@ResponseBody
	public JSONObject groupPublish(HttpServletRequest request) throws Exception {
		HashMap params = new HashMap();
		String fromUserId = request.getParameter("fromUserId");
		String toGroupId = request.getParameter("toGroupId");
		String objectName = request.getParameter("objectName");
		String content = request.getParameter("content");
		if (fromUserId != null && !"".equals(fromUserId) && toGroupId != null
				&& !"".equals(toGroupId) && objectName != null
				&& !"".equals(objectName) && content != null
				&& !"".equals(content)) {
			params.put("fromUserId", fromUserId);
			params.put("toGroupId", toGroupId);
			params.put("objectName", objectName);
			params.put("content", content);
			return RonghubUtil.post(
					"https://api.cn.ronghub.com/message/group/publish.json",
					params);
		} else {
			return null;
		}
	}

	// 创建群
	@RequestMapping(value = "groupCreate.json")
	@ResponseBody
	public JSONObject groupCreate(HttpServletRequest request) throws Exception {
		HashMap params = new HashMap();
		String userIds = request.getParameter("userIds");
		String groupId = request.getParameter("groupId");
		String groupName = request.getParameter("groupName");
		if (userIds != null && !"".equals(userIds) && groupId != null
				&& !"".equals(groupId)) {
			String[] userId = userIds.split("\\|");
			for (String id : userId) {
				params.put("userId", id);
			}
			params.put("groupId", groupId);
			if (groupName != null && !"".equals(groupName)) {
				params.put("groupName", groupName);
			}
			return RonghubUtil.post(
					"https://api.cn.ronghub.com/group/create.json", params);
		} else {
			return null;
		}
	}

	// 加入群
	@RequestMapping(value = "groupJoin.json")
	@ResponseBody
	public JSONObject groupJoin(HttpServletRequest request) throws Exception {
		HashMap params = new HashMap();
		String userIds = request.getParameter("userIds");
		String groupId = request.getParameter("groupId");
		if (userIds != null && !"".equals(userIds) && groupId != null
				&& !"".equals(groupId)) {
			String[] userId = userIds.split("\\|");
			for (String id : userId) {
				params.put("userId", id);
			}
			params.put("groupId", groupId);
			return RonghubUtil.post(
					"https://api.cn.ronghub.com/group/join.json", params);
		} else {
			return null;
		}
	}

	// 退出群
	@RequestMapping(value = "groupQuit.json")
	@ResponseBody
	public JSONObject groupQuit(HttpServletRequest request) throws Exception {
		HashMap params = new HashMap();
		String userIds = request.getParameter("userIds");
		String groupId = request.getParameter("groupId");
		if (userIds != null && !"".equals(userIds) && groupId != null
				&& !"".equals(groupId)) {
			String[] userId = userIds.split("\\|");
			for (String id : userId) {
				params.put("userId", id);
			}
			params.put("groupId", groupId);
			return RonghubUtil.post(
					"https://api.cn.ronghub.com/group/quit.json", params);
		} else {
			return null;
		}
	}

	// 解散群
	@RequestMapping(value = "groupDismiss.json")
	@ResponseBody
	public JSONObject groupDismiss(HttpServletRequest request) throws Exception {
		HashMap params = new HashMap();
		String userId = request.getParameter("userId");
		String groupId = request.getParameter("groupId");
		if (userId != null && !"".equals(userId) && groupId != null
				&& !"".equals(groupId)) {
			params.put("userId", userId);
			params.put("groupId", groupId);
			return RonghubUtil.post(
					"https://api.cn.ronghub.com/group/dismiss.json", params);
		} else {
			return null;
		}
	}
	//刷新群组信息
	@RequestMapping(value = "groupRefresh.json")
	@ResponseBody
	public JSONObject groupRefresh(HttpServletRequest request) throws Exception {
		HashMap params = new HashMap();
		String userId = request.getParameter("userId");
		String groupId = request.getParameter("groupId");
		String groupName= request.getParameter("groupName");
		if (userId != null && !"".equals(userId) && groupId != null
				&& !"".equals(groupId)&& groupName != null
						&& !"".equals(groupName)
				) {
			params.put("userId", userId);
			params.put("groupId", groupId);
			params.put("groupName", groupName);
			return RonghubUtil.post(
					"https://api.cn.ronghub.com/group/refresh.json", params);
		} else {
			return null;
		}
	}
	
}
