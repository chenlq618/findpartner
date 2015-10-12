package com.findpartner.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;

import com.findpartner.bean.Feedback;
import com.findpartner.dao.FeedbackDao;
import com.findpartner.dao.VersionDao;
import com.findpartner.util.Constants;

@Controller
public class UtilController {
	
	@Autowired(required = true)
	VersionDao versionDao;
	@Autowired(required = true)
	FeedbackDao feedbackDao;
	
	@RequestMapping(value = "getVersion.json")
	@ResponseBody
	public Map<String, Object> getVersion(HttpServletRequest request)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("version", versionDao.getVersion());
		return result;
	}
	
	
	
	@RequestMapping(value = "getTalkToken.json")
	@ResponseBody
	public Map<String, Object> getTalkToken(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Jedis jedis = new Jedis(Constants.RedisIp);
		String phone=request.getParameter("phone");
		String talkToken="";
		if(jedis.get(phone+"talkToken")!=null && !"".equals(jedis.get(phone+"talkToken"))){
			talkToken=jedis.get(phone+"talkToken");
		}else{
			//talkToken=RonghubUtil.getTalkToken(phone);
			jedis.set(phone+"talkToken", talkToken);
		}
		result.put("talkToken",talkToken );
		return result;
	}
	
	@RequestMapping(value = "feedback.json")
	@ResponseBody
	public Map<String, Object> feedback(HttpServletRequest request)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Feedback feedback=new Feedback();
		feedback.setUserId(request.getParameter("phone"));
		feedback.setFeedback(request.getParameter("feedback"));
		feedbackDao.insert(feedback);
		result.put("code", 1);
		return result;
	}
	

}
