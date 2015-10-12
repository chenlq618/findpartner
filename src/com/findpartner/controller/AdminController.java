package com.findpartner.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.findpartner.service.UserService;

@Controller
@RequestMapping(value = "admin")
public class AdminController {
	
	
	@RequestMapping(value = "login.action")
	public String login(HttpServletRequest request) throws Exception {
		System.out.println(request.getParameter("userId"));
		return "success.jsp";
	}


}
