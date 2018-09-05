package com.ge.digital.schedule.ps;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlantSimulationController {

	private static final Logger log = LoggerFactory.getLogger(PlantSimulationController.class);

	@Autowired
	PlantSimulationService plantSimulationService;

	@GetMapping("invoke")
	public Map<String, String> invoke(String timestemp, String sign) {
		String localKey = "schedule-ps";
		String md5 = encoderByMd5(timestemp + localKey);
		Map<String, String> result = new HashMap<>();
		try {
			if (!md5.equals(sign)) {
				result.put("code", "xxxxx");
				result.put("msg", "fail");
				return result;
			}
			plantSimulationService.invokeByCom();
			result.put("code", "00000");
			result.put("msg", "");
		} catch (Exception e) {
			// TODO: handle exception
			result.put("code", "11111");
			result.put("msg", e.getMessage());
		}
		log.info("invoke :{}", result);
		return result;
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
