package com.ge.digital.schedule;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ge.digital.gearbox.common.autoIncrKey.BizAutoIncrService;
import com.ge.digital.schedule.mapper.LineProcessTimeRepository;
import com.ge.digital.schedule.mapper.LineRepository;
import com.ge.digital.schedule.mapper.LineWorkstationStatusRepository;
import com.ge.digital.schedule.mapper.ProcessLineInfoRepository;
import com.ge.digital.schedule.mapper.WorkstationsNumRepository;
import com.ge.digital.schedule.service.ScheduleCoreService;
import com.ge.digital.schedule.service.ScheduleTaskService;
import com.ge.digital.schedule.vo.ScheduleInput;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleApplicationTest extends TestCase {

	private static final Logger log = LoggerFactory.getLogger(ScheduleApplicationTest.class);

	@Autowired
	ScheduleTaskService scheduleTaskService;

	@Autowired
	LineRepository lineRepository;

	@Autowired
	ProcessLineInfoRepository processLineInfoRepository;

	@Autowired
	LineWorkstationStatusRepository lineWorkstationStatusRepository;

	@Autowired
	WorkstationsNumRepository workstationsNumRepository;

	@Autowired
	LineProcessTimeRepository lineProcessTimeRepository;
	@Autowired
	BizAutoIncrService bizAutoIncrService;
	@Autowired
	ScheduleCoreService scheduleCoreService;
	@Test
	public void test() {
		ScheduleInput si = new ScheduleInput();
		si.setLockupDays(3);
		si.setScheduleTime(new Date());
		
		Date newDate = scheduleCoreService.getLockDate(si);
		System.out.println(newDate);
//		int test = bizAutoIncrService.nextSerial(BizAutoIncrKey.BATCH_UPDATE_ID.getValue());
//		System.out.println(test);
//		scheduleOrderService.fireScheduleCore();
		// scheduleorder
//		List<String> numbers = new ArrayList<>();
//		numbers.add("partNumber01");
//		numbers.add("partNumber02");
//		Map<String, Integer> map = scheduleTaskService.findNotIssue(numbers);
//		log.info("result:{}", map);
	}
}
