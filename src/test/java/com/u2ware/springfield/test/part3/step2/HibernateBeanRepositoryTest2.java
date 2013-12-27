package com.u2ware.springfield.test.part3.step2;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.u2ware.springfield.domain.PaginationRequest;
import com.u2ware.springfield.sample.part1.step4.FindByAgeBetween;
import com.u2ware.springfield.sample.part3.step2.HibernateBean;
import com.u2ware.springfield.sample.part3.step2.HibernateBeanComponent;
import com.u2ware.springfield.test.AbstractRootContextTest;
import com.u2ware.springfield.test.part3.FindByIdAndNameOrderByAgeAsc;
import com.u2ware.springfield.test.part3.FindByOrderByNameDesc;
import com.u2ware.springfield.test.part3.MyQuery;

public class HibernateBeanRepositoryTest2 extends AbstractRootContextTest{

	@Autowired 
	private HibernateBeanComponent hibernateBeanComponent;

	private HibernateBean[] createEntities(){
		HibernateBean[] r = new HibernateBean[10];
		for(int i = 1 ; i < 10 ; i++){
			r[i] = new HibernateBean(
					"id"+i , 
					i % 2 == 0 ? "XX"+i+"YY" : "AA"+i+"BB", 
					i % 2 == 0 ? null : i,  
					i % 2 == 0);
			
			logger.warn(""+r[i]);
		}		
		return r;
	}

	@Test
	public void testAll() throws Exception{
		hibernateBeanComponent.resetAll(createEntities());

		////////////////////////////////////////
		//
		////////////////////////////////////////
		Object query = null;
		Pageable pageable = new PaginationRequest(0, 10, "id,desc");
		
		Page<HibernateBean> page = hibernateBeanComponent.findAll(pageable);
		for(HibernateBean p : page){
			logger.warn(""+p);
		}
		Assert.assertEquals(9 , page.getContent().size());
		Assert.assertEquals("id9", page.getContent().get(0).getId());

		////////////////////////////////////////
		//
		////////////////////////////////////////
		query = new FindByOrderByNameDesc();
		pageable = new PageRequest(1, 1, new Sort(Direction.ASC, "age"));

		page = hibernateBeanComponent.findAll(query, pageable);
		for(HibernateBean p : page){
			logger.warn(""+p);
		}
		Assert.assertEquals(1 , page.getContent().size());
		Assert.assertEquals("id6", page.getContent().get(0).getId());
		
		////////////////////////////////////////
		//
		////////////////////////////////////////
		query = new FindByAgeBetween(new Integer[]{3, 5});
		pageable = new PaginationRequest(0, 10);
		page = hibernateBeanComponent.findAll(query, pageable);
		for(HibernateBean p : page){
			logger.warn(""+p);
		}
		Assert.assertEquals(2 , page.getContent().size());
		Assert.assertEquals("id3", page.getContent().get(0).getId());
		
		long result = hibernateBeanComponent.deleteAll(query);
		logger.warn(""+result);
		Assert.assertEquals(2 , result);
		
		////////////////////////////////////////
		//
		////////////////////////////////////////
		query = new FindByIdAndNameOrderByAgeAsc(null, "AA1BB");
		page = hibernateBeanComponent.findAll(query, pageable);
		logger.warn(""+page.getContent().size());
		Assert.assertEquals(1 , page.getContent().size());
		Assert.assertEquals("id1", page.getContent().get(0).getId());

		result = hibernateBeanComponent.deleteAll(query);
		Assert.assertEquals(1 , result);

		
		////////////////////////////////////////
		//
		////////////////////////////////////////
		query = new MyQuery("id8", null);
		page = hibernateBeanComponent.findAll(query, pageable);
		logger.warn(""+page.getContent().size());
		Assert.assertEquals(1 , page.getContent().size());
		Assert.assertEquals("id8", page.getContent().get(0).getId());

		result = hibernateBeanComponent.deleteAll(query);
		Assert.assertEquals(1 , result);
		
		hibernateBeanComponent.resetAll();
	}
}