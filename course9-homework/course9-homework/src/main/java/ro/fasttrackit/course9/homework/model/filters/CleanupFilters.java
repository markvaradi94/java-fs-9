package ro.fasttrackit.course9.homework.model.filters;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CleanupFilters(
        List<String> id,
        LocalDateTime date,
        List<String> roomId,
        List<String> procedureName,
        List<Integer> procedureOutcome
) {
}
