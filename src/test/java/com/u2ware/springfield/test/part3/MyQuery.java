package com.u2ware.springfield.test.part3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.u2ware.springfield.repository.QueryMethod;

@QueryMethod("findByIdAndNameOrderByAgeAsc")
public @ToString @NoArgsConstructor @AllArgsConstructor  class  MyQuery {

	@Getter @Setter public String id;
	@Getter @Setter public String name;
}
