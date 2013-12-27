package com.u2ware.springfield.sample.part1.step2;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.u2ware.springfield.config.Springfield;
import com.u2ware.springfield.repository.QueryMethod;

@Springfield
@QueryMethod("findByIdAndName")
@Entity
public @ToString @NoArgsConstructor @AllArgsConstructor class QueryEntity {

	@Id
	@Getter @Setter private String id;
	@Getter @Setter private String name;
	@Getter @Setter private Integer age;
	@Getter @Setter private Boolean sex;
}