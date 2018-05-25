package com.elan.cloud.erp.gateway.filter.post;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HeaderFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(HeaderFilter.class);
    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.get("isSuccess")!=null && !(Boolean)ctx.get("isSuccess")) return false;
        return true;
    }

    @Override
    public Object run() {
        log.debug("zuul filter----------Header----------");
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulResponseHeader("Content-type","application/json;charset=utf-8");
        ctx.addZuulResponseHeader("Content-Language","");
        return "ok";
    }
}
