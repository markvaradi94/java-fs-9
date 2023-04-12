package ro.fasttrackit.course9.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.fasttrackit.course9.homework.model.entity.RoomEntity;

public interface RoomRepo extends MongoRepository<RoomEntity, String> {
    boolean existsByNumber(String number);

    boolean existsByNumberAndIdNot(String number, String id);
}
