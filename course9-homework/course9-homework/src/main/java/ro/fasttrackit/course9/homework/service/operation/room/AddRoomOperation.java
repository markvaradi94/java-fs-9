package ro.fasttrackit.course9.homework.service.operation.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fasttrackit.course9.homework.model.dto.RoomDto;
import ro.fasttrackit.course9.homework.model.entity.RoomEntity;
import ro.fasttrackit.course9.homework.model.mappers.RoomMappers;
import ro.fasttrackit.course9.homework.operation.Operation;
import ro.fasttrackit.course9.homework.operation.validator.OperationValidator;
import ro.fasttrackit.course9.homework.repository.RoomRepo;
import ro.fasttrackit.course9.homework.repository.RoomRepository;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Optional.ofNullable;
import static java.util.UUID.randomUUID;

@Component
@RequiredArgsConstructor
public class AddRoomOperation implements Operation<RoomDto, RoomEntity> {
    private final RoomRepository repository;
    private final RoomRepo repo;
    private final RoomMappers mappers = new RoomMappers();

    @Override
    public RoomEntity doExecute(RoomDto request) {
        return repository.addRoom(mappers.toDb(addRoomId(request)));
    }

    private RoomDto addRoomId(RoomDto request) {
        return request.withId(ofNullable(request.id())
                .orElse(randomUUID().toString()));
    }

    @Override
    public OperationValidator<RoomDto> getValidator() {
        return (validator, request) -> {
            validator.check(isNullOrEmpty(request.number()))
                    .ifTrue("Room number cannot be null or empty.")
                    .ifFalse(() -> validator.check(repo.existsByNumber(request.number()))
                            .ifTrue("Room number cannot be duplicate."));
            return validator;
        };
    }
}
