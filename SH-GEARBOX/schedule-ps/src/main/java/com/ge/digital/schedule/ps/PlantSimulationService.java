package com.ge.digital.schedule.ps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

@Service
public class PlantSimulationService {

	private static final Logger log = LoggerFactory.getLogger(PlantSimulationService.class);

	/**
	 * 调用
	 * 
	 * @throws Exception
	 */
	public Boolean invokeByCom() {
		ActiveXComponent activeXComponent = new ActiveXComponent("Tecnomatix.PlantSimulation.RemoteControl.14.1");
		log.info("启动应用程序");
		Dispatch dispatch = activeXComponent.getObject();
		Dispatch.call(dispatch, "LoadModel", "D:\\GE模型0717Line2开发(1).spp");
		log.info("加载model");
		Variant variant = Dispatch.call(dispatch, "StartSimulation", ".Model.Model.EventController");
		//
		// DispatchEventHandler handler = new DispatchEventHandler();
		// DispatchEvents events = new DispatchEvents(dispatch, handler,
		// "Tecnomatix.PlantSimulation.RemoteControl.14.1");

		log.info("调用StartSimulation,{}", variant.toString());
		Variant result = Dispatch.call(dispatch, "IsSimulationRunning");
		log.info("检查是否还在运行{}", result.getBoolean());
		// 轮询结果
		while (result.getBoolean()) {
			result = Dispatch.call(dispatch, "IsSimulationRunning");
			log.info("仿真执行中:{}", result.getBoolean());
		}
		log.info("仿真模拟结束");
		return true;
	}

}
