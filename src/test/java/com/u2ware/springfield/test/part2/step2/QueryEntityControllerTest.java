package com.u2ware.springfield.test.part2.step2;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

import com.u2ware.springfield.test.AbstractRootContextTest;



public class QueryEntityControllerTest extends AbstractRootContextTest{

	@Test
	public void testMappingDo() throws Exception{
		this.mockMvc.perform(post("/part2/step2.do")
			)
			.andDo(print())
			.andExpect(status().isOk());
	}
	
}
