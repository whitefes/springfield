package com.u2ware.springfield.test.part4.step3;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;

import com.u2ware.springfield.domain.PaginationRequest;
import com.u2ware.springfield.sample.part4.step1.TargetEntity;
import com.u2ware.springfield.sample.part4.step3.ImplementsBean;
import com.u2ware.springfield.service.EntityService;
import com.u2ware.springfield.test.AbstractRootContextTest;

public class ServiceTest extends AbstractRootContextTest{

	@Autowired @Qualifier("implementsBeanService")
	private EntityService<TargetEntity, ImplementsBean> implementsBeanService;

	
	@Test
	public void testCreate() throws Exception{

		TargetEntity entity = new TargetEntity();
		entity.setId("id");
		entity.setPassword("password");
		entity.setName("name");
		entity.setAge(1);
		
		try{
			TargetEntity result = implementsBeanService.create(entity);
			logger.debug(""+result);
		}catch(Exception e){
			logger.debug("", e);
		}
	}

	@Test
	public void testFind() throws Exception{

		Pageable pageable = new PaginationRequest(0, 10, "age,desc", "name,asc");
		ImplementsBean query = new ImplementsBean();
		
		Iterable<?> result = implementsBeanService.find(query, pageable);
		logger.debug(""+result);
		if(result != null){
			for(Object row : result){
				logger.debug(""+row);
			}
		}
	}
	
}
