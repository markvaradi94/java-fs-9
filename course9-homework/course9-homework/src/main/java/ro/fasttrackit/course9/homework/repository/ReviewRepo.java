package ro.fasttrackit.course9.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.fasttrackit.course9.homework.model.entity.ReviewEntity;

import java.util.Optional;

public interface ReviewRepo extends MongoRepository<ReviewEntity, String> {
    Optional<ReviewEntity> findByIdAndRoomId(String reviewId, String roomId);
}
