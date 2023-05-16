package ro.fasttrackit.course9.homework.model.request.room;

import lombok.Builder;
import org.springframework.data.domain.Pageable;
import ro.fasttrackit.course9.homework.model.filters.RoomFilters;

import static java.util.Optional.ofNullable;
import static org.springframework.data.domain.Pageable.ofSize;

@Builder
public record GetRoomsRequest(
        RoomFilters filters,
        Pageable pageable
) {
    public GetRoomsRequest {
        pageable = ofNullable(pageable)
                .orElseGet(() -> ofSize(10));
    }
}
