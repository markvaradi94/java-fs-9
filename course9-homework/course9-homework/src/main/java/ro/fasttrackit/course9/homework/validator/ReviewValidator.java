package ro.fasttrackit.course9.homework.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fasttrackit.course9.homework.exception.ValidationException;
import ro.fasttrackit.course9.homework.model.entity.ReviewEntity;
import ro.fasttrackit.course9.homework.model.request.review.AddReviewRequest;
import ro.fasttrackit.course9.homework.operation.validator.OperationValidator;
import ro.fasttrackit.course9.homework.operation.validator.Validator;
import ro.fasttrackit.course9.homework.repository.ReviewRepo;
import ro.fasttrackit.course9.homework.repository.RoomRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.String.join;
import static java.util.Optional.empty;
import static java.util.Optional.of;

@Component
@RequiredArgsConstructor
public class ReviewValidator {
    private final ReviewRepo reviewRepo;
    private final RoomRepo roomRepo;

    public void validateNewThrow(ReviewEntity review) {
        validate(review, true)
                .ifPresent(validationException -> {
                    throw validationException;
                });
    }

    public void validateExistsOrThrow(String reviewId) {
        exists(reviewId)
                .ifPresent(validationException -> {
                    throw validationException;
                });
    }

    public void validateRoomExistsOrThrow(String roomId) {
        roomExists(roomId)
                .ifPresent(validationException -> {
                    throw validationException;
                });
    }

    public void validateReplaceThrow(String roomId, ReviewEntity review) {
        exists(roomId)
                .or(() -> validate(review, false))
                .ifPresent(validationException -> {
                    throw validationException;
                });
    }

    private Optional<ValidationException> validate(ReviewEntity review, boolean newReview) {
        List<String> errorMessages = new ArrayList<>();
        if (isNullOrEmpty(review.getId()) && !newReview) {
            errorMessages.add("Review id cannot be null or empty.");
        } else if (isNullOrEmpty(review.getRoomId())) {
            errorMessages.add("Review must be attributed to a room");
        } else if (review.getRating() == null || review.getRating() < 1 || review.getRating() > 5) {
            errorMessages.add("Review must have a valid rating value (1-5).");
        } else if (review.getTourist() == null) {
            errorMessages.add("Review must be written by an existing tourist");
        } else if (newReview && review.getId() != null && reviewRepo.existsById(review.getId())) {
            errorMessages.add("Cannot have duplicate id reviews.");
        }
        return errorMessages.isEmpty()
                ? empty()
                : of(new ValidationException(join(", ", errorMessages)));
    }

    private Optional<ValidationException> exists(String reviewId) {
        return reviewRepo.existsById(reviewId)
                ? empty()
                : of(new ValidationException("Review with id " + reviewId + " could not be found."));
    }

    private Optional<ValidationException> roomExists(String roomId) {
        return roomRepo.existsById(roomId)
                ? empty()
                : of(new ValidationException("Room with id " + roomId + " could not be found."));
    }
}
