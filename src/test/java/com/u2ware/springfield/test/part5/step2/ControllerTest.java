package com.u2ware.springfield.test.part5.step2;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

import com.u2ware.springfield.test.AbstractRootContextTest;



public class ControllerTest extends AbstractRootContextTest{

	//////////////////////////////////////////////////////////
	//
	//////////////////////////////////////////////////////////
	@Test
	public void testCustomLayer1() throws Exception{
		this.mockMvc.perform(
				get("/part5/step2"))
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	
}
