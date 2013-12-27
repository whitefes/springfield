package com.u2ware.springfield.test.part1.step2;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

import com.u2ware.springfield.test.AbstractRootContextTest;



public class QueryEntityControllerTest extends AbstractRootContextTest{

	@Test
	public void testFind1() throws Exception{
		this.mockMvc.perform(
				get("/part1/step2"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testFind2() throws Exception{
		this.mockMvc.perform(
				get("/part1/step2")
			.param("id", "1"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testFind3() throws Exception{
		this.mockMvc.perform(
				get("/part1/step2")
			.param("id", "1")
			.param("name", "1"))
			.andExpect(status().isOk());
	}
	
}
