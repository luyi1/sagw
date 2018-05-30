package com.ge.digital.spo.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ge.digital.spo.chain.infrastructure.user.vo.UserByRoleVo;
import com.ge.digital.spo.chain.infrastructure.user.vo.UserVo;
import com.ge.digital.spo.security.service.UserService;
import com.ge.digital.spo.security.util.Encrypt;

//custom authentication Provider
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {


private UserService userService;

@Autowired
public void setUserService(UserService userService) {
    this.userService = userService;
}
 @Override
 public Authentication authenticate(Authentication authentication) throws AuthenticationException {
     // get username&password from credential
     String name = authentication.getName();
     String password = authentication.getCredentials().toString();
     UserVo uv = new UserVo();
     uv.setLoginId(name);
     List<UserVo> userVOs =  userService.findByUserVo(uv);
     UserVo user = userVOs.stream().findAny().orElse(null);
     
     // authentication logic user!=null &&
     ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
     HttpServletResponse response =  attributes.getResponse();
     response.setHeader("Access-Control-Allow-Origin", "*");
     response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
     response.setHeader("Access-Control-Max-Age", "3600");
     response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia,Authorization,NetGroupId");
     HttpServletRequest request =  attributes.getRequest();
     if ( user!=null &&  Encrypt.md5(password).equals(user.getPassword())&&user.getIsactive()) {
    	 UserByRoleVo urv = new UserByRoleVo();
    	 urv.setId(user.getId());
    	 request.setAttribute("defaultNetGrp", user.getDefaultNetGrp()==null?"NA":user.getDefaultNetGrp());
    	 request.setAttribute("landingPage", user.getLandingPage()==null?"NA":user.getLandingPage()) ;
    	 request.setAttribute("locale", user.getLocale()==null?"":user.getLocale()) ;
    	 List<UserByRoleVo> list = userService.findByUserByRoleVo(urv);
         ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    	 for(UserByRoleVo ubr:list){
         // set authority and role
    		 if(ubr!=null){
         authorities.add( new GrantedAuthorityImpl(ubr.getRoleId()) );
    		 }

    	 }
         // generate authentication token
         Authentication auth = new UsernamePasswordAuthenticationToken(name, password, authorities);
         return auth;
     }else {
         throw new BadCredentialsException("Please check username and password is correct");
     }
 }

 // 是否可以提供输入类型的认证服务
 @Override
 public boolean supports(Class<?> authentication) {
     return authentication.equals(UsernamePasswordAuthenticationToken.class);
 }
}
