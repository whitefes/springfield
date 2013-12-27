package com.u2ware.springfield.test;

/*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
*/
import javax.servlet.Filter;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations="classpath:com/u2ware/springfield/sample/root-context.xml")
public abstract class AbstractRootContextTest {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected WebApplicationContext applicationContext;
	@Autowired(required=false) @Qualifier("springSecurityFilterChain")
	protected Filter springSecurityFilterChain;
	protected MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		
		if(mockMvc == null){
			
			DefaultMockMvcBuilder<DefaultMockMvcBuilder<?>> support = MockMvcBuilders.webAppContextSetup(applicationContext);
			if(springSecurityFilterChain != null){
				support.addFilters(springSecurityFilterChain); 
			}
			this.mockMvc = support.build();

		}
	}
	
}
