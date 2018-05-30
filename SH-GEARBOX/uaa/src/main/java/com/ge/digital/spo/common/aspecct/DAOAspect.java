package com.ge.digital.spo.common.aspecct;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ge.digital.spo.chain.infrastructure.common.ModelBase;
import com.ge.digital.spo.security.service.AuthService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Aspect
@Component
@Configuration
public class DAOAspect {

	@Pointcut("execution(* com.ge.digital.spo.chain.infrastructure.*.dao.*.update*(..)) and !execution(* com.ge.digital.spo.chain.infrastructure.*.dao.UwbCoordinateMapper.update*(..)) "
			+ "and !execution(* com.ge.digital.spo.chain.infrastructure.*.dao.UwbSignalMapper.update*(..)) and !execution(* com.ge.digital.spo.chain.infrastructure.*.dao.AndonMapper.update*(..))")
	public void daoUpdate() {
		// it's just for Point cut.
	}

	@Pointcut("execution(* com.ge.digital.spo.chain.infrastructure.*.dao.*.insert*(..)) and !execution(* com.ge.digital.spo.chain.infrastructure.*.dao.UwbCoordinateMapper.insert*(..)) "
			+ "and !execution(* com.ge.digital.spo.chain.infrastructure.*.dao.UwbSignalMapper.insert*(..)) and !execution(* com.ge.digital.spo.chain.infrastructure.*.dao.AndonMapper.insert*(..))")
	public void daoCreate() {
		// it's just for Point cut.
	}

	@Around("daoUpdate()")
	public Object doAroundUpdate(ProceedingJoinPoint pjp) throws Throwable {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attributes == null) {
			Object object = pjp.proceed();
			return object;
		}
		HttpServletRequest request = attributes.getRequest();
		String token = request.getHeader("Authorization");
		if (token != null && !pjp.getSignature().getDeclaringType().toString()
				.equals("interface com.ge.digital.spo.chain.infrastructure.md.dao.JobpayloadMapper")) {
			String username = "";
			// please

			Claims claims = Jwts.parser()
					// get sign key
					.setSigningKey(AuthService.SECRET)
					// // get ride of Bearer
					.parseClaimsJws(token.replace(AuthService.TOKEN_PREFIX, "")).getBody();

			username = claims.getSubject();
			attributes.setAttribute("username", username, RequestAttributes.SCOPE_REQUEST);
			Object[] objects = pjp.getArgs();
			if (objects != null && objects.length > 0) {

				for (Object arg : objects) {
					if (arg instanceof ModelBase) {
						ModelBase mBase = ((ModelBase) arg);
						Date now = new Date();
						mBase.setLastUpdateBy(username);
						mBase.setLastUpdateOn(now);
					}
				}
			}
		}
		Object object = pjp.proceed();
		return object;

	}

	@Around("daoCreate()")
	public Object doAroundCreate(ProceedingJoinPoint pjp) throws Throwable {
		// if()
		if (!pjp.getSignature().getDeclaringType().toString()
				.equals("interface com.ge.digital.spo.chain.infrastructure.md.dao.JobpayloadMapper")) {
			String username = "";
			// please
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes();
			if (attributes == null) {
				Object object = pjp.proceed();
				return object;
			}
			HttpServletRequest request = attributes.getRequest();

			Object[] objects = pjp.getArgs();

			if (objects != null && objects.length > 0) {

				for (Object arg : objects) {
					if (arg instanceof ModelBase) {
						String token = request.getHeader("Authorization");
						Claims claims = Jwts.parser()
								// get sign key
								.setSigningKey(AuthService.SECRET)
								// // get ride of Bearer
								.parseClaimsJws(token.replace(AuthService.TOKEN_PREFIX, "")).getBody();

						username = claims.getSubject();
						ModelBase mBase = ((ModelBase) arg);
						Date now = new Date();
						if (mBase.getCreateBy() == null || "".equals(mBase.getCreateBy())) {
							mBase.setCreateBy(username);
						}

						if (mBase.getCreateOn() == null) {
							mBase.setCreateOn(now);
						}
					}
				}
			}
		}
		Object object = pjp.proceed();
		return object;
	}
}
