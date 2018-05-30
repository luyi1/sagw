package com.ge.digital.spo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.CrossOrigin;


@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@CrossOrigin
public class ApiGateWayApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(ApiGateWayApplication.class, args);
	}
	
}
