package com.ge.digital.schedule.ps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class SchedulePSApplication {
	public static void main(String[] args) {
		new SpringApplication(SchedulePSApplication.class).run(args);
	}
}
