package com.ge.digital.schedule.service;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ge.digital.schedule.util.HttpClientUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class PlantSimulationService {

	private static final Logger log = LoggerFactory.getLogger(PlantSimulationService.class);

	@Value("${custom.plantsimulation.url}")
	String psInvokeUrl;

	@Value("${custom.plantsimulation.serverkey}")
	String localKey;

	@Autowired
	Gson gson;

	/**
	 * 调用
	 * 
	 * @throws Exception
	 */
	public Boolean invoke() {
		Map<String, String> params = new HashMap<>();
		long currentTime = System.currentTimeMillis();
		String sign = encoderByMd5((currentTime + localKey));
		params.put("timestemp", String.valueOf(currentTime));
		params.put("sign", sign);
		String result = HttpClientUtil.doGet(psInvokeUrl, params);
		log.info("result :{}", result);
		Map<String, String> map = gson.fromJson(result, new TypeToken<Map<String, String>>() {
		}.getType());
		log.info("schedule-ps invoke result : {}", map);
		String code = map.get("code");
		if (StringUtils.equals(code, "00000")) {
			return true;
		}
		return false;
	}

	public String encoderByMd5(String str) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			String md5Str = Base64.getEncoder().encodeToString(md5.digest(str.getBytes("utf-8")));
			log.info(md5Str);
			return md5Str;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("EncoderByMd5 has an error :{}", e);
		}
		return "";
	}

}
