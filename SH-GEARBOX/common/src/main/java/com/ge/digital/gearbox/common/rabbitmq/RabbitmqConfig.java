package com.ge.digital.gearbox.common.rabbitmq;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration	
public class RabbitmqConfig {
	@Value("${spring.rabbitmq.host}")
	private String host;

	@Value("${spring.rabbitmq.username}")
	private String user;

	@Value("${spring.rabbitmq.password}")
	private String pass;

	@Value("${spring.rabbitmq.virtual-host}")
	private String vhost;
	
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
		connectionFactory.setVirtualHost(vhost);
		connectionFactory.setUsername(user);
		connectionFactory.setPassword(pass);
		return connectionFactory;
	}

	@Bean
	public AmqpAdmin amqpAdmin() {
		return new RabbitAdmin(connectionFactory());
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		template.setMessageConverter(messageConverter());
		return template;
	}

	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
