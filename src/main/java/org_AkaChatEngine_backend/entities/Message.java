package org_AkaChatEngine_backend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor //This tells Lombok to automatically generate a constructor with all the fields.
@NoArgsConstructor //This tells Lombok to generate a zero-argument constructor
@Getter
@Setter
public class Message {

    private String sender; //who is sender
    private String content;     //may be messaged, video,image...
    private LocalDateTime timeStamp;    //which time message be sending

    public Message(String sender, String content) { //if you send sender and content that time current time show
        this.sender = sender;
        this.content = content;
        this.timeStamp = LocalDateTime.now();   //current time
    }
}