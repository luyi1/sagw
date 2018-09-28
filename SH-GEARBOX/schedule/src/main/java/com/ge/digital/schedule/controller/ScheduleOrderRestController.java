package com.ge.digital.schedule.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ge.digital.gearbox.common.response.BizErrorResponse;
import com.ge.digital.gearbox.common.response.NormalResponse;
import com.ge.digital.gearbox.common.response.RestResponseCode;
import com.ge.digital.schedule.entity.ScheduleOrder;
import com.ge.digital.schedule.excel.entity.ScheduleOrderExcelSupport;
import com.ge.digital.schedule.excelutil.ExcelUploadSupport;
import com.ge.digital.schedule.mapper.LineWorkstationStatusRepository;
import com.ge.digital.schedule.mq.MessageQueueProducer;
import com.ge.digital.schedule.service.RedisService;
import com.ge.digital.schedule.service.ScheduleOrderService;
import com.ge.digital.schedule.service.ScheduleTaskService;
import com.ge.digital.schedule.service.UploadingService;
import com.ge.digital.schedule.util.ExportExcelUtils;
import com.ge.digital.schedule.vo.ExcelData;
import com.ge.digital.schedule.vo.ScheduleTaskVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("排产订单管理")
@RestController
@RequestMapping("/api/scheduleOrder")
public class ScheduleOrderRestController {

	private static final Logger log = LoggerFactory.getLogger(ScheduleOrderRestController.class);

	@Autowired
	RedisService redisService;

	@Autowired
	MessageQueueProducer messageQueueProducer;

	@Autowired
	UploadingService uploadingService;

	@Autowired
	ScheduleOrderService scheduleOrderService;
	@Autowired
	ScheduleTaskService scheduleTaskService;

	@Autowired
	LineWorkstationStatusRepository lineWorkstationStatusRepository;

	@RequestMapping(value = "/uploadScheduleOrder", method = RequestMethod.POST)
	public Object uploadScheduleOrder(@RequestParam("file") MultipartFile file, String type) {
		NormalResponse rsp = new NormalResponse();
		try {
			//redisService.flushTempData();
			List<ScheduleOrder> scheduleOrders = new ArrayList<>();
			List<? extends ExcelUploadSupport> list = uploadingService.excel2Object(file,
					ScheduleOrderExcelSupport.class);
			boolean hasError = false;
			for (ExcelUploadSupport ls : list) {
				ScheduleOrder so = new ScheduleOrder();
				BeanUtils.copyProperties(ls, so);
				// 需求数量必须有值且必须为整数
				if (so.getAmount() == null || so.getAmount() == 0) {
					hasError = true;
					ls.getErrors().add(RestResponseCode.UPLOAD_RECORD_AMOUNT_CHECK.getLabel());
				}
				// 交付日期不为空或者是正确的日期格式(yyyyMMdd hh:mm)，且日期不能是过去的日期
				if (so.getFinishDate() == null || so.getFinishDate().getTime() < new Date().getTime()) {
					hasError = true;
					ls.getErrors().add(RestResponseCode.UPLOAD_RECORD_DATA_CHECK.getLabel());
				}
				so.setScheduleOrderNo(scheduleOrderService.getOrderNo());
				scheduleOrders.add(so);
			}
			// 保存order到redis
			redisService.setOrders(scheduleOrders);
			rsp.setBody(list);
			if (!hasError) {
				messageQueueProducer.rabbitMasterDatacheck();
				messageQueueProducer.rabbitDeviceAndLineCheck();
			}
		} catch (RuntimeException | IOException e) {
			e.printStackTrace();
			log.error("line uploading error!", e);
			return new BizErrorResponse(RestResponseCode.UPLOAD_EXCEPTION);
		}
		return rsp;

	}

	@ApiOperation("确认运行")
	@PostMapping("confirm")
	public Object confirmScheduleOrder() {
		NormalResponse rsp = new NormalResponse();
		BizErrorResponse bizError = new BizErrorResponse();
		try {
			scheduleOrderService.confirmScheduleOrder();
			rsp.setBody("正在检查");
			return rsp;
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
			log.error("confirmScheduleOrder has an error", e);
			return new BizErrorResponse(RestResponseCode.STSTEM_EXCEPTION);
		}
	}

	@ApiOperation("下发排产序列")
	@PostMapping("submit")
	public Object submitScheduleOrder() {
		NormalResponse rsp = new NormalResponse();
		try {
			scheduleOrderService.submit();
			return rsp;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("distributeScheduleOrder has an error", e);
			return new BizErrorResponse(RestResponseCode.WIP_SUBMIT_EXCEPTION);
		}
	}

	@RequestMapping(value = "/excel", method = RequestMethod.GET)
	public Object excel(HttpServletResponse response) throws Exception {
		try {
			ExcelData data = new ExcelData();
			data.setName("hello");
			List<String> titles = new ArrayList();
			titles.add("任务ID");
			titles.add("零件编码");
			titles.add("零件名称");
			titles.add("数量(炉)");
			titles.add("物料需求时间");
			data.setTitles(titles);
			List<List<Object>> rows = new ArrayList();
			List<ScheduleTaskVO> vos = redisService.getVos();
			if (null == vos)
				return new BizErrorResponse();
			for (ScheduleTaskVO vo : vos) {
				rows.add(vo.getValues());
			}
			data.setRows(rows);
			ExportExcelUtils.exportExcel(response, "物料需求订单.xlsx", data);
			return new NormalResponse();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("excel has an error,{}", e);
			return new BizErrorResponse(RestResponseCode.EXPORT_EXCEPTION);
		}

	}

}