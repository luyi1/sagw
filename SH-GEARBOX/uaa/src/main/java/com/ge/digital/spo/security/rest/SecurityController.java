package com.ge.digital.spo.security.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ge.digital.spo.controller.common.NormalResponse;
import com.ge.digital.spo.security.service.AuthService;


@RestController
@RequestMapping("api")
public class SecurityController {
	@Autowired
	AuthService authService;
	@RequestMapping(value = "checkUrl", method = RequestMethod.GET)
	public Object authResource(@RequestParam("url") String url, HttpServletRequest request) throws Exception {
		NormalResponse rsp = new NormalResponse();
		String token = request.getHeader("Authorization");
		String netGrpId = request.getHeader("NetGroupId")==null?"":request.getHeader("NetGroupId");
		boolean isAuth = authService.authResource(url, token,netGrpId);
		if(true)
		{
			return rsp;
		}else{
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			attributes.getResponse().sendError(HttpServletResponse.SC_FORBIDDEN, "AdditionalInformationIfAvailable");
		}
		return rsp;
	}
	
	@RequestMapping(value = "getGrpIdAndLandingPage", method = RequestMethod.GET)
	public Object getGrpIdAndLandingPage(@RequestParam("token") String token,HttpServletRequest request) throws IOException {
		NormalResponse rsp = new NormalResponse();
		String defaultGrpIdAndLandingP = authService.getGrpIdAndLandingPage(token);
		rsp.setBody(defaultGrpIdAndLandingP);
		return rsp;
	}
}
