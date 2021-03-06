package com.u2ware.springfield.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface EntityRepository<T, ID extends Serializable> {

	public <X> X getTemplate();
	
	public boolean exists(ID id) ;
	public boolean exists(T entity) ;
	
	public T read(ID id) ;
	public T read(T entity) ;
	public T create(T entity) ;
	public T update(T entity) ;
	public T createOrUpdate(T entity) ;
	public void delete(T entity);
	public void delete(ID id);
	
	public long count();
	public List<T> findAll();
	public List<T> findAll(Sort sort);
	public Page<T> findAll(Pageable pageable);
	public void deleteAll();

	public long count(Object query);
	public List<T> findAll(Object query);
	public List<T> findAll(Object query, Sort sort);
	public Page<T> findAll(Object query, Pageable pageable);
	public void deleteAll(Object query);

}



/*
public List<T> findAll(String query , Object[] param);
public List<T> findAll(String query , Object[] param, Sort sort);
public Page<T> findAll(String query , Object[] param, Pageable pageable);
public long count(String query , Object[] param);
*/
