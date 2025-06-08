
package org_AkaChatEngine_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

//this class  used for configure the websocket
//@EnableWebSocketMessageBroker is used in Spring Boot to enable WebSocket support using a message broker, typically for STOMP messaging. It tells Spring to treat the application as a WebSocket message broker application, allowing real-time communication between clients and the server.  For example, I used it in a chat application where clients send messages to /app/sendMessage, and the server broadcasts to subscribed users via /topic/roomId."
// WebSocketMessageBrokerConfigure using this interface we configure the method  configureMessageBroker and
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {  //This class is the core configuration for enabling and setting up WebSocket-based messaging in a Spring Boot application.
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        config.enableSimpleBroker("/topic");  // in memory message broker enable
        //  if clint subscribe  /topic/messages server rout in this  url
        /* config.enableSimpleBroker("/topic");
        Enables a simple in-memory message broker. and Messages will be broadcast to destinations starting with /topic.
        🔔Example: If a client subscribes to: /topic/messages Then the server will send (broadcast) messages to all subscribers of /topic/messages.
        🧠 Real-time Chat Usage: Used for broadcasting messages to all clients in a chat room.
        */


        config.setApplicationDestinationPrefixes("/app");
        //   /app/chat
        //server side controller method  message handel
        // prefix set server-side controller handler : @MessagingMapping("/chat)
        /* config.setApplicationDestinationPrefixes("/app");
            Tells Spring what prefix client messages must use when sending messages to the server. Messages with /app/ are routed to controller methods annotated with @MessageMapping.
            🔔 Example: Client sends message to /app/chat
                Server has:
                @MessageMapping("/chat")
                public void handleChatMessage(Message message) {
                // handle logic
                   }
                */
        /*
        * Component	Role
            /app/chat	Client sends to server, handled by controller (@MessageMapping)
            /topic/messages	Server broadcasts to clients listening on this topic
             enableSimpleBroker	Enables in-memory messaging to subscribed clients
             setApplicationPrefix	Defines route for client → server messages
        * */
    }

    //Write STOMP End Point  which used  who is a clint are subscribe
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {//This method registers the endpoint where WebSocket (STOMP) clients connect to initiate a WebSocket connection.

        registry.addEndpoint("/chat")//connection establishment     // Register the WebSocket endpoint that clients will use to connect to the server
                .setAllowedOrigins("http://localhost:3000") // Allow connections only from this specific origin (your frontend app URL) to prevent unauthorized access
                .withSockJS(); // Enable SockJS fallback options for browsers or environments that do not support native WebSockets

    }
    // /chat endpoint par connection apka establish hoga
}
