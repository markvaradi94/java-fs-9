package ro.fasttrackit.course9.homework.service.operation.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fasttrackit.course9.homework.operation.Operation;
import ro.fasttrackit.course9.homework.repository.RoomRepo;

@Component
@RequiredArgsConstructor
public class CheckRoomExistsOperation implements Operation<String, Boolean> {
    private final RoomRepo repo;

    @Override
    public Boolean doExecute(String roomId) {
        return repo.existsById(roomId);
    }
}
