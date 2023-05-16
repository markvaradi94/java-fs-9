package ro.fasttrackit.course9.homework.model.request.review;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.Builder;

@Builder
public record UpdateReviewRequest(
        String roomId,
        String reviewId,
        JsonPatch jsonPatch
) {
}
