package org_AkaChatEngine_backend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "rooms")  //Map With MongoDb
@Getter
@Setter
@AllArgsConstructor //parameterized constructor help  for manual object creation or DI or Testing
@NoArgsConstructor  //Zero parameterized constructor help for deserialization
public class Room {

    @Id
    private  String id; //Primary Key:----->if i don’t set id yourself, Spring will auto-generate one for you using MongoDB's ObjectId.means  Then MongoDB (and Spring Data) will automatically generate a unique id (like "68459b082d972f1ca7b69169") when you save the object to the database.
    private  String roomId;     //User Create room id bcz he joins easily whatever he pass
    private List<Message> messages = new ArrayList<>(); //This line creates a list to store multiple messages inside each Room. In your real-time chat app (AkaChatEngine), every room can have many messages, so you need a collection to hold them.
}
