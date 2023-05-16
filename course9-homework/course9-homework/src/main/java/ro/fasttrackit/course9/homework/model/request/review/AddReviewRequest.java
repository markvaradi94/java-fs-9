package ro.fasttrackit.course9.homework.model.request.review;

import lombok.Builder;
import ro.fasttrackit.course9.homework.model.dto.ReviewDto;

@Builder
public record AddReviewRequest(
        String roomId,
        ReviewDto review
) {
}
