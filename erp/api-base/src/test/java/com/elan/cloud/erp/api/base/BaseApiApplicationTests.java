package com.elan.cloud.erp.api.base;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BaseApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class BaseApiApplicationTests {
	@Autowired
	private WebApplicationContext context;

	protected MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
	}
	@Autowired
	protected TestRestTemplate testRestTemplate;



}
