package com.u2ware.springfield.sample.part4.step3;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.u2ware.springfield.config.Springfield;
import com.u2ware.springfield.repository.QueryMethod;
import com.u2ware.springfield.sample.part4.step1.TargetEntity;

@Springfield(
	entity=TargetEntity.class
)
@QueryMethod("findByIdAndPassword")
public @ToString class ImplementsBean {
	
	@Getter @Setter private String id;
	@Getter @Setter private String password;
}