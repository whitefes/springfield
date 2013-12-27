package com.u2ware.springfield.test.part1.step4;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

import com.u2ware.springfield.test.AbstractRootContextTest;



public class SearchEntityControllerTest extends AbstractRootContextTest{

	@Test
	public void testFindByName() throws Exception{
		this.mockMvc.perform(
				get("/part1/step41")
			.param("name", "1"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testFindByAgeBetween() throws Exception{
		this.mockMvc.perform(
				get("/part1/step42")
			.param("age", "10")
			.param("age", "20"))
			.andExpect(status().isOk());
	}
	
}
