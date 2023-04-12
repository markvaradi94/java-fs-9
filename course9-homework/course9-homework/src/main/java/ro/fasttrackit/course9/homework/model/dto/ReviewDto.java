package ro.fasttrackit.course9.homework.model.dto;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record ReviewDto(
        String id,
        String message,
        Integer rating,
        String touristName,
        Integer touristAge,
        String roomId
) {
}
