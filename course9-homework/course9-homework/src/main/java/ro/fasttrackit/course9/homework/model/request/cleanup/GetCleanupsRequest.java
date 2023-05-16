package ro.fasttrackit.course9.homework.model.request.cleanup;

import lombok.Builder;
import org.springframework.data.domain.Pageable;
import ro.fasttrackit.course9.homework.model.filters.CleanupFilters;

import static java.util.Optional.ofNullable;
import static org.springframework.data.domain.Pageable.ofSize;

@Builder
public record GetCleanupsRequest(
        CleanupFilters filters,
        Pageable pageable
) {
    public GetCleanupsRequest {
        pageable = ofNullable(pageable)
                .orElseGet(() -> ofSize(10));
    }
}
