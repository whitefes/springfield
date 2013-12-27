package com.u2ware.springfield.test.part5.step3;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import com.u2ware.springfield.sample.part5.step3.AttachedFile;
import com.u2ware.springfield.test.AbstractRootContextTest;



public class ControllerTest extends AbstractRootContextTest{

	protected AttachedFile executeUpload()throws Exception{
		
		MockMultipartFile file = new MockMultipartFile("multipartFile", "data.text", "text/html", "AAAAAA".getBytes());

		AttachedFile model = (AttachedFile)
		this.mockMvc.perform(fileUpload("/part5/step3/new.html")
				.file(file)
				//.param("id", "1")
				)
				//.andDo(print())
				.andExpect(status().isOk())
				.andReturn().getModelAndView().getModel().get("model_entity");
	
		return model;
	}
	
	//////////////////////////////////////////////////////////
	//
	//////////////////////////////////////////////////////////	
	@Test
	public void testDownload() throws Exception{
		
		Integer id = executeUpload().getId();
		logger.debug(""+id);
		
		this.mockMvc.perform(get("/part5/step3/{id}.download", id)
			)
			.andDo(print())
			.andExpect(status().isOk());
	}

	//////////////////////////////////////////////////////////
	//
	//////////////////////////////////////////////////////////	
	@Test
	public void testStream() throws Exception{
		
		Integer id = executeUpload().getId();
		this.mockMvc.perform(get("/part5/step3/{id}.stream", id))
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	//////////////////////////////////////////////////////////
	//
	//////////////////////////////////////////////////////////	
	@Test
	public void testDelete() throws Exception{
		
		Integer id = executeUpload().getId();
		this.mockMvc.perform(
				delete("/part5/step3/{id}/edit.html", id))
			//.andDo(print())
			.andExpect(status().isOk());
	}
	
}
