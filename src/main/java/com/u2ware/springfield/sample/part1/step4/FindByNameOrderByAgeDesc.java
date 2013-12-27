package com.u2ware.springfield.sample.part1.step4;

import lombok.Getter;
import lombok.Setter;

import com.u2ware.springfield.config.Springfield;

@Springfield(
	entity=SearchEntity.class,
	topLevelMapping="/part1/step41"
)
public class FindByNameOrderByAgeDesc {
	
	@Getter @Setter private String name;
}
