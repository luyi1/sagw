package com.ge.digital.schedule.aop;

import java.util.Date;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.ge.digital.schedule.entity.ModelBase;

@Aspect
@Component
public class MapperAscept {

	@Pointcut("execution(* com.ge.digital.schedule.mapper.*Repository.save(*))")
	public void repsitorySavePointcut() {

	}

	@Before("repsitorySavePointcut()")
	public void saveBefore(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		if (args[0] instanceof List) {
			List<ModelBase> modelBases = (List<ModelBase>) args[0];
			for (ModelBase modelBase : modelBases) {
				modelBase.setCreateOn(new Date());
				modelBase.setLastUpdateOn(modelBase.getCreateOn());
			}
		} else {
			ModelBase modelBase = (ModelBase) args[0];
			modelBase.setCreateOn(new Date());
			modelBase.setLastUpdateOn(modelBase.getCreateOn());
		}
	}

	@Pointcut("execution(* com.ge.digital.schedule.service.*Service.update(*))")
	public void repsitoryUpdatePointcut() {

	}

	@Before("repsitoryUpdatePointcut()")
	public void updateBefore(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		if (args[0] instanceof List) {
			List<ModelBase> modelBases = (List<ModelBase>) args[0];
			for (ModelBase modelBase : modelBases) {
				modelBase.setLastUpdateOn(new Date());
			}
		} else {
			ModelBase modelBase = (ModelBase) args[0];
			modelBase.setLastUpdateOn(new Date());
		}

	}
}
