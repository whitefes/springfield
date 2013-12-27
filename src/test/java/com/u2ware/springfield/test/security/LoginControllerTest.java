package com.u2ware.springfield.test.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.u2ware.springfield.test.AbstractRootContextTest;




public class LoginControllerTest extends AbstractRootContextTest{

	@Test
	public void testLogin() throws Exception{

		String id = new DateTime().toString();
		this.mockMvc.perform(
				post("/security/user/register/new.html")
				.param("username", id)
				.param("password1", "a")
				.param("password2", "a")
				.param("role", "USER")
				.param("description", id)
				)
			.andExpect(status().isOk());
		
		this.mockMvc.perform(
				post("/j_spring_security_check")
				.param("j_username", id)
				.param("j_password", "a")
				.param("_spring_security_remember_me", "true"))
			.andDo(print())
			.andExpect(MockMvcResultMatchers.redirectedUrl("/security/user/loginForm.html"));
	
	}
	
	
	
	
}
