package ro.fasttrackit.course9.homework.service.operation.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fasttrackit.course9.homework.model.entity.RoomEntity;
import ro.fasttrackit.course9.homework.operation.Operation;
import ro.fasttrackit.course9.homework.repository.RoomRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DeleteRoomOperation implements Operation<String, Optional<RoomEntity>> {
    private final RoomRepository repository;

    @Override
    public Optional<RoomEntity> doExecute(String roomId) {
        return repository.deleteById(roomId);
    }
}
