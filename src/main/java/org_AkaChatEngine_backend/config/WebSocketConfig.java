//package org_AkaChatEngine_backend.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
////this class  used for configure the websocket
////@EnableWebSocketMessageBroker is used in Spring Boot to enable WebSocket support using a message broker, typically for STOMP messaging. It tells Spring to treat the application as a WebSocket message broker application, allowing real-time communication between clients and the server.  For example, I used it in a chat application where clients send messages to /app/sendMessage, and the server broadcasts to subscribed users via /topic/roomId."
//// WebSocketMessageBrokerConfigurer using this interface we configure the method  configureMessageBroker and
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig  implements WebSocketMessageBrokerConfigurer {
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        WebSocketMessageBrokerConfigurer.super.registerStompEndpoints(registry);
//    }
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        WebSocketMessageBrokerConfigurer.super.configureMessageBroker(registry);
//    }
//}
