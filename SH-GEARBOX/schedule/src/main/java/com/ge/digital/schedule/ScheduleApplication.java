package com.ge.digital.schedule;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.ge.digital.gearbox.common.filter.CorsFilter;
import com.google.gson.Gson;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Hello world!
 *
 */
@EntityScan({"com.ge.digital.schedule.entity", "com.ge.digital.gearbox.common.autoIncrKey"})
@EnableJpaRepositories({"com.ge.digital.gearbox.common.autoIncrKey","com.ge.digital.schedule.mapper" })
@SpringBootApplication(scanBasePackages={"com.ge.digital.gearbox.common","com.ge.digital.schedule"}) 
@EnableSwagger2
@EnableFeignClients
@EnableEurekaClient
@EnableCaching
public class ScheduleApplication {

	@Bean
	Gson gson() {
		return new Gson();
	}
	
	@Bean
    @LoadBalanced
    RestTemplate restTemplateRibbon() {
        return new RestTemplate();
    }
	@Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
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
	public CorsFilter getCorsFilter() {
		return new CorsFilter();
	}

	@Bean
	public FilterRegistrationBean filterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(getCorsFilter());
		registration.setName("CorsFilter");
		registration.setEnabled(true);
		registration.setOrder(1);
		return registration;
	}
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
	
	@Bean
	public OpenEntityManagerInViewFilter openEntityManagerInViewFilter() {
		return new OpenEntityManagerInViewFilter();
	}

	public static void main(String[] args) {
		new SpringApplication(ScheduleApplication.class).run(args);
	}
}
