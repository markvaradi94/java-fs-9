package ro.fasttrackit.course9.homework.service.operation.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fasttrackit.course9.homework.model.entity.ReviewEntity;
import ro.fasttrackit.course9.homework.model.mappers.ReviewMappers;
import ro.fasttrackit.course9.homework.model.request.review.AddReviewRequest;
import ro.fasttrackit.course9.homework.operation.Operation;
import ro.fasttrackit.course9.homework.operation.validator.OperationValidator;
import ro.fasttrackit.course9.homework.repository.ReviewRepository;
import ro.fasttrackit.course9.homework.service.operation.room.CheckRoomExistsOperation;

import static com.google.common.base.Strings.isNullOrEmpty;

@Component
@RequiredArgsConstructor
public class AddReviewOperation implements Operation<AddReviewRequest, ReviewEntity> {
    private final CheckRoomExistsOperation checkRoomExistsOperation;
    private final ReviewRepository repository;
    private final ReviewMappers mappers = new ReviewMappers();

    @Override
    public ReviewEntity doExecute(AddReviewRequest request) {
        var review = request.review();
        return repository.addReview(mappers.toDb(review.withRoomId(request.roomId())));
    }

    @Override
    public OperationValidator<AddReviewRequest> getValidator() {
        return (validator, request) -> {
            validator.check(isNullOrEmpty(request.roomId()))
                    .ifTrue("You must provide a valid roomId for this request.")
                    .ifFalse(() -> validator.check(checkRoomExistsOperation.execute(request.roomId()))
                            .ifFalse("Could not find room with id " + request.roomId())
                            .ifTrue(() -> validator.check(request.review() != null)
                                    .ifFalse("You cannot add a null review")));
            return validator;
        };
    }
}
