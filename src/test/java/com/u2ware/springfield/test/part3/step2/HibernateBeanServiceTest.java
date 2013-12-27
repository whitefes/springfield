package com.u2ware.springfield.test.part3.step2;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.u2ware.springfield.sample.part3.step2.HibernateBean;
import com.u2ware.springfield.service.EntityService;
import com.u2ware.springfield.test.AbstractRootContextTest;

public class HibernateBeanServiceTest extends AbstractRootContextTest{

	@Autowired @Qualifier("hibernateBeanService")
	private EntityService<HibernateBean,HibernateBean> hibernateBeanService;

	@Test
	public void testTransaction() throws Exception {
		logger.warn(""+hibernateBeanService);
		try{
			HibernateBean entity = new HibernateBean(new DateTime().toString(), "name", 14 , true);
			hibernateBeanService.create(entity);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
