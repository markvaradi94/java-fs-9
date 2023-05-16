package ro.fasttrackit.course9.homework.model.request.room;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.Builder;

@Builder
public record UpdateRoomRequest(
        String roomId,
        JsonPatch jsonPatch
) {
}
