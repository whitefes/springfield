package com.u2ware.springfield.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import com.u2ware.springfield.domain.EntityInformation;
import com.u2ware.springfield.domain.EntityPageable;
import com.u2ware.springfield.domain.EntitySortable;
import com.u2ware.springfield.domain.Pagination;
import com.u2ware.springfield.domain.PaginationRequest;
import com.u2ware.springfield.service.EntityService;
import com.u2ware.springfield.validation.EntityValidator;
import com.u2ware.springfield.validation.RejectableException;

/**
 *                        /{topLevelMapping}/{methodLevelMapping}
 * 		Home:         GET /{path}/
 * 		List        : GET /{path}
 * 		Create Form : GET /{path}/new
 * 		Create     : POST /{path}/new
 * 		Read        : GET /{path}/{id}
 * 		Update Form : GET /{path}/{id}/edit
 * 		Update      : PUT /{path}/{id}/edit
 * 		Delete   : DELETE /{path}/{id}/edit
 * 
 * @author admin
 *
 * @param <T>
 * @param <Q>
 */
public class EntityControllerImpl<T,Q> extends EntityController<T,Q>{
	
	protected EntityControllerImpl(EntityInformation<T,Q> information, EntityService<T,Q> service, EntityValidator<T,Q> validator) {
		super.setInformation(information);
		super.setService(service);
		super.setValidator(validator);
	}
	

	
	@RequestMapping(method={RequestMethod.GET, RequestMethod.POST}, value="/")
	public String home(Model model, @ModelAttribute(MODEL_QUERY)Q query,BindingResult errors) throws Exception{

		logger.warn("request method: home()");
		logger.warn("request model : "+query);	
		
		getValidator().home(query, errors);
		if(errors.hasErrors()){
			return resolveViewName(model, errors, "home", null, query, null, null);
		}

		try{
			Object result = getService().home(query);
			return resolveViewName(model, errors, "home", null, query, null, result);

		}catch(RejectableException e){
			super.validate(errors, e);
			return resolveViewName(model, errors, "home", null, query, null, null);
		}
	}
	
	
	@RequestMapping(method={RequestMethod.GET, RequestMethod.POST}, value="")
	public String find(
			@RequestParam(required=false,value=ENABLE_PARAMETER_NAME,defaultValue="true")Boolean pageEnable,
			Model model, Pageable pageable, @ModelAttribute(MODEL_QUERY)Q query, BindingResult errors) throws Exception{

		logger.warn("request method: find()");
		logger.warn("request model : "+query);	

		boolean isNew = false;
		int page = 0;
		int size = 20;
		boolean enable = true;
		String[] sortSource = null;
		if(ClassUtils.isAssignableValue(EntitySortable.class, query)){
			EntitySortable t = (EntitySortable)query;
			sortSource = t.getSortSource();
			isNew = true;
		}
		if(ClassUtils.isAssignableValue(EntityPageable.class, query)){
			EntityPageable t = (EntityPageable)query;
			page = t.getPageNumber();
			size = t.getPageSize();
			enable = t.isPageEnable();
			isNew = true;
		}
		Pageable p = null;
		if(isNew && enable){
			p = new PaginationRequest(page, size, sortSource);
			logger.warn("request model : new pageable="+p);	
		}else if(pageEnable){
			p = pageable;
			logger.warn("request model : pageable="+p);	
		}
		
		
		getValidator().findForm(query, errors);
		if(errors.hasErrors()){
			return resolveViewName(model, errors, "find", null, query, p, new Pagination<T>());
		}
		
		try{
			Iterable<?> result = getService().find(query, p);
			if(result == null)
				result = new Pagination<T>();
			
			return resolveViewName(model, errors, "find", null, query, p, result);
		}catch(RejectableException e){
			super.validate(errors, e);
			return resolveViewName(model, errors, "find", null, query, p, new Pagination<T>());
		}
	}
	
	
	@RequestMapping(method=RequestMethod.GET, value="/"+COMMAND_ID_PATH+"")
	public String read(Model model, @ModelAttribute(MODEL_ENTITY)T entity,BindingResult errors) throws Exception{
		
		logger.warn("request method: read()");
		logger.warn("request model : "+entity);	

		getValidator().read(entity, errors);
		if(errors.hasErrors()){
			return resolveViewName(model, errors, "read", entity, null, null, null);
		}

		try{
			T newEntity = getService().read(entity);
			if(newEntity == null) 
				throw new HttpClientErrorException(HttpStatus.NOT_FOUND);		

			return resolveViewName(model, errors, "read", newEntity, null, null, null);

		}catch(RejectableException e){
			super.validate(errors, e);
			return resolveViewName(model, errors, "read", entity, null, null, null);
		}
	}

	
	@RequestMapping(method=RequestMethod.GET, value="/new")
	public String createForm(Model model, @ModelAttribute(MODEL_ENTITY)T entity, BindingResult errors) throws Exception{

		logger.warn("request method: createForm()");
		logger.warn("request model : "+entity);	

		getValidator().createForm(entity, errors);
		if(errors.hasErrors()){
			return resolveViewName(model, errors, "createForm", entity, null, null, null);
		}
		
		try{
			T newEntity = getService().createForm(entity);
			return resolveViewName(model, errors, "createForm", newEntity, null, null, null);
			
		}catch(RejectableException e){
			super.validate(errors, e);
			return resolveViewName(model, errors, "createForm", entity, null, null, null);
		}
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="/new")
	public String create(Model model, @ModelAttribute(MODEL_ENTITY) T entity, BindingResult errors) throws Exception{
		
		logger.warn("request method: create()");
		logger.warn("request model : "+entity);	

		getValidator().create(entity, errors);
		if(errors.hasErrors()){
			return resolveViewName(model, errors, "createForm", entity, null, null, null);
		}

		try{
			T newEntity = getService().create(entity);		
			return resolveViewName(model, errors,"create", newEntity, null, null, null);
			
		}catch(RejectableException e){
			super.validate(errors, e);
			return resolveViewName(model, errors, "createForm", entity, null, null, null);
		}
	}
	
	
	@RequestMapping(method=RequestMethod.GET, value="/"+COMMAND_ID_PATH+"/edit")
	public String updateForm(Model model, @ModelAttribute(MODEL_ENTITY)T entity, BindingResult errors) throws Exception{
		
		logger.warn("request method: updateForm()");
		logger.warn("request model : "+entity);	

		getValidator().updateForm(entity, errors);
		if(errors.hasErrors()){
			return resolveViewName(model, errors, "updateForm", entity, null, null, null);
		}

		try{
			T newEntity = getService().updateForm(entity);
			return resolveViewName(model, errors, "updateForm", newEntity, null, null, null);

		}catch(RejectableException e){
			super.validate(errors, e);
			return resolveViewName(model, errors, "updateForm", entity, null, null, null);
		}
	}
	
	
	@RequestMapping(method=RequestMethod.PUT, value="/"+COMMAND_ID_PATH+"/edit")
	public String update(Model model, @ModelAttribute(MODEL_ENTITY) T entity,BindingResult errors) throws Exception{

		logger.warn("request method: update()");
		logger.warn("request model : "+entity);	

		getValidator().update(entity, errors);
		if(errors.hasErrors()){
			return resolveViewName(model, errors, "updateForm", entity, null, null, null);
		}
		
		try{
			T newEntity = getService().update(entity);
			return resolveViewName(model, errors, "update", newEntity, null, null, null);
			
		}catch(RejectableException e){
			super.validate(errors, e);
			return resolveViewName(model, errors, "updateForm", entity, null, null, null);
		}
	}

	
	@RequestMapping(method=RequestMethod.DELETE, value="/"+COMMAND_ID_PATH+"/edit")
	public String delete(Model model, @ModelAttribute(MODEL_ENTITY)T entity, BindingResult errors) throws Exception{
		
		logger.warn("request method: delete()");
		logger.warn("request model : "+entity);	

		getValidator().delete(entity, errors);
		if(errors.hasErrors()){
			return resolveViewName(model, errors, "read", entity, null, null, null);
		}

		try{
			T newEntity = getService().delete(entity);
			return resolveViewName(model, errors, "delete", newEntity, null, null, null);
		}catch(RejectableException e){
			super.validate(errors, e);
			return resolveViewName(model, errors, "read", entity, null, null, null);
		}
	}
}