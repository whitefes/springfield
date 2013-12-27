package com.u2ware.springfield.sample.part3.step4;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.u2ware.springfield.config.Springfield;
import com.u2ware.springfield.config.Springfield.Strategy;

@Springfield(
	strategy=Strategy.JDBC,
	identity={"id"}
)
public @ToString @NoArgsConstructor @AllArgsConstructor class JdbcBean {

	@Getter @Setter private String id;
	@Getter @Setter private String name;
	@Getter @Setter private Integer age;
	@Getter @Setter private Boolean sex;
}