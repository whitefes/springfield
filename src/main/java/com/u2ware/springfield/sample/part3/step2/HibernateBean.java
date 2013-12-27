package com.u2ware.springfield.sample.part3.step2;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.u2ware.springfield.config.Springfield;
import com.u2ware.springfield.config.Springfield.Strategy;

@Springfield(
	strategy=Strategy.HIBERNATE
)
@Entity
public @ToString @NoArgsConstructor @AllArgsConstructor class HibernateBean {

	@Id
	@Getter @Setter private String id;
	@Getter @Setter private String name;
	@Getter @Setter private Integer age;
	@Getter @Setter private Boolean sex;

}