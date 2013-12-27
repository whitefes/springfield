package com.u2ware.springfield.sample.part4.step1;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import com.u2ware.springfield.config.Springfield;

@Springfield
@Entity
public class TargetEntity {

	@Id
	@NotNull @Getter @Setter private String id;
	@NotNull @Getter @Setter private String password;
	@NotNull @Getter @Setter private String name;
	@NotNull @Getter @Setter private Integer age;
}