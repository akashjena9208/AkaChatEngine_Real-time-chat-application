package org_AkaChatEngine_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org_AkaChatEngine_backend.entities.Message;
import org_AkaChatEngine_backend.entities.Room;
import org_AkaChatEngine_backend.repositories.RoomRepository;

import java.util.List;

@RestController
//@RestController is a Spring annotation used to define a controller where every method returns a JSON or other HTTP response body directly, instead of rendering a view (like HTML or JSP).
@RequestMapping("/api/v1/rooms")
//room create,join,leave,message fetch
public class RoomController {
    //I used constructor injection because it's cleaner, testable, and Spring auto-injects it without needing @Autowired if there's only one constructor
    // @Autowired is optional if there’s only one constructor:
    //but if multiple constructors, then Spring gets confused unless you tell it which constructor to use — that’s when @Autowired is required.
    private RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    //create room
    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody String roomId) {

        // Validation :- Checked room exit ot not
        //if room is exit
        if (roomRepository.findByRoomId(roomId) != null) {
            //room is already there
            return ResponseEntity.badRequest().body("Room already exists!");

        }

        //if room is not exit create new  room
        //create new room
        Room room = new Room();
        room.setRoomId(roomId);
        Room savedRoom = roomRepository.save(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);

    }

    //get room  //anybody room join
    //get room: join
    @GetMapping("/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId)  //need room id bcz which room join
    {
        //First Fetch and checked room present or not if not present error else return
        Room room = roomRepository.findByRoomId(roomId);
        if (room == null) {
            return ResponseEntity.badRequest().body("Room not found!!");
        }
        return ResponseEntity.ok(room);

    }


    //get  message of room
    // Fetch All the msg to user
    //This method uses @PathVariable to get the room ID from the URL and @RequestParam to handle pagination parameters (page, size) for fetching messages efficiently.
    //GET /room123/messages?page=1&size=10 :---This fetches messages for room "room123", from page 1, with 10 messages per page.
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessages(@PathVariable String roomId,        // To extract the roomId from the URL path.   AND  example :-GET /chat/room123/messages
                                                     @RequestParam(value = "page", defaultValue = "0", required = false) int page, //To accept an optional page number from the query parameters for pagination.  example :-GET /chat/room123/messages?page=1
                                                     @RequestParam(value = "size", defaultValue = "20", required = false) int size  // To accept an optional size (i.e., number of messages per page).   example GET /chat/room123/messages?page=1&size=10
                                                     //combine 3 :- /room123/messages?page=1&size=10
    ) {
        //Room Fetch
        Room room = roomRepository.findByRoomId(roomId);
        //checked null or not if null bad request
        if (room == null) {
            return ResponseEntity.badRequest().build();//build() is a method commonly used in the Builder design pattern to create and return the final object after setting its properties.
        }
        //if room id is found then
        //get messages :
        //pagination
        List<Message> messages = room.getMessages(); //fetches all messages from the Room object. and Store as list
        int start = Math.max(0, messages.size() - (page + 1) * size);   //	Calculates start index for current page from the end of the list
        int end = Math.min(messages.size(), start + size); //Makes sure we don’t go out of bound
        List<Message> paginatedMessages = messages.subList(start, end); //	Extracts the current page of messages
        return ResponseEntity.ok(paginatedMessages); //	Returns the paginated list in HTTP response

        //📌 Why from end to start?
        //This logic gives latest messages first, which is a common chat behavior (latest messages shown at the bottom).
    }

}
