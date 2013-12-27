package com.u2ware.springfield.sample.part4.step3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.u2ware.springfield.repository.EntityRepository;
import com.u2ware.springfield.sample.part4.step1.TargetEntity;
import com.u2ware.springfield.service.EntityService;
import com.u2ware.springfield.validation.RejectableException;

@Service
@Transactional
public class ImplementsBeanService implements EntityService<TargetEntity, ImplementsBean> {

	@Autowired @Qualifier("targetEntityRepository")
	private EntityRepository<TargetEntity, String> targetEntityRepository;

	@Override
	public Object home(ImplementsBean query) {
		return null;
	}

	@Override
	public Iterable<?> find(ImplementsBean query, Pageable pageable) {
		return targetEntityRepository.findAll(query);
	}

	@Override
	public TargetEntity read(TargetEntity entity) {
		return null;
	}

	@Override
	public TargetEntity createForm(TargetEntity entity) {
		return null;
	}

	@Override
	public TargetEntity create(TargetEntity entity) {
		if(targetEntityRepository.exists(entity)){
			throw new RejectableException("id" , "Duplication", "중복키입니다.");
		}
		return targetEntityRepository.save(entity);
	}


	@Override
	public TargetEntity updateForm(TargetEntity entity) {
		return null;
	}

	@Override
	public TargetEntity update(TargetEntity entity) {
		return null;
	}

	@Override
	public TargetEntity delete(TargetEntity entity) {
		return null;
	}
}