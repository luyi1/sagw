package com.ge.digital.schedule.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.digital.schedule.excelutil.MasterDataType;
import com.ge.digital.schedule.service.LineBufferService;
import com.ge.digital.schedule.service.LineProcessTimeService;
import com.ge.digital.schedule.service.ProcessLineInfoService;
import com.ge.digital.schedule.service.WorkstationsNumService;

@Aspect
@Component
public class MasterDataAscept {

	@Autowired
	LineBufferService lineBufferService;

	@Autowired
	ProcessLineInfoService processLineInfoService;

	@Autowired
	LineProcessTimeService lineProcessTimeService;

	@Autowired
	WorkstationsNumService workstationsNumService;

	@Pointcut("execution(* com.ge.digital.schedule.controller.MasterDataController.saveMasterdata(*))")
	public void saveMasterDataPointcut() {

	}

	@Before("saveMasterDataPointcut()")
	public void afterSaveMasterData(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		String type = (String) args[1];
		if (MasterDataType.LineBuffer.getValue().equals(type)) {
			lineBufferService.loadToRedis();
		} else if (MasterDataType.ProcessLineInfo.getValue().equals(type)) {
			processLineInfoService.loadToRedis();
		} else if (MasterDataType.LineProcessTime.getValue().equals(type)) {
			lineProcessTimeService.loadToRedis();
		} else if (MasterDataType.WorkstationStatus.getValue().equals(type)) {
			workstationsNumService.loadToRedis();
		}
	}

}
