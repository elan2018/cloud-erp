package com.elan.cloud.erp.gateway.filter.pre;

import com.alibaba.fastjson.JSON;
import com.elan.cloud.erp.gateway.permission.config.DefaultSecurityManager;
import com.elan.cloud.erp.gateway.permission.utils.AntPathRequestMatcher;
import com.elan.common.response.ResponseResult;
import com.elan.common.utils.EncrypUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 身份认证
 */
@Component
public class AuthRoleFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(AuthRoleFilter.class);


    @Value("${permit.url:/user/controller}")
    String permit;

    @Value("${permit.method:}")
    String method=null;

    @Autowired
    DefaultSecurityManager defaultSecurityManager;

    @Resource(name="encryToken")
    EncrypUtil encrypUtil;



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
     * @return 是否过滤
     */
    @Override
    public boolean shouldFilter() {
        boolean should=true;
        RequestContext ctx = RequestContext.getCurrentContext();

        if (ctx.get("isSuccess")!=null && !(Boolean)ctx.get("isSuccess")) {
            log.debug("上一个过滤器验证失败！",ctx.getRequest().getRequestURI()+"跳过过滤！");
            return false;
        }
        AntPathRequestMatcher matcher=new AntPathRequestMatcher(this.method,this.permit,false);
        should = !matcher.matches(ctx.getRequest());
        log.debug("过滤="+ctx.getRequest().getRequestURI());
        return should;
    }

    @Override
    public Object run() {
        log.debug("gateway filter----------auth-------");
        RequestContext ctx = RequestContext.getCurrentContext();

        HttpServletRequest request = ctx.getRequest();
        Map<String,List<String>> k = ctx.getRequestQueryParams();
        if (log.isDebugEnabled()) {
            log.debug("request's parameter-----------------------------");
                Enumeration<String> params = request.getParameterNames();
                while (params.hasMoreElements()) {
                    String name = params.nextElement();
                    log.debug(String.format("%s=%s", name, request.getParameter(name)));
                }

            log.debug("zuul's header-----------------------------");
            Map<String, String> header = ctx.getZuulRequestHeaders();
            Iterator<String> iter = header.keySet().iterator();
            while (iter.hasNext()){
                String headerName = iter.next();
                log.debug(String.format("%s=%s",headerName,header.get(headerName)));
            }
            log.debug("request's header-----------------------------");
            Enumeration<String> header1 = ctx.getRequest().getHeaderNames();
            while (header1.hasMoreElements()){
                String headerName = header1.nextElement();
                log.debug(String.format("%s=%s",headerName,ctx.getRequest().getHeader(headerName)));
            }
        }
        String access_token = ctx.getRequest().getHeader("x-access-token");
        if(StringUtils.isEmpty(access_token)){
            access_token = ctx.getRequest().getParameter("access_token");
        }
        if (StringUtils.isEmpty(access_token)){//判断登录
            log.warn("header[x-access-token] or parameter[access_token] is empty！");
            ctx.set("isSuccess",false);
            //ctx.setSendZuulResponse(false);
            //ctx.setResponseStatusCode(401);
            try {
                ResponseResult result = new ResponseResult(401,"令牌不允许为空！");
                ctx.setResponseBody(JSON.toJSONString(result));
            }catch (Exception e){
                e.printStackTrace();
                log.error(e.getMessage());
            }
            return null;
        }
        int userId =0;
        String _access_token = null;
        try {
            _access_token = new String(encrypUtil.decrypt(access_token));
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (BadPaddingException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        String _userId =_access_token.substring(_access_token.lastIndexOf("_")+1);

        try{
            userId = Integer.valueOf(_userId);
        }catch(NumberFormatException e){
            log.error("User ID not number!",_userId);
        }

        //用户权限验证
       if (!defaultSecurityManager.decide(request,userId)){
           log.warn("Access Denied! ");
           ctx.set("isSuccess",false);
           //ctx.setSendZuulResponse(false);
           //ctx.setResponseStatusCode(403);
           try {
               ResponseResult result = new ResponseResult(403,"授权禁止使用！！");
               ctx.setResponseBody(JSON.toJSONString(result));
           }catch (Exception e){
               log.error(e.getMessage());
               e.printStackTrace();
           }
       }
        return null;
    }
}
