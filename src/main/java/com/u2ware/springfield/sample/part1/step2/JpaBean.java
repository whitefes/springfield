package com.u2ware.springfield.sample.part1.step2;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.u2ware.springfield.config.Springfield;
import com.u2ware.springfield.config.Springfield.Strategy;

@Springfield(
	strategy=Strategy.JPA
)
@Entity
public @ToString class JpaBean {

	@Id
	@NotNull @Getter @Setter private String id;
	@NotNull @Getter @Setter private String password;
	@NotNull @Getter @Setter private String name;
	@NotNull @Getter @Setter private Integer age;

	public JpaBean() {

	}
	public JpaBean(String id) {
		this.id = id;
	}
	public JpaBean(String id, String password, String name, Integer age) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.age = age;
	}
}