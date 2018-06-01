package com.elan.cloud.erp.frontend.filter;


import com.elan.common.response.ResponseResultUtils;
import com.elan.common.utils.GenerationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginFilter implements Filter {

     private Logger logger = LoggerFactory.getLogger(LoginFilter.class);

     private String login;
     private List<String> excludes=new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("登录过滤器启动........................");
        String temp = filterConfig.getInitParameter("excludes");
        if (temp != null) {
            String[] url = temp.split(",");
            for (int i = 0; url != null && i < url.length; i++) {
                excludes.add(url[i]);
            }
        }

        temp = filterConfig.getInitParameter("login");
        if(StringUtils.isNotBlank(temp) ){
            login = temp;
        }

    }
    private boolean excludeUrl(String url){
        for(String s:excludes){
            if (s.indexOf("*")==0){
                if (url.endsWith(s.replaceAll("\\*",""))) return true;
            }
            if (s.indexOf("/")==0) {
                if (url.startsWith(s.replaceAll("\\*",""))) return true;
            }

        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String url=req.getRequestURI();

        if(excludeUrl(url)){
            filterChain.doFilter(req,resp);
            return;
        }

        Object access_token = req.getSession().getAttribute("x-access-token");
        if (!url.startsWith(this.login) && (access_token == null || access_token.equals(""))) {//登录过期或未登录
                logger.warn("登录过期或未登录，请求路径："+url);
                String xmlHttpRequest = req.getHeader("X-Requested-With");
                if (StringUtils.isNotEmpty(xmlHttpRequest) && xmlHttpRequest.equalsIgnoreCase("XMLHttpRequest")){
                    resp.setContentType("application/json;charset=utf-8");
                    resp.setCharacterEncoding("utf-8");
                    ObjectMapper mapper = new ObjectMapper();
                    resp.getWriter().print(mapper.writeValueAsString(ResponseResultUtils.error(401,"请重新登录！",this.login)));

                }else {
                    resp.setContentType("text/html;charset=utf-8");
                    resp.setCharacterEncoding("utf-8");
                    StringBuffer html = new StringBuffer();
                    html.append("<script>window.parent.location.href='"+this.login+"'</script>");
                    resp.getWriter().print(html.toString());
                }
                return;
        }
        Object csrf_token =req.getSession().getAttribute("csrf_token");
        if(csrf_token==null) {
            csrf_token = GenerationUtil.uuid();
            req.getSession().setAttribute("csrf_token", csrf_token);
        }
        req.setAttribute("csrf_token",csrf_token);//添加到页面请求中
        filterChain.doFilter(req,resp);
    }

    @Override
    public void destroy() {

    }


}
