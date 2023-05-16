package ro.fasttrackit.course9.homework.service.operation.cleanup;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ro.fasttrackit.course9.homework.exception.ResourceNotFoundException;
import ro.fasttrackit.course9.homework.model.entity.CleanupEntity;
import ro.fasttrackit.course9.homework.model.request.cleanup.UpdateCleanupRequest;
import ro.fasttrackit.course9.homework.operation.Operation;
import ro.fasttrackit.course9.homework.operation.validator.OperationValidator;
import ro.fasttrackit.course9.homework.repository.CleanupRepository;
import ro.fasttrackit.course9.homework.service.operation.room.CheckRoomExistsOperation;

import static com.google.common.base.Strings.isNullOrEmpty;

@Component
@RequiredArgsConstructor
public class UpdateCleanupOperation implements Operation<UpdateCleanupRequest, CleanupEntity> {
    private final CheckRoomExistsOperation checkRoomExistsOperation;
    private final CleanupRepository cleanupRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public CleanupEntity doExecute(UpdateCleanupRequest request) {
        CleanupEntity cleanupToPatch = cleanupRepository.findByCleanupIdAndRoomId(request.cleanupId(), request.roomId())
                .orElseThrow(() -> new ResourceNotFoundException("Could not find cleanup with id " + request.cleanupId() + "for room with id " + request.roomId()));
        JsonNode patchedCleanupJson = request.jsonPatch().apply(objectMapper.valueToTree(cleanupToPatch));
        CleanupEntity patchedCleanup = objectMapper.treeToValue(patchedCleanupJson, CleanupEntity.class);
        return cleanupRepository.addCleanup(patchedCleanup);
    }

    @Override
    public OperationValidator<UpdateCleanupRequest> getValidator() {
        return (validator, request) -> {
            validator.check(isNullOrEmpty(request.roomId()))
                    .ifTrue("You must provide a valid roomId for this request.")
                    .ifFalse(() -> validator.check(checkRoomExistsOperation.execute(request.roomId()))
                            .ifFalse("Could not find room with id " + request.roomId())
                            .ifTrue(() -> validator.check(isNullOrEmpty(request.cleanupId()))
                                    .ifTrue("You must provide a valid cleanupId for this request.")
                                    .ifFalse(() -> validator.check(request.jsonPatch() != null)
                                            .ifFalse("Please provide a valid json patch for this request."))));
            return validator;
        };
    }
}
