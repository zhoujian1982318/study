package controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = MvcInitializer.class)
public class GreetingControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new GreetingController()).build();
	}

	
	@Test
	public void greeting() throws Exception {
		boolean isContain = wac.containsBean("grCntroller");
		boolean isContain2 = wac.containsBean("requestMappingHandlerMapping");
		System.out.println("contain grCntroller" + isContain);
		System.out.println("contain requestMappingHandlerMapping" + isContain2);
		mockMvc.perform(get("/greeting").param("name", "test"))
				.andExpect(status().isOk())
				.andExpect(content().string("{\"id\":1,\"content\":\"Hello, test!\"}"));
	}
}
