package com.u2ware.springfield.sample.part1.step3;

import lombok.Getter;
import lombok.Setter;

import com.u2ware.springfield.config.Springfield;
import com.u2ware.springfield.repository.QueryMethod;

@Springfield(
	entity=FindEntity.class
)
@QueryMethod("findByNameAndAge")
public class FindEntityBean {

	@Getter @Setter private String name;
	@Getter @Setter private Integer age;
}
