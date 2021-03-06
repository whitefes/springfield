package com.u2ware.springfield.sample.home.code;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Code {

	@EmbeddedId @Valid
	private @Getter @Setter CodeId codeId;
	
	@NotNull
	private @Getter @Setter String name;
	
	
}
