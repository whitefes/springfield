package com.u2ware.springfield.test.part3.step1;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.u2ware.springfield.sample.part3.step1.JpaBean;
import com.u2ware.springfield.service.EntityService;
import com.u2ware.springfield.test.AbstractRootContextTest;

public class JpaBeanServiceTest extends AbstractRootContextTest{

	@Autowired @Qualifier("jpaBeanService")
	private EntityService<JpaBean,JpaBean> jpaBeanService;

	@Test
	public void testTransaction() {
		logger.warn(""+jpaBeanService);
		
		JpaBean entity = new JpaBean(new DateTime().toString(), "name", 14 , true);
		jpaBeanService.create(entity);
	}
}
