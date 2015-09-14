package com.findpartner.interceptor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.findpartner.util.Constants;

import redis.clients.jedis.Jedis;
import sun.misc.BASE64Encoder;

public class JsonInterceptor extends HandlerInterceptorAdapter {

	
	private String urlFormat = ".*L.json";// 定义权限管理的url格式
	Jedis jedis = new Jedis(Constants.RedisIp);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

				
		String url = request.getRequestURL().toString();
		
		if (url.indexOf("login.json") > -1 || url.indexOf("regist.json")>-1) {
			if (request.getParameter("phone") != null
					&&!"".equals(request.getParameter("phone"))
					&& request.getParameter("deviceId") != null
					&& !"".equals(request.getParameter("deviceId"))) {
				String token = generateTokeCode(request.getParameter("phone"),
						request.getParameter("deviceId"));
				request.setAttribute("token", token);
				jedis.set(request.getParameter("phone"), token);
				jedis.set(request.getParameter("phone") + "DV",
						request.getParameter("deviceId"));
			} else {
				request.setAttribute("info", "帐号和设备ID不能为空");
				request.getRequestDispatcher("/user/reLogin.json").forward(
						request, response);
				return false;
			}
		}
		// 退出操作
		if (url.indexOf("exit.json") > 0) {
			if ((request.getParameter("phone") != null)
					&& isLogin(request.getParameter("phone"),
							request.getParameter("deviceId"),
							request.getParameter("token"))) {
				jedis.del(request.getParameter("phone"));
				jedis.del(request.getParameter("phone") + "DV");
			}
		}

		// 如果要权限验证的，先验证
		if (url.matches(urlFormat)) {

			// 如果没登陆，重定向到登陆页面
			if ((request.getParameter("phone") == null)
					) {
				request.setAttribute("info", "使用该功能前，请先登陆");
				request.getRequestDispatcher("/user/reLogin.json").forward(
						request, response);
				return false;
			}
			if(!isLogin(request.getParameter("phone"),
					request.getParameter("deviceId"),
					request.getParameter("token"))){
				request.setAttribute("info", "你的设备发生变化，请重要登陆");
				request.getRequestDispatcher("/user/reLogin.json").forward(
						request, response);
				return false;
			}

		}

		return true;
	}

	public boolean isLogin(String phone, String deviceId, String token) {
		if (token != null && deviceId != null && jedis.get(phone) .equals(token)
				&& jedis.get(phone + "DV").equals(deviceId) ) {
			return true;
		}else{
			return false;
		}
		
	}

	public synchronized String generateTokeCode(String phone, String deviceID) {
		String value = System.currentTimeMillis() + phone + deviceID;
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte[] b = md.digest(value.getBytes());// 产生数据的指纹
			BASE64Encoder be = new BASE64Encoder();
			be.encode(b);
			return be.encode(b);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
