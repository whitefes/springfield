package com.u2ware.springfield.repository;

import java.lang.reflect.ParameterizedType;


public abstract class TemplateCallback<R, X> {
	
	public abstract R doInTemplate(X template);

	
	public final Class<?> getType(){
		ParameterizedType type = (ParameterizedType)getClass().getGenericSuperclass();
		Class<?> r = (Class<?>)(type.getActualTypeArguments()[1]);
		return r;
	}
	
}



/*
ParameterizedType type = (ParameterizedType)callback.getClass().getGenericSuperclass();
Class<?> r = (Class<?>)(type.getActualTypeArguments()[1]);

if(ClassUtils.isAssignable(EntityManager.class, r)){
	return callback.doInTemplate((X)entityManager);
	
}else if(ClassUtils.isAssignable(JPAQueryDslExecutor.class, r)){
	return callback.doInTemplate((X)support);
	
}else{
	throw new RuntimeException("EntityRepositoryCallback Generic type is wrong.");
}
*/
