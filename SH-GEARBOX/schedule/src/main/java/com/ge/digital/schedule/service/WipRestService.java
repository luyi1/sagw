package com.ge.digital.schedule.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ge.digital.gearbox.common.response.RestResponseCode;
import com.ge.digital.schedule.dto.TakeOrderDTO;
import com.ge.digital.schedule.entity.LineWorkstationsStatus;
import com.ge.digital.schedule.util.HttpClientUtil;
import com.ge.digital.schedule.vo.WipApiResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class WipRestService {

	private static final Logger log = LoggerFactory.getLogger(WipRestService.class);

	@Autowired
	Gson gson;

	@Autowired
	LineWorkstationStatusService lineWorkstationStatusService;
	@Value("${custom.wip.url.fetchdeviceandline}")
	String fetchDeviceAndLineUrl;

	@Value("${custom.wip.url.fetchmaterielbypartnumbers}")
	String fetchMaterielByPartNumbersUrl;

	@Value("${custom.wip.url.submit}")
	String submitUrl;

	@Value("${custom.wip.url.fetchnewworkstationstatus}")
	String fetchNewWorkstationStatusUrl;

	@Autowired
	RedisService redisService;

	/**
	 * 4）获取当前可用物料
	 * 
	 * @return
	 */
	public Map<String, Integer> fetchMaterielByPartNumbers(List<String> partNumbers) {
		log.info("fetchMaterielByPartNumbers 参数：{}", partNumbers);
		String result = HttpClientUtil.doPostJson(fetchMaterielByPartNumbersUrl, gson.toJson(partNumbers));
		log.info("fetchMaterielByPartNumbers 结果,{}", result);
		WipApiResult wipApiResult = gson.fromJson(result, WipApiResult.class);
		if (wipApiResult.getResCode().equals("00000")) {
			Map<String, Integer> datas = gson.fromJson(wipApiResult.getResData(),
					new TypeToken<Map<String, Integer>>() {
					}.getType());
			return datas;
		} else {
			throw new IllegalArgumentException(RestResponseCode.WIP_MATERIEL_USEABLE_EXCEPTION.getLabel());
		}
	}

	/**
	 * 排产下发
	 * 
	 * @return
	 */
	public boolean submit(List<TakeOrderDTO> takeOrderDTOs) {
		WipApiResult wipApiResult = new WipApiResult();
		String result = HttpClientUtil.doPostJson(submitUrl, gson.toJson(takeOrderDTOs));
		log.info("WIP排产下发结果,{}", result);
		wipApiResult = gson.fromJson(result, WipApiResult.class);
		return true;
	}

	/**
	 * 获取当前工位的可用信息
	 * 
	 * @return
	 */
	@Transactional
	public boolean fetchNewWorkstationStatus() {
		WipApiResult wipApiResult = new WipApiResult();
		String result = HttpClientUtil.doPost(fetchNewWorkstationStatusUrl);
		log.info("WIP获取当前工位的可用信息结果,{}", result);
		wipApiResult = gson.fromJson(result, WipApiResult.class);
		if (wipApiResult.isSuccess()) {
			List<LineWorkstationsStatus> list = gson.fromJson(wipApiResult.getResData(),
					new TypeToken<List<LineWorkstationsStatus>>() {
					}.getType());

			lineWorkstationStatusService.save(list);
			return true;
		}
		return false;
	}

	public JSONObject deviceAndLineFeedback() {
		return new JSONObject();
	}

	public JSONObject materielFeedback() {
		return new JSONObject();
	}

	public JSONObject distributeFeedback() {
		return new JSONObject();
	}

}
