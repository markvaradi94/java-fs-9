package ro.fasttrackit.course9.homework.service.operation.room;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ro.fasttrackit.course9.homework.model.entity.ReviewEntity;
import ro.fasttrackit.course9.homework.model.filters.ReviewFilters;
import ro.fasttrackit.course9.homework.model.request.review.GetReviewsRequest;
import ro.fasttrackit.course9.homework.operation.Operation;
import ro.fasttrackit.course9.homework.operation.validator.OperationValidator;
import ro.fasttrackit.course9.homework.service.operation.review.GetReviewsOperation;

import static java.util.Collections.singletonList;

@Component
@RequiredArgsConstructor
public class FindReviewsForRoomOperation implements Operation<String, Page<ReviewEntity>> {
    private final CheckRoomExistsOperation checkRoomExistsOperation;
    private final GetReviewsOperation getReviewsOperation;

    @Override
    public Page<ReviewEntity> doExecute(String roomId) {
        return getReviewsOperation.execute(GetReviewsRequest.builder()
                .filters(ReviewFilters.builder()
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
