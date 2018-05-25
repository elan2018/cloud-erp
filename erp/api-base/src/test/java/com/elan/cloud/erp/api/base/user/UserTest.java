package com.elan.cloud.erp.api.base.user;

import com.alibaba.fastjson.JSON;
import com.elan.cloud.erp.api.base.BaseApiApplication;
import com.elan.common.response.ResponseResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BaseApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest{

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testRequest() throws Exception {
        Object[] param = new Object[]{1};
        String result = testRestTemplate.getForObject("/menu?userId={userId}",String.class,param);
        System.out.println(result);
        ResponseResult res = JSON.parseObject(result,ResponseResult.class);
        Assert.assertEquals(0L,(long)res.getCode());
    }
}
