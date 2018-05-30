package com.ge.digital.spo.security;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.ge.digital.spo.security.service.AuthService;
import com.ge.digital.spo.security.util.JSONResult;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

class TokenAuthenticationService {

  // JWT generate addAuthentication method
    static void addAuthentication(HttpServletRequest request, HttpServletResponse response, String username,Collection<? extends GrantedAuthority> authorities) {
    	
//    	UserRoleResource urr =new UserRoleResource();
//    	urr.setUrl("/api/scheduling/CancellOrderMain");
//    	urr.setResourceName("Surgery");
//    	urr.setRolename("Admin");
//    	List<UserRoleResource> list=new ArrayList<>();
//    	list.add(urr);
    // 生成JWT
    	Iterator<? extends GrantedAuthority> it = authorities.iterator();

    	String authoritieStr = "";
    	while(it.hasNext())
    	{
    		GrantedAuthority authority = it.next();
    		if(it.hasNext())
    		{
    			authoritieStr=authoritieStr+authority.getAuthority()+",";
    		}else{
    			authoritieStr=authoritieStr+authority.getAuthority();
    		}
    	}
    	String dNetGrp = (String)request.getAttribute("defaultNetGrp");
    	String landingPage = (String)request.getAttribute("landingPage");
    	String locale = (String)request.getAttribute("locale");
        String JWT = Jwts.builder()
                // Save privilege for user
                .claim("authorities", authoritieStr)
                .claim("defaultNetGrp", dNetGrp)
                .claim("landingPage", landingPage)
                .claim("locale", locale)
//                .claim("roleResource", urr)
                // write username to subject
                .setSubject(username)
                // set expiration
                        .setExpiration(new Date(System.currentTimeMillis() + AuthService.EXPIRATIONTIME))
                // e-signature 
                        .signWith(SignatureAlgorithm.HS512, AuthService.SECRET)
                        .compact();

        // write JWT into body
        try {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getOutputStream().println(JSONResult.fillResultString("0000", "", JWT,true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  // JWT验证方法
    static Authentication getAuthentication(HttpServletRequest request) {
        // get token from header
        String token = request.getHeader(AuthService.HEADER_STRING);

        if (token != null) {
            // parse Token
            Claims claims = Jwts.parser()
                    // get sign key
                    .setSigningKey(AuthService.SECRET)
                    // get ride of Bearer
                    .parseClaimsJws(token.replace(AuthService.TOKEN_PREFIX, ""))
                    .getBody();

            // get user name
            String user = claims.getSubject();

            // get authorities
            List<GrantedAuthority> authorities =  AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities"));

            // return token
            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, authorities) :
                    null;
        }
        return null;
    }
}
