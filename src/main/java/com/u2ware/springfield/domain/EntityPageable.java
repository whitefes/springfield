package com.u2ware.springfield.domain;


public interface EntityPageable {

	public int getPageNumber();
	public int getPageSize();
	public boolean isPageEnable();
	
}
