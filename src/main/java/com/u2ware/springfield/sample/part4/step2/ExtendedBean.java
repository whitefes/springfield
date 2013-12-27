package com.u2ware.springfield.sample.part4.step2;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.u2ware.springfield.config.Springfield;
import com.u2ware.springfield.repository.QueryMethod;
import com.u2ware.springfield.sample.part4.step1.TargetEntity;

@Springfield(
	entity=TargetEntity.class
)
@QueryMethod("findByNameAndAge")
public @ToString class ExtendedBean {

	@NotNull @Getter @Setter private String name;
	@NotNull @Getter @Setter private Integer age;
	
}