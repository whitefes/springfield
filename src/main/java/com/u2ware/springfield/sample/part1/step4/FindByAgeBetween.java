package com.u2ware.springfield.sample.part1.step4;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.u2ware.springfield.config.Springfield;


@Springfield(
	entity=SearchEntity.class,
	topLevelMapping="/part1/step42"
)
public @ToString @NoArgsConstructor @AllArgsConstructor class FindByAgeBetween {

	private @Getter @Setter Integer[] age;
}
