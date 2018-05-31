package com.ge.digital.spo.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;




@SpringBootApplication
@EnableEurekaServer
public class EurekaBackendApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(EurekaBackendApplication.class, args);
		
	}
}
