package com.u2ware.springfield.sample.part4.step4;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import com.u2ware.springfield.config.Springfield;
import com.u2ware.springfield.config.Springfield.Strategy;


@Springfield(
	strategy=Strategy.DTO,
	identity={"id"}
)
public class DtoBean {

	@NotNull @Getter @Setter private String id;
	@NotNull @Getter @Setter private String password;
	@NotNull @Getter @Setter private String name;
	@NotNull @Getter @Setter private Integer age;
	
}