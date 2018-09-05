package com.ge.digital.schedule.ps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SchedulePSApplication {
	public static void main(String[] args) {
		new SpringApplication(SchedulePSApplication.class).run(args);
	}
}
