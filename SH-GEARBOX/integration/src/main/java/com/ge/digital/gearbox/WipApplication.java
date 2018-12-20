package com.ge.digital.gearbox;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName: WipApplication
 * @Description: TODO
 * @author: lu yi
 * @date: May 31, 2018 1:11:00 PM
 */
@SpringBootApplication(scanBasePackages={"com.ge.digital.gearbox.common","com.ge.digital.gearbox"}) 
//@EnableFeignClients
@EnableEurekaClient
public class WipApplication {

	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	Queue archiveQueue() {
		return new Queue("integration.scheduletask.archive");
	}
	
	@Bean
	Queue procQueue() {
		return new Queue("integeration.attachProc.event");
	}

	public static void main(String[] args) {
		new SpringApplication(WipApplication.class).run(args);
	}
}
