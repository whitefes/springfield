package com.u2ware.springfield.test.part5.step4;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;


import com.u2ware.springfield.test.AbstractRootContextTest;
import com.u2ware.springfield.view.multipart.MultipartFileBean;

public class ControllerTest extends AbstractRootContextTest{

	
	@Test
	public void testUploadAndRemove() throws Exception{
		MockMultipartFile file = new MockMultipartFile("multipartFile", "data.text", "text/html", "AAAAAA".getBytes());
		
		MultipartFileBean r = (MultipartFileBean)
			this.mockMvc.perform(fileUpload("/part5/step4/upload.html")
				.file(file))
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn().getModelAndView().getModel().get("model_entity");


		String multipartFile = r.getContentFile();
		logger.debug(multipartFile);
		
		
		this.mockMvc.perform(post("/part5/step4/delete.html")
				.param("multipartFile" , multipartFile))
			.andExpect(status().isOk());
	}
}
