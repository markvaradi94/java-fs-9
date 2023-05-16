package ro.fasttrackit.course9.homework.service.operation.room;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ro.fasttrackit.course9.homework.exception.ResourceNotFoundException;
import ro.fasttrackit.course9.homework.model.entity.RoomEntity;
import ro.fasttrackit.course9.homework.model.request.room.UpdateRoomRequest;
import ro.fasttrackit.course9.homework.operation.Operation;
import ro.fasttrackit.course9.homework.operation.validator.OperationValidator;
import ro.fasttrackit.course9.homework.repository.RoomRepository;

import static com.google.common.base.Strings.isNullOrEmpty;

@Component
@RequiredArgsConstructor
public class UpdateRoomOperation implements Operation<UpdateRoomRequest, RoomEntity> {
    private final RoomRepository repository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public RoomEntity doExecute(UpdateRoomRequest request) {
        RoomEntity roomToPatch = repository.findById(request.roomId())
                .orElseThrow(() -> new ResourceNotFoundException("Could not find room with id " + request.roomId()));
        JsonNode patchedRoomJson = request.jsonPatch().apply(objectMapper.valueToTree(roomToPatch));
        RoomEntity patchedRoom = objectMapper.treeToValue(patchedRoomJson, RoomEntity.class);
        return repository.addRoom(patchedRoom);
    }

    @Override
    public OperationValidator<UpdateRoomRequest> getValidator() {
        return (validator, request) -> {
            validator.check(isNullOrEmpty(request.roomId()))
                    .ifTrue("You must provide a valid roomId for this request.")
                    .ifFalse(() -> validator.check(request.jsonPatch() != null)
                            .ifFalse("Please provide a valid json patch for this request."));
            return validator;
        };
    }
}
