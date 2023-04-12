package ro.fasttrackit.course9.homework.model.dto;

import lombok.Builder;
import lombok.With;
import ro.fasttrackit.course9.homework.model.domain.CleaningProcedure;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@With
@Builder
public record CleanupDto(
        String id,
        LocalDateTime date,
        String roomId,
        List<CleaningProcedure> procedures
) {
    public CleanupDto {
        date = ofNullable(date)
                .orElseGet(LocalDateTime::now);
    }
}
