package ro.fasttrackit.course9.homework.service.operation.review;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ro.fasttrackit.course9.homework.exception.ResourceNotFoundException;
import ro.fasttrackit.course9.homework.model.entity.ReviewEntity;
import ro.fasttrackit.course9.homework.model.request.review.UpdateReviewRequest;
import ro.fasttrackit.course9.homework.operation.Operation;
import ro.fasttrackit.course9.homework.operation.validator.OperationValidator;
import ro.fasttrackit.course9.homework.repository.ReviewRepository;
import ro.fasttrackit.course9.homework.service.operation.room.CheckRoomExistsOperation;

import static com.google.common.base.Strings.isNullOrEmpty;

@Component
@RequiredArgsConstructor
public class UpdateReviewOperation implements Operation<UpdateReviewRequest, ReviewEntity> {
    private final CheckRoomExistsOperation checkRoomExistsOperation;
    private final ReviewRepository repository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public ReviewEntity doExecute(UpdateReviewRequest request) {
        ReviewEntity reviewToPatch = repository.findByReviewIdAndRoomId(request.reviewId(), request.roomId())
                .orElseThrow(() -> new ResourceNotFoundException("Could not find review with id " + request.reviewId() + "for room with id " + request.roomId()));
        JsonNode patchedReviewJson = request.jsonPatch().apply(objectMapper.valueToTree(reviewToPatch));
        ReviewEntity patchedReview = objectMapper.treeToValue(patchedReviewJson, ReviewEntity.class);
        return repository.addReview(patchedReview);
    }

    @Override
    public OperationValidator<UpdateReviewRequest> getValidator() {
        return (validator, request) -> {
            validator.check(isNullOrEmpty(request.roomId()))
                    .ifTrue("You must provide a valid roomId for this request.")
                    .ifFalse(() -> validator.check(checkRoomExistsOperation.execute(request.roomId()))
                            .ifFalse("Could not find room with id " + request.roomId())
                            .ifTrue(() -> validator.check(isNullOrEmpty(request.reviewId()))
                                    .ifTrue("You must provide a valid reviewId for this request.")
                                    .ifFalse(() -> validator.check(request.jsonPatch() != null)
                                            .ifFalse("Please provide a valid json patch for this request."))));
            return validator;
        };
    }
}
