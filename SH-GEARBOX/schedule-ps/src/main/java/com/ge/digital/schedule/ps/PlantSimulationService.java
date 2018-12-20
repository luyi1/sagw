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
		log.info("start application");
		Dispatch dispatch = activeXComponent.getObject();
		Dispatch.call(dispatch, "LoadModel", "D:\\dev\\GE模型0717Line2开发(1).spp");
		log.info("load model");
		Variant variant = Dispatch.call(dispatch, "StartSimulation", ".Model.Model.EventController");

		log.info("invoke StartSimulation,{}", variant.toString());
		Variant result = Dispatch.call(dispatch, "IsSimulationRunning");
		log.info("check if simulation is still running{}", result.getBoolean());
		// 轮询结果
		while (result.getBoolean()) {
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result = Dispatch.call(dispatch, "IsSimulationRunning");
			log.info("simulating:{}", result.getBoolean());
		}
		Dispatch.call(dispatch, "exitApplication");
		log.info("done");
		return true;
	}

	public static void main(String[] args) {
		new PlantSimulationService().invokeByCom();
	}
}
