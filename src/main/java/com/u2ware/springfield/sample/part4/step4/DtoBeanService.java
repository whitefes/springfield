package com.u2ware.springfield.sample.part4.step4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.u2ware.springfield.repository.EntityRepository;
import com.u2ware.springfield.sample.part4.step1.TargetEntity;
import com.u2ware.springfield.service.EntityService;

@Service
public class DtoBeanService implements EntityService<DtoBean, DtoBean>{

	@Autowired @Qualifier("targetEntityRepository")
	private EntityRepository<TargetEntity, String> targetEntityRepository;

	@Override
	public Object home(DtoBean query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<?> find(DtoBean query, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DtoBean read(DtoBean entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DtoBean createForm(DtoBean entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DtoBean create(DtoBean entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DtoBean updateForm(DtoBean entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DtoBean update(DtoBean entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DtoBean delete(DtoBean entity) {
		// TODO Auto-generated method stub
		return null;
	}

}