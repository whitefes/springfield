package com.u2ware.springfield.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

public class EntityControllerInterceptor implements org.springframework.web.servlet.HandlerInterceptor{

	private static final Logger logger = LoggerFactory.getLogger(EntityControllerInterceptor.class);

	
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		logger.warn("preHandle : "+request.getRequestURI());
		return true;
	}

	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		logger.warn("postHandle : "+request.getRequestURI());
	}

	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex)throws Exception {
		logger.warn("afterCompletion : "+request.getRequestURI());
	}
}
