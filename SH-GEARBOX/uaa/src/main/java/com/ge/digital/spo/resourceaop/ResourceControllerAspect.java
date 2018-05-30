package com.ge.digital.spo.resourceaop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

//import com.ge.digital.spo.security.model.UserRoleResource;

@Aspect
@Component
@Configuration
public class ResourceControllerAspect {
	private static final Map netGrpAuthurlMap = new HashMap();
	private static final Map regionCodeGetAuthurlMap = new HashMap();
	private static final Map regionCodePostAuthurlMap = new HashMap();
	static{
		netGrpAuthurlMap.put("/api/scheduling/CancellOrderMain", 1);//cancel_order
		netGrpAuthurlMap.put("/api/scheduling/changeOrder", 1);//change_order
		netGrpAuthurlMap.put("/api/scheduling/offlineOrderMain", 1);//offline_order
		netGrpAuthurlMap.put("/api/scheduling/backOfflineOrderMain", 1);//online_order
		netGrpAuthurlMap.put("/api/scheduling/getGapResult", 1);//加班Gap计算
		netGrpAuthurlMap.put("/api/scheduling/massUploadCalendar", 1);//批量加班
		netGrpAuthurlMap.put("/api/scheduling/swap", 1);//查询adjustment订单
		netGrpAuthurlMap.put("/api/scheduling/swap/exec", 1);//
		netGrpAuthurlMap.put("/api/scheduling/leveling", 1);
		netGrpAuthurlMap.put("/api/scheduling/leveling/chgDate", 1);
		netGrpAuthurlMap.put("/api/scheduling/leveling/save", 1);
		netGrpAuthurlMap.put("/api/scheduling/leveling/delRecords", 1);
		netGrpAuthurlMap.put("/api/scheduling/leveling/delBatch", 1);
		netGrpAuthurlMap.put("/api/scheduling/swap", 1);
		netGrpAuthurlMap.put("/api/scheduling/progress", 1);
		netGrpAuthurlMap.put("/api/scheduling/initDropDown", 1);
		netGrpAuthurlMap.put("/api/scheduling/uploadOrderMain", 1);
		netGrpAuthurlMap.put("/api/scheduling/simulate", 1);
		netGrpAuthurlMap.put("/api/scheduling/changeReqDate", 1);
		netGrpAuthurlMap.put("/api/scheduling/execute", 1);
		regionCodePostAuthurlMap.put("/api/andon/createAndon", 1);
		regionCodeGetAuthurlMap.put("/api/andon/showAndonList", 1);
		netGrpAuthurlMap.put("/api/scheduling/execute", 1);
	}
	private static final Logger logger = LoggerFactory.getLogger(ResourceControllerAspect.class);
    static final String SECRET = "P@ssw02d";            // JWT password
    static final String TOKEN_PREFIX = "Bearer";        // Token prefix
    static final String HEADER_STRING = "Authorization";// header key for authorization
  
    @Pointcut("execution(* com.ge.digital.spo.restapi.controller.*.*(..))")
    public void RestfulAop(){
    	//it's just for Point cut.
    }
    

//    @Around("RestfulAop()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable  {
		long start = System.currentTimeMillis();
		
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		HttpServletRequest request = attributes.getRequest();
		String token = request.getHeader("Authorization");
		String netGrpId = request.getHeader("netGrpId");
		 String authorities = "";
		String region_code="";
		String url =request.getRequestURI();
		request.getParameter("");
		if(token!=null)
		{
			token = "";
//			attributes.getResponse().sendError(HttpServletResponse.SC_FORBIDDEN, "AdditionalInformationIfAvailable");
		}
	
//		  Claims claims = Jwts.parser()
//                  // get sign key
//                  .setSigningKey(SECRET)
//                  // get ride of Bearer
//                  .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
//                  .getBody();
		  String username ="";
//		  username=claims.getSubject();
		  authorities = "";
//		  authorities =claims.get("authorities").toString();
		  Object[] args = pjp.getArgs();
		  String resourceName = getResourceNameFromurl(url,args,request);
		  //TODO
//		  if()
//		  userRoleResourceService.validate(request.getRequestURI(),resource_name,authorities);
//		  UserRoleResource urr =new UserRoleResource();
//	    	urr.setUrl("/api/scheduling/CancellOrderMain");
//	    	urr.setResourceName("Surgery");
//	    	urr.setRolename("Admin");
//	    	List<UserRoleResource> list=new ArrayList<>();
//	    	list.add(urr);
//	    	userRoleResourceService.getUserRoleResourceByUsername(username);
		 
		  
//		System.out.println(claims);
		  Object object = pjp.proceed();
		long end = System.currentTimeMillis();
		logger.info("total execute time:" + (end - start)+"ms");
		return object;
    }
    private boolean needResourceAuthUrl(String url)
    {
    	return isNetGrpResourceAuthUrl(url)||isRegionCodePostAuthurlMap(url);
    }
    private boolean isNetGrpResourceAuthUrl(String url)
    {
    	return netGrpAuthurlMap.containsKey(url);
    }
    private boolean isRegionCodePostAuthurlMap(String url)
    {
    	return regionCodePostAuthurlMap.containsKey(url);
    }
    
    private String getResourceNameFromurl(String url,Object[] args,HttpServletRequest request)
    {	if(!needResourceAuthUrl(url))
	    {
	    	return "NO_NEED_AUTH_RES";
	    }
    	if(isNetGrpResourceAuthUrl(url))
    	{
    		 
    		return request.getHeader("netGrpId");
    	}
    	else if(isRegionCodePostAuthurlMap(url))
    	{
    		
 			JSONObject jo = new JSONObject(args[0]);
 			jo.get("regionCode");
//    		jo.
    	}
    	return "";
    }


    
}