package ro.fasttrackit.course9.homework.model.dto;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record RoomDto(
        String id,
        String number,
        Integer floor,
        String hotelName,
        Boolean tv,
        Boolean doubleBed
) {
}
