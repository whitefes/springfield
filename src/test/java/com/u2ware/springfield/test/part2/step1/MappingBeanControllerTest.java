package com.u2ware.springfield.test.part2.step1;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

import com.u2ware.springfield.test.AbstractRootContextTest;



public class MappingBeanControllerTest extends AbstractRootContextTest{

	//@Test
	public void testMapping() throws Exception{
		this.mockMvc.perform(get("/part2/step1.html")
			)
			.andDo(print())
			.andExpect(status().isOk());
	}
	//@Test
	public void testMappingJson() throws Exception{
		this.mockMvc.perform(get("/part2/step1.json")
			)
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	//@Test
	public void testMappingXml() throws Exception{
		this.mockMvc.perform(get("/part2/step1.xml")
			)
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	//@Test
	public void testMappingXls() throws Exception{
		this.mockMvc.perform(get("/part2/step1.xls")
			)
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	@Test
	public void testMappingCsv() throws Exception{
		this.mockMvc.perform(get("/part2/step1.csv")
			)
			.andDo(print())
			.andExpect(status().isOk());
	}

	
}
