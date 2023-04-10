package ro.fasttrackit.mongodemo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.fasttrackit.mongodemo.entity.Room;

import java.util.List;

public interface RoomRepository extends MongoRepository<Room, String> {
    List<Room> findByRoomNumberIgnoreCase(String number);
}
