package ro.fasttrackit.course9.homework.model.request.review;

import lombok.Builder;

@Builder
public record DeleteReviewRequest(
        String roomId,
        String reviewId
) {
}
