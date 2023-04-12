package ro.fasttrackit.course9.homework.model.filters;

import lombok.Builder;

import java.util.List;

@Builder
public record ReviewFilters(
        List<String> id,
        List<Integer> rating,
        List<String> roomId,
        List<String> touristName
) {
}
