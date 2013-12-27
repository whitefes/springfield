package com.u2ware.springfield.sample.part4.step1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.u2ware.springfield.repository.EntityRepository;
import com.u2ware.springfield.service.EntityServiceImpl;


@Service
public class TargetEntityService extends EntityServiceImpl<TargetEntity, TargetEntity>{

	@Autowired @Qualifier("targetEntityRepository")
	private EntityRepository<TargetEntity, String> targetEntityRepository;

	@Autowired @Qualifier("dataSourceJpaTransactionTemplate")
	private TransactionTemplate transactionTemplate;

	@Override
	protected EntityRepository<TargetEntity, String> getRepository() {
		return targetEntityRepository;
	}
	
	@Override
	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}
	
	@Override
	public Iterable<?> find(TargetEntity request, Pageable pageable) {
		logger.debug("Overide findForm ");
		logger.debug("Overide findForm ");
		logger.debug("Overide findForm ");
		logger.debug("Overide findForm ");
		logger.debug("Overide findForm ");
		return super.find(request, pageable);
	}
}