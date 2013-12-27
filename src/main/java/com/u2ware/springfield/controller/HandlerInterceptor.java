package com.u2ware.springfield.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.ModelAndView;

public class HandlerInterceptor implements org.springframework.web.servlet.HandlerInterceptor{

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		logger.warn("preHandle : "+LocaleContextHolder.getLocale()+" "+request.getRequestURI()+" "+handler);
		return true;
	}

	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		logger.warn("postHandle : "+LocaleContextHolder.getLocale()+" "+request.getRequestURI()+" "+handler);
	}

	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex)throws Exception {
		logger.warn("afterCompletion : "+LocaleContextHolder.getLocale()+" "+request.getRequestURI()+" "+handler);
	}


}
