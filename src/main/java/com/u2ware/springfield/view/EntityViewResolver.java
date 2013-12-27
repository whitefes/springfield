package com.u2ware.springfield.view;

import java.util.Locale;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class EntityViewResolver implements BeanNameAware{

	private View view;
	private ViewResolver viewResolver;
	private String beanName;
	private String baseExtensions;	
	private String[] extensions;
	private boolean resourceRequired =false;

	public EntityViewResolver(View view){
		this.view = view;
	}
	public EntityViewResolver(ViewResolver viewResolver){
		this.viewResolver = viewResolver;
	}

	public boolean isResourceRequired() {
		return resourceRequired;
	}
	public void setResourceRequired(boolean resourceRequired) {
		this.resourceRequired = resourceRequired;
	}
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public String[] getExtensions() {
		return extensions;
	}
	public void setExtensions(String... extensions) {
		this.extensions = extensions;
	}
	public String getBaseExtensions() {
		return baseExtensions;
	}
	public void setBaseExtensions(String baseExtensions) {
		this.baseExtensions = baseExtensions;
	}

	public View resolveViewName(String viewName, Locale locale) throws Exception {
		return view != null ? view : viewResolver.resolveViewName(viewName, locale);
	}

	
	public String toString(){
		return view != null ? view.toString() : viewResolver.toString();
	}
}