package ro.fasttrackit.course9.homework.service.operation.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fasttrackit.course9.homework.model.entity.ReviewEntity;
import ro.fasttrackit.course9.homework.model.request.review.DeleteReviewRequest;
import ro.fasttrackit.course9.homework.operation.Operation;
import ro.fasttrackit.course9.homework.operation.validator.OperationValidator;
import ro.fasttrackit.course9.homework.repository.ReviewRepository;
import ro.fasttrackit.course9.homework.service.operation.room.CheckRoomExistsOperation;

import java.util.Optional;

import static com.google.common.base.Strings.isNullOrEmpty;

@Component
@RequiredArgsConstructor
public class DeleteReviewOperation implements Operation<DeleteReviewRequest, Optional<ReviewEntity>> {
    private final CheckRoomExistsOperation checkRoomExistsOperation;
    private final ReviewRepository repository;

    @Override
    public Optional<ReviewEntity> doExecute(DeleteReviewRequest request) {
        return repository.deleteById(request.reviewId(), request.roomId());
    }

    @Override
    public OperationValidator<DeleteReviewRequest> getValidator() {
        return (validator, request) -> {
            validator.check(isNullOrEmpty(request.roomId()))
                    .ifTrue("You must provide a valid roomId for this request.")
                    .ifFalse(() -> validator.check(checkRoomExistsOperation.execute(request.roomId()))
                            .ifFalse("Could not find room with id " + request.roomId())
                            .ifTrue(() -> validator.check(isNullOrEmpty(request.reviewId()))
                                    .ifTrue("You must provide a valid reviewId for this request.")));
            return validator;
        };
    }
}
