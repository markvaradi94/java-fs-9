package ro.fasttrackit.course9.homework.service.operation.cleanup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fasttrackit.course9.homework.model.entity.CleanupEntity;
import ro.fasttrackit.course9.homework.model.request.cleanup.DeleteCleanupRequest;
import ro.fasttrackit.course9.homework.operation.Operation;
import ro.fasttrackit.course9.homework.operation.validator.OperationValidator;
import ro.fasttrackit.course9.homework.repository.CleanupRepository;
import ro.fasttrackit.course9.homework.service.operation.room.CheckRoomExistsOperation;

import java.util.Optional;

import static com.google.common.base.Strings.isNullOrEmpty;

@Component
@RequiredArgsConstructor
public class DeleteCleanupOperation implements Operation<DeleteCleanupRequest, Optional<CleanupEntity>> {
    private final CheckRoomExistsOperation checkRoomExistsOperation;
    private final CleanupRepository cleanupRepository;

    @Override
    public Optional<CleanupEntity> doExecute(DeleteCleanupRequest request) {
        return cleanupRepository.deleteById(request.cleanupId(), request.roomId());
    }

    @Override
    public OperationValidator<DeleteCleanupRequest> getValidator() {
        return (validator, request) -> {
            validator.check(isNullOrEmpty(request.roomId()))
                    .ifTrue("You must provide a valid roomId for this request.")
                    .ifFalse(() -> validator.check(checkRoomExistsOperation.execute(request.roomId()))
                            .ifFalse("Could not find room with id " + request.roomId())
                            .ifTrue(() -> validator.check(isNullOrEmpty(request.cleanupId()))
                                    .ifTrue("You must provide a valid cleanupId for this request.")));
            return validator;
        };
    }
}
