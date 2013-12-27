package com.u2ware.springfield.sample.part1.step1;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.u2ware.springfield.config.Springfield;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Springfield
@Entity
public @ToString @NoArgsConstructor @AllArgsConstructor class SimpleEntity {

	@Id
	@Getter @Setter private String id;
	@Getter @Setter private String name;
	@Getter @Setter private Integer age;
	@Getter @Setter private Boolean sex;
}