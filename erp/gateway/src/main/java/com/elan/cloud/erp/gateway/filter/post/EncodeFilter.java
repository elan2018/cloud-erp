package com.elan.cloud.erp.gateway.filter.post;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.elan.common.response.ResponseResult;
import com.elan.common.response.ResponseResultUtils;
import com.netflix.zuul.ZuulFilter;

import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author 廖永生
 * @create 2017-12-30 21:38
 * @decription
 */
@Component
public class EncodeFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(EncodeFilter.class.getName());
    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.get("isSuccess")!=null && (Boolean)ctx.get("isSuccess")==false) return false;
        return true;
    }

    @Override
    public Object run() {
        log.debug("zuul filter----------Encode----------");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        InputStream stream =ctx.getResponseDataStream();
        try {
            String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
            log.debug(String.format("response body:-----%s",body));
            log .debug("response status:"+ctx.getResponseStatusCode());
            ctx.setResponseBody(encode(body));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    private String encode(String result){
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.getResponseStatusCode()!=200){
            ctx.setResponseStatusCode(200);
            return result;
        }
        ResponseResult responseResult =null;
        if(result.indexOf("{")==0){
            JSONObject object = JSON.parseObject(result);
            responseResult= ResponseResultUtils.success(object);
        }else if (result.indexOf("[")==0){
            JSONArray array = JSON.parseArray(result);
            responseResult= ResponseResultUtils.success(array);
        }else{
            responseResult= ResponseResultUtils.success(result);
        }
       return JSON.toJSONString(responseResult);
    }
}