package com.elan.cloud.erp.gateway.filter.authentication;

import com.elan.cloud.erp.gateway.permission.config.DefaultSecurityManager;
import com.elan.cloud.erp.gateway.permission.utils.AntPathRequestMatcher;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;


/**
 * 身份认证
 */
@Component
public class AuthRoleFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(AuthRoleFilter.class);


    @Value("${permit.url:/user/login}")
    String permit;

    @Value("${permit.method:}")
    String method=null;

    @Autowired
    DefaultSecurityManager defaultSecurityManager;



    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 判断是否需要过滤，验证URL
     * 1、静态资源（主要是图片、CSS、JS，但是基本不存在服务中，而是在前端控制，放在单独的静态资源服务器中）
     * 2、动态资源（业务）
     * @return
     */
    @Override
    public boolean shouldFilter() {
         boolean should=true;
        RequestContext ctx = RequestContext.getCurrentContext();

        if (ctx.get("isSuccess")!=null && (Boolean)ctx.get("isSuccess")==false) {
            log.debug(ctx.getRequest().getRequestURI()+"跳过过滤！");
            return false;
        }
        AntPathRequestMatcher matcher=new AntPathRequestMatcher(this.method,this.permit,false);
        should = !matcher.matches(ctx.getRequest());
        log.debug(should+"过滤="+ctx.getRequest().getRequestURI());
        return should;
    }

    @Override
    public Object run() {
        log.debug("zuul filter----------auth-------");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if (log.isDebugEnabled()) {
            log.debug("parameter-----------------------------");
            Enumeration<String> params = request.getParameterNames();
            while (params.hasMoreElements()) {
                String name = params.nextElement();
                log.debug(String.format("%s=%s", name, request.getParameter(name)));
            }
            log.debug("zuul header-----------------------------");
            Map<String, String> header = ctx.getZuulRequestHeaders();
            Iterator<String> iter = header.keySet().iterator();
            while (iter.hasNext()){
                String headerName = iter.next();
                log.debug(String.format("%s=%s",headerName,header.get(headerName)));
            }
            log.debug("header-----------------------------");
            Enumeration<String> header1 = ctx.getRequest().getHeaderNames();
            while (header1.hasMoreElements()){
                String headerName = header1.nextElement();
                log.debug(String.format("%s=%s",headerName,ctx.getRequest().getHeader(headerName)));
            }
        }
        String access_token = ctx.getRequest().getHeader("x-access-token");
        if (StringUtil.isBlank(access_token)){//判断登录
            log.warn("Token is empty！");
            ctx.set("isSuccess",false);
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.setResponseBody("令牌不允许为空！");
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        String userId ="1";
       if (defaultSecurityManager.decide(request,userId)==false){
           log.warn("Access Denied! ");
           ctx.set("isSuccess",false);
           ctx.setSendZuulResponse(false);
           ctx.setResponseStatusCode(403);
           try {
               ctx.setResponseBody("没有授权使用！");
           }catch (Exception e){
               e.printStackTrace();
           }
       }
        return null;
    }
}
