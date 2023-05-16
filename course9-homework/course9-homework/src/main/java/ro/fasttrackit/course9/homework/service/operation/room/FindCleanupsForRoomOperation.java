package ro.fasttrackit.course9.homework.service.operation.room;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ro.fasttrackit.course9.homework.model.entity.CleanupEntity;
import ro.fasttrackit.course9.homework.model.filters.CleanupFilters;
import ro.fasttrackit.course9.homework.model.request.cleanup.GetCleanupsRequest;
import ro.fasttrackit.course9.homework.operation.Operation;
import ro.fasttrackit.course9.homework.operation.validator.OperationValidator;
import ro.fasttrackit.course9.homework.service.operation.cleanup.GetCleanupsOperation;

import java.util.Collections;

import static java.util.Collections.singletonList;

@Component
@RequiredArgsConstructor
public class FindCleanupsForRoomOperation implements Operation<String, Page<CleanupEntity>> {
    private final CheckRoomExistsOperation checkRoomExistsOperation;
    private final GetCleanupsOperation getCleanupsOperation;

    @Override
    public Page<CleanupEntity> doExecute(String roomId) {
        return getCleanupsOperation.execute(GetCleanupsRequest.builder()
                .filters(CleanupFilters.builder()
                        .roomId(singletonList(roomId))
                        .build())
                .build());
    }

    @Override
    public OperationValidator<String> getValidator() {
        return (validator, roomId) -> {
            validator.check(checkRoomExistsOperation.execute(roomId))
                    .ifFalse("Could not find room with id " + roomId);
            return validator;
        };
    }
}
