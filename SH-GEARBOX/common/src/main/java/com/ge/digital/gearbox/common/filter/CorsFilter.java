package com.ge.digital.gearbox.common.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ge.digital.gearbox.common.redis.RedisClient;

public class CorsFilter implements Filter {

	@Autowired
	RedisClient redisClient;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		try {
			String authorization = request.getHeader("Authorization");
			// if (StringUtils.isBlank(authorization)) {
			// response.sendError(HttpServletResponse.SC_FORBIDDEN,
			// "Authorization not invaild");
			// return;
			// }
			if (StringUtils.isNotBlank(authorization) && !redisClient.exists(authorization)) {
				DecodedJWT jwt = JWT.decode(authorization);
				Map<String, Claim> map = jwt.getClaims();
				Claim username = map.get("username");
				redisClient.set(authorization, username.asString(), 3600);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers",
				"X-Requested-With, Content-Type, X-Codingpedia,Authorization,NetGroupId");
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
