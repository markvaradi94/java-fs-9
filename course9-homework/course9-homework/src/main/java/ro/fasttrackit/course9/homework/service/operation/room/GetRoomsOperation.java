package ro.fasttrackit.course9.homework.service.operation.room;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ro.fasttrackit.course9.homework.model.entity.RoomEntity;
import ro.fasttrackit.course9.homework.model.request.room.GetRoomsRequest;
import ro.fasttrackit.course9.homework.operation.Operation;
import ro.fasttrackit.course9.homework.repository.RoomRepository;

@Component
@RequiredArgsConstructor
public class GetRoomsOperation implements Operation<GetRoomsRequest, Page<RoomEntity>> {
    private final RoomRepository repository;

    @Override
    public Page<RoomEntity> doExecute(GetRoomsRequest request) {
        return repository.findAll(request);
    }
}
