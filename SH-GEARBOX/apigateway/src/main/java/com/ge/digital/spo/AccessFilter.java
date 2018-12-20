package com.ge.digital.spo;  
  
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;  
  
@Component  
public class AccessFilter extends ZuulFilter {  
  
    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);  
  
    @Override  
    public String filterType() {  
        //前置过滤器  
        return "pre";  
    }  
  
    @Override  
    public int filterOrder() {  
        //优先级，数字越大，优先级越低  
        return 0;  
    }  
  
    @Override  
    public boolean shouldFilter() {  
        //是否执行该过滤器，true代表需要过滤  
        return true;  
    }  
  
    @Override  
    public Object run() {  
        RequestContext ctx = RequestContext.getCurrentContext();  
        HttpServletRequest request = ctx.getRequest();  
  
        log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());  
  
        //获取传来的参数accessToken  
        Object accessToken = request.getParameter("accessToken");  
        HttpServletResponse response = ctx.getResponse();
        response.setHeader("Access-Control-Allow-Origin", "*");
      response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
      response.setHeader("Access-Control-Max-Age", "3600");
      response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia,Authorization,NetGroupId,test");

//        if(accessToken == null) {  
//            log.warn("access token is empty");  
//            //过滤该请求，不往下级服务去转发请求，到此结束  
//            ctx.setSendZuulResponse(false);  
//            ctx.setResponseStatusCode(401);  
//            ctx.setResponseBody("{\"result\":\"accessToken is empty!\"}");  
//            return null;  
//        }  
        //如果有token，则进行路由转发  
        log.info("access token ok");  
        //这里return的值没有意义，zuul框架没有使用该返回值  
        return null;  
    }  
  
}  