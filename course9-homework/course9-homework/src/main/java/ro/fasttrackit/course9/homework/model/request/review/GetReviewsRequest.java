package ro.fasttrackit.course9.homework.model.request.review;

import lombok.Builder;
import org.springframework.data.domain.Pageable;
import ro.fasttrackit.course9.homework.model.filters.ReviewFilters;

import java.util.Optional;

import static org.springframework.data.domain.Pageable.ofSize;

@Builder
public record GetReviewsRequest(
        ReviewFilters filters,
        Pageable pageable
) {
    public GetReviewsRequest {
        pageable = Optional.ofNullable(pageable)
                .orElseGet(() -> ofSize(10));
    }
}
