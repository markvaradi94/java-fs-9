package ro.fasttrackit.course9.homework.service;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.fasttrackit.course9.homework.exception.ResourceNotFoundException;
import ro.fasttrackit.course9.homework.model.dto.ReviewDto;
import ro.fasttrackit.course9.homework.model.filters.ReviewFilters;
import ro.fasttrackit.course9.homework.model.mappers.ReviewMappers;
import ro.fasttrackit.course9.homework.model.request.review.AddReviewRequest;
import ro.fasttrackit.course9.homework.model.request.review.DeleteReviewRequest;
import ro.fasttrackit.course9.homework.model.request.review.GetReviewsRequest;
import ro.fasttrackit.course9.homework.model.request.review.UpdateReviewRequest;
import ro.fasttrackit.course9.homework.service.operation.review.AddReviewOperation;
import ro.fasttrackit.course9.homework.service.operation.review.DeleteReviewOperation;
import ro.fasttrackit.course9.homework.service.operation.review.GetReviewsOperation;
import ro.fasttrackit.course9.homework.service.operation.review.UpdateReviewOperation;

import static ro.fasttrackit.course9.homework.util.MongoQueryUtils.mapPagedContent;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewMappers reviewMappers = new ReviewMappers();
    private final GetReviewsOperation getReviewsOperation;
    private final AddReviewOperation addReviewOperation;
    private final DeleteReviewOperation deleteReviewOperation;
    private final UpdateReviewOperation updateReviewOperation;

    public Page<ReviewDto> findAll(ReviewFilters filters, Pageable pageable) {
        return mapPagedContent(reviewMappers.toApiMapper(),
                getReviewsOperation.execute(GetReviewsRequest.builder()
                        .filters(filters)
                        .pageable(pageable)
                        .build()),
                pageable);
    }

    public ReviewDto add(String roomId, ReviewDto newReview) {
        return reviewMappers.toApi(addReviewOperation.execute(AddReviewRequest.builder()
                .roomId(roomId)
                .review(newReview)
                .build()));
    }

    public ReviewDto delete(String roomId, String reviewId) {
        return deleteReviewOperation.execute(DeleteReviewRequest.builder()
                        .roomId(roomId)
                        .reviewId(reviewId)
                        .build())
                .map(reviewMappers::toApi)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find review with id " + reviewId + "for room with id " + roomId));
    }

    public ReviewDto patch(String roomId, String reviewId, JsonPatch patch) {
        return reviewMappers.toApi(updateReviewOperation.execute(UpdateReviewRequest.builder()
                .roomId(roomId)
                .reviewId(reviewId)
                .jsonPatch(patch)
                .build()));
    }
}
