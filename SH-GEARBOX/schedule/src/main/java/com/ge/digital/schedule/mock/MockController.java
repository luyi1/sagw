package com.ge.digital.schedule.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ge.digital.schedule.entity.LineWorkstationsStatus;
import com.ge.digital.schedule.entity.ScheduleTask;
import com.ge.digital.schedule.entity.WorkstationsNum;
import com.ge.digital.schedule.service.LineWorkstationStatusService;
import com.ge.digital.schedule.service.WorkstationsNumService;
import com.ge.digital.schedule.vo.WipApiResult;
import com.google.gson.Gson;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/")
public class MockController {

	private static final Logger log = LoggerFactory.getLogger(MockController.class);

	@Autowired
	Gson gson;

	@Autowired
	WorkstationsNumService workstationNumService;

	@ApiOperation("检查设备和产线")
	@PostMapping("fetchDeviceAndLine")
	public WipApiResult fetchDeviceAndLine() {
		log.info("checkDeviceAndLine:{}");
		WipApiResult wipApiResult = new WipApiResult();
		try {
			wipApiResult.setResCode("00000");
			wipApiResult.setResMsg("msg");
			wipApiResult.setResData("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wipApiResult;
	}

	@ApiOperation("获取当前可用物料")
	@PostMapping("fetchMaterielByPartNumbers")
	public WipApiResult fetchMaterielByPartNumbers(@RequestBody List<String> partNumbers) {
		log.info("checkMateriel:{}", partNumbers);
		WipApiResult wipApiResult = new WipApiResult();
		wipApiResult.setResCode("00000");
		wipApiResult.setResMsg("可用物料");
		Map<String, Integer> map = new HashMap<>();
		map.put("PartNumber01", 10);
		map.put("PartNumber02", 10);
		map.put("PartNumber03", 10);
		wipApiResult.setResData(gson.toJson(map));
		return wipApiResult;
	}

	@ApiOperation("排产下发")
	@PostMapping("takeOrder")
	public WipApiResult submit(@RequestBody List<ScheduleTask> scheduleTasks) {
		log.info("submit:{}", scheduleTasks);
		WipApiResult wipApiResult = new WipApiResult();
		wipApiResult.setResCode("00000");
		wipApiResult.setResMsg("排产下发");
		wipApiResult.setResData("");
		return wipApiResult;
	}

	@ApiOperation("获取最新的工位信息")
	@PostMapping("fetchWorkstationsStatus")
	public WipApiResult fetchWorkstationsStatus() {
		log.info("checkMateriel:{}");
		WipApiResult wipApiResult = new WipApiResult();
		wipApiResult.setResCode("00000");
		wipApiResult.setResMsg("OK");
		List<LineWorkstationsStatus> lineWorkstationsStatuses = new ArrayList<>();
		List<WorkstationsNum> workstationsNums = workstationNumService.findAll();
		for (WorkstationsNum workstationsNum : workstationsNums) {
				for (int i = 1; i <= workstationsNum.getOP10(); i++) {
					LineWorkstationsStatus lineWorkstationsStatus = new LineWorkstationsStatus();
					lineWorkstationsStatus.setStation("station" + i);
					lineWorkstationsStatus.setHaveMateriel(true);
					lineWorkstationsStatus.setUsability(true);
					lineWorkstationsStatus.setLine(workstationsNum.getLine());
					lineWorkstationsStatus.setOpNo("OP10");
					lineWorkstationsStatuses.add(lineWorkstationsStatus);
				}
				for (int i = 1; i <= workstationsNum.getOP20(); i++) {
					LineWorkstationsStatus lineWorkstationsStatus = new LineWorkstationsStatus();
					lineWorkstationsStatus.setStation("station" + i);
					lineWorkstationsStatus.setHaveMateriel(true);
					lineWorkstationsStatus.setUsability(true);
					lineWorkstationsStatus.setLine(workstationsNum.getLine());
					lineWorkstationsStatus.setOpNo("OP20");
					lineWorkstationsStatuses.add(lineWorkstationsStatus);
				}
				for (int i = 1; i <= workstationsNum.getOP30(); i++) {
					LineWorkstationsStatus lineWorkstationsStatus = new LineWorkstationsStatus();
					lineWorkstationsStatus.setStation("station" + i);
					lineWorkstationsStatus.setHaveMateriel(true);
					lineWorkstationsStatus.setUsability(true);
					lineWorkstationsStatus.setLine(workstationsNum.getLine());
					lineWorkstationsStatus.setOpNo("OP30");
					lineWorkstationsStatuses.add(lineWorkstationsStatus);
				}
				for (int i = 1; i <= workstationsNum.getOP40(); i++) {
					LineWorkstationsStatus lineWorkstationsStatus = new LineWorkstationsStatus();
					lineWorkstationsStatus.setStation("station" + i);
					lineWorkstationsStatus.setHaveMateriel(true);
					lineWorkstationsStatus.setUsability(true);
					lineWorkstationsStatus.setLine(workstationsNum.getLine());
					lineWorkstationsStatus.setOpNo("OP40");
					lineWorkstationsStatuses.add(lineWorkstationsStatus);
				}
				for (int i = 1; i <= workstationsNum.getOP50(); i++) {
					LineWorkstationsStatus lineWorkstationsStatus = new LineWorkstationsStatus();
					lineWorkstationsStatus.setStation("station" + i);
					lineWorkstationsStatus.setHaveMateriel(true);
					lineWorkstationsStatus.setUsability(true);
					lineWorkstationsStatus.setLine(workstationsNum.getLine());
					lineWorkstationsStatus.setOpNo("OP50");
					lineWorkstationsStatuses.add(lineWorkstationsStatus);
				}
				for (int i = 1; i <= workstationsNum.getOP60(); i++) {
					LineWorkstationsStatus lineWorkstationsStatus = new LineWorkstationsStatus();
					lineWorkstationsStatus.setStation("station" + i);
					lineWorkstationsStatus.setHaveMateriel(true);
					lineWorkstationsStatus.setUsability(true);
					lineWorkstationsStatus.setLine(workstationsNum.getLine());
					lineWorkstationsStatus.setOpNo("OP60");
					lineWorkstationsStatuses.add(lineWorkstationsStatus);
			}
		}
		wipApiResult.setResData(gson.toJson(lineWorkstationsStatuses));
		return wipApiResult;
	}

}
