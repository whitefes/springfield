package com.u2ware.springfield.test.part1.step1;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;

import com.u2ware.springfield.test.AbstractRootContextTest;



public class SimpleEntityControllerTest extends AbstractRootContextTest{

	@Test
	public void testFind() throws Exception{
		this.mockMvc.perform(get("/part1/step1")
			.param("model_query_pageable.enable", "true")
			.param("model_query_pageable.pageNumber", "4")
			.param("model_query_pageable.pageSize", "7")
			.param("model_query_pageable.sort", "id")
			.param("model_query_pageable.sort", "name,desc")
			
		)
		.andDo(print())
		.andExpect(status().isOk());
	}
}
