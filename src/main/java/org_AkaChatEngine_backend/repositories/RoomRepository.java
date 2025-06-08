package org_AkaChatEngine_backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org_AkaChatEngine_backend.entities.Room;
//import org_AkaChatEngine_backend.entities.Room;

public interface RoomRepository extends MongoRepository<Room, String> {
   // Get room using roomId (custom field, not Mongo _id)
    Room findByRoomId(String roomId);
}
