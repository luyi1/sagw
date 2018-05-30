package com.ge.digital.spo.security.dynamicalRole;

import java.util.ArrayList;  
import java.util.Collection;  
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
  
import javax.servlet.http.HttpServletRequest;  
  
import org.springframework.security.access.ConfigAttribute;  
import org.springframework.security.access.SecurityConfig;  
import org.springframework.security.web.FilterInvocation;  
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;  
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;  
import org.springframework.security.web.util.matcher.RequestMatcher;  
import org.springframework.stereotype.Component;  
  

  
/** 
 * Created by Athos on 2016-10-16. 
 */  
@Component("mySecurityMetadataSource")  
public class MySecurityMetadataSource  implements FilterInvocationSecurityMetadataSource {  
  
    private static Map<String,Collection<ConfigAttribute>> aclResourceMap = null;  
    
  
    /** 
     * 构造方法 
     */  
    //1  
    public MySecurityMetadataSource(){  
     
        loadResourceDefine();  
    }  
  
    @Override  
    public Collection<ConfigAttribute> getAttributes(Object object)throws IllegalArgumentException{  
        HttpServletRequest request=((FilterInvocation)object).getRequest();  
        Iterator<String> ite = aclResourceMap.keySet().iterator();  
        while (ite.hasNext()){  
            String resURL = ite.next();  
            RequestMatcher requestMatcher = new AntPathRequestMatcher(resURL);  
            if(requestMatcher.matches(request)){  
                return aclResourceMap.get(resURL);  
            }  
        }  
        return null;  
    }  
    //4  
    @Override  
    public Collection<ConfigAttribute> getAllConfigAttributes() {  
        System.out.println("metadata : getAllConfigAttributes");  
        return null;  
    }  
    //3  
    @Override  
    public boolean supports(Class<?> clazz) {  
        System.out.println("metadata : supports");  
        return true;  
    }  
  
  
    private void loadResourceDefine(){  
        /** 
         * 因为只有权限控制的资源才需要被拦截验证,所以只加载有权限控制的资源 
         */  
   //TODO
//    	Map<String,List<Role>> urlRoleMapping = roleurlmappingService.search();
        aclResourceMap = new HashMap<>();  
//todo
            ConfigAttribute ca = new SecurityConfig("ROLE_ADMIN");  
            ConfigAttribute ca2 = new SecurityConfig("ROLE_ADMIN1");  
//            String url = "/api/fifo/getMainList";
//            String url2 = "/api/masterdata/queryNetGrpIds";
//            String url3 = "/api/masterdata/queryFGs";
//            String url4 = "/api/scheduling/CancellOrderMain";
//            String url5 = "/api/scheduling/changeOrder";
//            String url6 = "/api/scheduling/offlineOrderMain";
//            String url7 = "/api/scheduling/backOfflineOrderMain";
//            String url8 = "/api/scheduling/getGapResult";
            
//            Collection<ConfigAttribute> atts2 = new ArrayList<ConfigAttribute>();  
//            atts2.add(ca2);  
//            aclResourceMap.put(url2,atts2);  
            Collection<ConfigAttribute> atts3 = new ArrayList<ConfigAttribute>();  
//            atts3.add(ca);  
            
//            aclResourceMap.put(url3,atts3);  
//            aclResourceMap.put(url4,atts3);
//            aclResourceMap.put(url5,atts3);
//            aclResourceMap.put(url6,atts3);
//            aclResourceMap.put(url7,atts3);
//            aclResourceMap.put(url8,atts3);
//            if(aclResourceMap.containsKey(url)){  
//                Collection<ConfigAttribute> value = aclResourceMap.get(url);  
//                value.add(ca);  
//                aclResourceMap.put(url,value);  
//  
//            }else {  
//                Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();  
//                atts.add(ca);  
//                aclResourceMap.put(url,atts);  
//            }  

    }  
}  