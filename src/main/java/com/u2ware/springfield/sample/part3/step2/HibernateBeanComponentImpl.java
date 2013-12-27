package com.u2ware.springfield.sample.part3.step2;

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
@Transactional("hsqlDataSourceHibernateTransactionManager")
public class HibernateBeanComponentImpl implements HibernateBeanComponent{

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired @Qualifier("hibernateBeanRepository")
	private EntityRepository<HibernateBean,String> hibernateBeanRepository;
	
	
	public Long resetAll(HibernateBean... entities) {
		hibernateBeanRepository.deleteAll();
		long r = 0;
		for(HibernateBean e : entities){
			if(e != null)
				hibernateBeanRepository.save(e);
			r++;
		}
		return r;
	}
	
	public Page<HibernateBean> findAll(Pageable pageable) {
		return hibernateBeanRepository.findAll(pageable);
	}
	
	public Page<HibernateBean> findAll(Object query, Pageable pageable) {
		return hibernateBeanRepository.findAll(query, pageable);
	}

	@Override
	public Long deleteAll(Object query) {
		return hibernateBeanRepository.deleteAll(query);
	}
	
	

}
