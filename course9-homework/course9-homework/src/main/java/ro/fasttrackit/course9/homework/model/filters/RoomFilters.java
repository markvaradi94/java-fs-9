package ro.fasttrackit.course9.homework.model.filters;

import lombok.Builder;

import java.util.List;

@Builder
public record RoomFilters(
        List<String> id,
        List<String> number,
        List<Integer> floor,
        Boolean tv,
        Boolean doubleBed
) {
}
