package com.u2ware.springfield.sample.part3.step1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.u2ware.springfield.repository.EntityRepository;
import com.u2ware.springfield.service.EntityServiceImpl;


@Service("jpaBeanService")
public class JpaBeanService extends EntityServiceImpl<JpaBean, JpaBean>{

	@Autowired @Qualifier("jpaBeanRepository")
	private EntityRepository<JpaBean,String> jpaBeanRepository;
	
	@Autowired @Qualifier("dataSourceJpaTransactionTemplate")
	private TransactionTemplate transactionTemplate;
	
	@Override
	public EntityRepository<JpaBean, String> getRepository() {
		return jpaBeanRepository;
	}
	@Override
	protected TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	
	@Override
	//@Transactional
	@Transactional("dataSourceJpaTransactionManager")
	public JpaBean create(JpaBean entity) {
		JpaBean r = jpaBeanRepository.save(entity);
		//Integer.parseInt("aaaa");
		return r;
	}
}
