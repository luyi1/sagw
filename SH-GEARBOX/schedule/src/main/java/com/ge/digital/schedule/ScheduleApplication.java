package com.ge.digital.schedule;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

import com.google.gson.Gson;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Hello world!
 *
 */
@EntityScan({"com.ge.digital.schedule.entity", "com.ge.digital.gearbox.common.autoIncrKey"})
@EnableJpaRepositories({"com.ge.digital.gearbox.common.autoIncrKey","com.ge.digital.schedule.mapper" })
@SpringBootApplication(scanBasePackages={"com.ge.digital.gearbox.common.autoIncrKey","com.ge.digital.schedule"}) 
@EnableSwagger2
@EnableEurekaClient
public class ScheduleApplication {

	@Bean
	Gson gson() {
		return new Gson();
	}

	@Bean
	Queue confirmQueue() {
		return new Queue("schedule.request.confirm");
	}

	@Bean
	Queue deviceandlinecheckQueue() {
		return new Queue("schedule.request.deviceandlinecheck");
	}

	@Bean
	Queue masterdatacheckQueue() {
		return new Queue("schedule.request.masterdatacheck");
	}

	@Bean
	public OpenEntityManagerInViewFilter openEntityManagerInViewFilter() {
		return new OpenEntityManagerInViewFilter();
	}

	public static void main(String[] args) {
		new SpringApplication(ScheduleApplication.class).run(args);
	}
}
