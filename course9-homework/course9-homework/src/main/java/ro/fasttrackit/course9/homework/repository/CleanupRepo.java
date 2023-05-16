package ro.fasttrackit.course9.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.fasttrackit.course9.homework.model.entity.CleanupEntity;

import java.util.Optional;

public interface CleanupRepo extends MongoRepository<CleanupEntity, String> {
    Optional<CleanupEntity> findByIdAndRoomId(String cleanupId, String roomId);

    boolean existsByIdAndRoomId(String cleanupId, String roomId);
}
