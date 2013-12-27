package com.u2ware.springfield.sample.part2.step2;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.u2ware.springfield.config.Springfield;

@Springfield(
	methodLevelMapping={
		"*.html",
		"*.do"
	},
	attributesCSV=
		"springfield.view.extension.do={springfieldJsonViewResolver}" 
)
@Entity
public @ToString @NoArgsConstructor @AllArgsConstructor class CustomExtensionsBean {

	@Id
	@Getter @Setter private String id;
	@Getter @Setter private String name;
	@Getter @Setter private Integer age;
	@Getter @Setter private Boolean sex;
}