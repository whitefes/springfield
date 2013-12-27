package com.u2ware.springfield.test.part3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public @ToString @NoArgsConstructor @AllArgsConstructor class FindByIdAndNameOrderByAgeAsc {

	@Getter @Setter public String id;
	@Getter @Setter public String name;
}
