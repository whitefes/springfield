package com.u2ware.springfield.test;

/*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
*/
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.SmartValidator;

public class RootContextTest extends AbstractRootContextTest{
	
	@Autowired(required=false)
	private MessageSourceAccessor messageSourceAccessor;
	

	@Autowired(required=false)
	private SessionFactory sessionFactory;
	
	@PersistenceContext(name="dataSourceJpa")
	private EntityManager entityManager;
	
	@Test
	public void beans() throws Exception{
		logger.warn("======================================================================Beans");
		
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		Arrays.sort(beanNames, 0, beanNames.length);
		
		for(String name : beanNames){
			logger.warn(name+"="+applicationContext.getBean(name).getClass());
		}
		
		
		
		logger.warn("======================================================================PlatformTransactionManager");
		for(String name : applicationContext.getBeanNamesForType(PlatformTransactionManager.class)){
			logger.warn(name+"="+applicationContext.getBean(name));
		}

		logger.warn("======================================================================PlatformTransactionManager");
		for(String name : applicationContext.getBeanNamesForType(TransactionTemplate.class)){
			logger.warn(name+"="+applicationContext.getBean(name));
		}
		
		
		logger.warn("======================================================================MessageSource");
		for(String name : applicationContext.getBeanNamesForType(MessageSource.class)){
			logger.warn(name+"="+applicationContext.getBean(name));
		}

		logger.warn("======================================================================MessageSource");
		for(String name : applicationContext.getBeanNamesForType(JdbcTemplate.class)){
			logger.warn(name+"="+applicationContext.getBean(name));
		}
		
		logger.warn("======================================================================MessageSource");
		for(String name : applicationContext.getBeanNamesForType(SmartValidator.class)){
			logger.warn(name+"="+applicationContext.getBean(name));
		}
		
		logger.warn("======================================================================MessageSource");
		if(messageSourceAccessor != null){
			try{
				logger.warn(messageSourceAccessor.getMessage("application.list"));
			}catch(Exception e){
				
			}
		}
		logger.warn("======================================================================MessageSource");
		logger.warn(""+entityManager);
	}
}
