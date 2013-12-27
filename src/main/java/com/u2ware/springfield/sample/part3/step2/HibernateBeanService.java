package com.u2ware.springfield.sample.part3.step2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.u2ware.springfield.repository.EntityRepository;
import com.u2ware.springfield.service.EntityServiceImpl;


@Service("hibernateBeanService")
public class HibernateBeanService extends EntityServiceImpl<HibernateBean, HibernateBean>{

	@Autowired @Qualifier("hibernateBeanRepository")
	private EntityRepository<HibernateBean,String> hibernateBeanRepository;
	
	@Autowired @Qualifier("dataSourceHibernateTransactionTemplate")
	private TransactionTemplate transactionTemplate;

	@Override
	public EntityRepository<HibernateBean, String> getRepository() {
		return hibernateBeanRepository;
	}
	
	@Override
	protected TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}
	
	@Override
	@Transactional("dataSourceHibernateTransactionManager")
	public HibernateBean create(HibernateBean entity) {
		HibernateBean r = hibernateBeanRepository.save(entity);
		//Integer.parseInt("aaaa");
		return r;
	}	
}
