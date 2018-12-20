//package com.ge.digital.gearbox.common.websocket;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer {
//
//	@Value("${websocket.host}")
//	private String host;
//
//	@Value("${websocket.port}")
//	private int port;
//
//	@Value("${websocket.username}")
//	private String userName;
//
//	@Value("${websocket.password}")
//	private String password;
//	
//	@Value("${websocket.virtual-host}")
//	private String vHost;
//
//	@Override
//	public void registerStompEndpoints(StompEndpointRegistry registry) {
//		// TODO Auto-generated method stub
//		registry.addEndpoint("/wssagw").withSockJS();
//		
//
//	}
//
//	@Override
//	public void configureMessageBroker(MessageBrokerRegistry registry) {
//		// TODO Auto-generated method stub
//		registry.enableStompBrokerRelay("/topic", "/queue").setRelayHost(host).setRelayPort(port)
//				.setClientLogin(userName).setClientPasscode(password).setVirtualHost(vHost);
//		registry.setApplicationDestinationPrefixes("/app");
//	}
//
//}
