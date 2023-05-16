package ro.fasttrackit.course9.homework.model.request.cleanup;

import lombok.Builder;

@Builder
public record DeleteCleanupRequest(
        String roomId,
        String cleanupId
) {
}
