package com.u2ware.springfield.sample.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired(required=false)
	private MessageSourceAccessor messageSource;
	
	
	@RequestMapping(value={"/","/index.html"})
	public String home(){
		try{
			logger.warn(messageSource.getMessage("application.list"));
			logger.warn(messageSource.getMessage("application.list"));
			logger.warn(messageSource.getMessage("application.list"));
			logger.warn(messageSource.getMessage("application.list"));
			logger.warn(messageSource.getMessage("application.list"));
			logger.warn(messageSource.getMessage("application.list"));
			logger.warn(messageSource.getMessage("application.list"));
			logger.warn(messageSource.getMessage("application.list"));
			logger.warn(messageSource.getMessage("application.list"));
			logger.warn(messageSource.getMessage("application.list"));
			logger.warn(messageSource.getMessage("application.list"));
			logger.warn(messageSource.getMessage("application.list"));
			logger.warn(messageSource.getMessage("application.list"));
			logger.warn(messageSource.getMessage("application.list"));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "/index.html";
	}
	

	
}
