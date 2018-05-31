package com.ge.digital.spo.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;




@SpringBootApplication
@EnableEurekaServer
public class EurekaBackendApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(EurekaBackendApplication.class, args);
		
	}
}
