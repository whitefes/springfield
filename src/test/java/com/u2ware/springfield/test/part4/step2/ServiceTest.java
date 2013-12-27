package com.u2ware.springfield.test.part4.step2;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;

import com.u2ware.springfield.domain.PaginationRequest;
import com.u2ware.springfield.sample.part4.step1.TargetEntity;
import com.u2ware.springfield.sample.part4.step2.ExtendedBean;
import com.u2ware.springfield.service.EntityService;
import com.u2ware.springfield.test.AbstractRootContextTest;

public class ServiceTest extends AbstractRootContextTest{

	@Autowired @Qualifier("extendedBeanService")
	private EntityService<TargetEntity, ExtendedBean> extendedBeanService;

	
	@Test
	public void testCreate() throws Exception{

		TargetEntity entity = new TargetEntity();
		entity.setId("id");
		entity.setPassword("password");
		entity.setName("name");
		entity.setAge(1);
		
		TargetEntity result = extendedBeanService.create(entity);
		logger.debug(""+result);
	}

	@Test
	public void testFind() throws Exception{

		Pageable pageable = new PaginationRequest(0, 10, "age,desc", "name,asc");
		ExtendedBean query = new ExtendedBean();
		
		Iterable<?> result = extendedBeanService.find(query, pageable);
		logger.debug(""+result);
		if(result != null){
			for(Object row : result){
				logger.debug(""+row);
			}
		}
	}
	
}
