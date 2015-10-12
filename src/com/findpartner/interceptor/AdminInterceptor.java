package com.findpartner.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AdminInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURL().toString();
		if (url.indexOf("login") > -1){
			if (request.getParameter("userId") != null
					&&!"".equals(request.getParameter("userId"))){
				
			}
		}else{
			
		}
		return true;
	}

}
