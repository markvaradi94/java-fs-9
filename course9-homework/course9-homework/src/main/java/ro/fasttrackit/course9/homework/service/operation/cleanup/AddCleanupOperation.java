package ro.fasttrackit.course9.homework.service.operation.cleanup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fasttrackit.course9.homework.model.dto.CleanupDto;
import ro.fasttrackit.course9.homework.model.entity.CleanupEntity;
import ro.fasttrackit.course9.homework.model.mappers.CleanupMappers;
import ro.fasttrackit.course9.homework.model.request.cleanup.AddCleanupRequest;
import ro.fasttrackit.course9.homework.operation.Operation;
import ro.fasttrackit.course9.homework.operation.validator.OperationValidator;
import ro.fasttrackit.course9.homework.repository.CleanupRepository;
import ro.fasttrackit.course9.homework.service.operation.room.CheckRoomExistsOperation;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Optional.ofNullable;
import static java.util.UUID.randomUUID;


@Component
@RequiredArgsConstructor
public class AddCleanupOperation implements Operation<AddCleanupRequest, CleanupEntity> {
    private final CheckRoomExistsOperation checkRoomExistsOperation;
    private final CleanupRepository cleanupRepository;
    private final CleanupMappers mappers = new CleanupMappers();

    @Override
    public CleanupEntity doExecute(AddCleanupRequest request) {
        var cleanup = request.cleanup();
        return cleanupRepository.addCleanup(mappers.toDb((addIdsToCleanup(request, cleanup))));
    }

    private CleanupDto addIdsToCleanup(AddCleanupRequest request, CleanupDto cleanup) {
        return cleanup
                .withRoomId(request.roomId())
                .withId(ofNullable(request.roomId())
                        .orElse(randomUUID().toString()));
    }

    @Override
    public OperationValidator<AddCleanupRequest> getValidator() {
        return (validator, request) -> {
            validator.check(isNullOrEmpty(request.roomId()))
                    .ifTrue("You must provide a valid roomId for this request.")
                    .ifFalse(() -> validator.check(checkRoomExistsOperation.execute(request.roomId()))
                            .ifFalse("Could not find room with id " + request.roomId())
                            .ifTrue(() -> validator.check(request.cleanup() != null)
                                    .ifFalse("You cannot add a null cleanup")));
            return validator;
        };
    }
}
