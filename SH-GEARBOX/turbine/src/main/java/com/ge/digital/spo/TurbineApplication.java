package com.ge.digital.spo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


@SpringBootApplication
@EnableHystrixDashboard
@EnableTurbine
public class TurbineApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(TurbineApplication.class, args);
	}
	
}
