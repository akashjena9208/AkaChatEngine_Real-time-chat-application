package org_AkaChatEngine_backend.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org_AkaChatEngine_backend.entities.Message;
import org_AkaChatEngine_backend.entities.Room;
import org_AkaChatEngine_backend.playload.MessageRequest;
import org_AkaChatEngine_backend.repositories.RoomRepository;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

//Handling The Chat
@Controller
@CrossOrigin("http://localhost:3000")
public class ChatController {

    private RoomRepository roomRepository;
    //Constructor injection is preferred because it makes the class immutable, makes dependencies explicit, and simplifies unit testing.
    public ChatController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    //for sending and receiving messages
    @MessageMapping("/sendMessage/{roomId}")// /app/sendMessage/roomId     //if i pass the room id
    @SendTo("/topic/room/{roomId}")//subscribe   //publish the message in this url
    /*@MessageMapping is used to handle incoming WebSocket messages from clients, like how @PostMapping handles HTTP POST.
      @SendTo is used to define where the server will broadcast the response, usually to a topic that many clients are subscribed to.*/
    public Message sendMessage(
            @DestinationVariable String roomId, //It’s similar to @PathVariable in REST controllers, but for WebSocket endpoints.It allows you to access dynamic parts of the STOMP destination (like /app/chat/{roomId}) in your controller method.
            @RequestBody MessageRequest request

    ) {

        Room room = roomRepository.findByRoomId(request.getRoomId()); //fetch the room id
        //Message information set
        Message message = new Message();
        message.setContent(request.getContent());
        message.setSender(request.getSender());
        message.setTimeStamp(LocalDateTime.now());
        //exception handel
        if (room != null) {
            room.getMessages().add(message);
            roomRepository.save(room);
        } else {
            throw new RuntimeException("room not found !!");
        }

        return message;
    }
}
