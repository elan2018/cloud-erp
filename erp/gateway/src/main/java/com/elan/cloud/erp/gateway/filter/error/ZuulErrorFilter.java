package com.elan.cloud.erp.gateway.filter.error;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.elan.common.response.ResponseResult;
import com.elan.common.response.ResponseResultUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class ZuulErrorFilter extends ZuulFilter{

    private static Logger log = LoggerFactory.getLogger(ZuulErrorFilter.class);
    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.get("isSuccess")!=null && !(Boolean)ctx.get("isSuccess")) return false;
        return true;
    }

    @Override
    public Object run() {
        log.debug("zuul filter ---------------error--------------------");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        ctx.addZuulResponseHeader("Content-type","application/json;charset=utf-8");
        ctx.addZuulResponseHeader("Content-Language","");
        log.error(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        InputStream stream =ctx.getResponseDataStream();
        try {
            String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
            log.error(String.format("error response body:-----%s",body));
            ctx.setResponseBody(encode(body));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }


    private String encode(String result){
        RequestContext ctx = RequestContext.getCurrentContext();
        ResponseResult responseResult =null;
        responseResult =JSON.parseObject(result,ResponseResult.class);
        if(responseResult.getCode()==-1) {
            if (result.indexOf("{") == 0) {
                JSONObject object = JSON.parseObject(result);
                responseResult = ResponseResultUtils.error(ctx.getResponseStatusCode(), "系统异常", object);
            } else if (result.indexOf("[") == 0) {
                JSONArray array = JSON.parseArray(result);
                responseResult = ResponseResultUtils.error(ctx.getResponseStatusCode(), "系统异常", array);
            } else {
                responseResult = ResponseResultUtils.error(ctx.getResponseStatusCode(), "系统异常", result);
            }
        }
        return JSON.toJSONString(responseResult);
    }
}
