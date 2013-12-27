package com.u2ware.springfield.service;

import com.u2ware.springfield.domain.EntityPageable;

public interface EntityService<T, Q>{
	
	public Object home(Q query);

	public Iterable<?> findForm(Q query, EntityPageable pageable);

	public Iterable<?> find(Q query, EntityPageable pageable);
	
	public T read(T entity);
	
	public T createForm(T entity);
	
	public T create(T entity) ;
	
	public T updateForm(T entity);
	
	public T update(T entity);
	
	public T delete(T entity);

	//public boolean exists(T entity);

	public boolean validate(T entity);

	public boolean reset(T entity);
}

//public void addEventListener(ServiceEventListener listener);
//public void removeEventListener(ServiceEventListener listener);

//public T appendForm(T entity);
//public Object choiceForm(Q request, EntityPageable pageable);
