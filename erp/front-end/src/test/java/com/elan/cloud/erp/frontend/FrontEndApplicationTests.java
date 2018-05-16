package com.elan.cloud.erp.frontend;

import com.elan.common.response.ResponseResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FrontEndApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class FrontEndApplicationTests {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void get() throws Exception {
		Map<String,String> multiValueMap = new HashMap<>();
		multiValueMap.put("userId","1");//传值，但要在url上配置相应的参数
		ResponseResult result = testRestTemplate.getForObject("/user/menu?userId={userId}",ResponseResult.class,multiValueMap);
		//Assert.assertEquals(result.getCode().toString(),"1");
	}
}
