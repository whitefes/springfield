package com.u2ware.springfield.sample.part3.step1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface JpaBeanComponent {

	public Long resetAll(JpaBean... entities);
	public Page<JpaBean> findAll(Object query, Pageable pageable);
	public Page<JpaBean> findAll(Pageable pageable);
	public Long deleteAll(Object query);
	
}
