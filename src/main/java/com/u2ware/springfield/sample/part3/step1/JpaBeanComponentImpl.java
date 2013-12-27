package com.u2ware.springfield.sample.part3.step1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.u2ware.springfield.repository.EntityRepository;


@Component
@Transactional
public class JpaBeanComponentImpl implements JpaBeanComponent{

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired @Qualifier("jpaBeanRepository")
	private EntityRepository<JpaBean,String> jpaBeanRepository;
	
	public Long resetAll(JpaBean... entities) {
		jpaBeanRepository.deleteAll();
		long r = 0;
		for(JpaBean e : entities){
			if(e != null)
				jpaBeanRepository.save(e);
			r++;
		}
		return r;
	}

	public Page<JpaBean> findAll(Pageable pageable) {
		return jpaBeanRepository.findAll(pageable);
	}
	
	public Page<JpaBean> findAll(Object query, Pageable pageable) {
		return jpaBeanRepository.findAll(query, pageable);
	}

	public Long deleteAll(Object query) {
		return jpaBeanRepository.deleteAll(query);
	}

	
}
