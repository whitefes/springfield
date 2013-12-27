package com.u2ware.springfield.sample.part3.step2;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface HibernateBeanComponent {

	public Long resetAll(HibernateBean... entities);
	public Page<HibernateBean> findAll(Object query, Pageable pageable);
	public Page<HibernateBean> findAll(Pageable pageable);
	public Long deleteAll(Object query);
	
	
	
}
