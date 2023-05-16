package ro.fasttrackit.course9.homework.model.request.cleanup;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.Builder;

@Builder
public record UpdateCleanupRequest(
        String roomId,
        String cleanupId,
        JsonPatch jsonPatch
) {
}
