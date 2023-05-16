package ro.fasttrackit.course9.homework.model.request.cleanup;

import lombok.Builder;
import ro.fasttrackit.course9.homework.model.dto.CleanupDto;

@Builder
public record AddCleanupRequest(
        String roomId,
        CleanupDto cleanup
) {
}
