package com.u2ware.springfield.sample.part5.step1;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.u2ware.springfield.config.Springfield;
import com.u2ware.springfield.config.Springfield.Strategy;
import com.u2ware.springfield.repository.QueryMethod;
import com.u2ware.springfield.sample.part4.step1.TargetEntity;

@Springfield(
	strategy=Strategy.JPA, 
	entity=TargetEntity.class
)
@QueryMethod("findByIdAndPasswordOrderByAgeDesc")
public @ToString class CheckBean {
	
	@Getter @Setter private String id;
	@Getter @Setter private String password;
}