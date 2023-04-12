package ro.fasttrackit.course9.homework.model.domain;

import lombok.Builder;

@Builder
public record RoomFacilities(
        boolean tv,
        boolean doubleBed
) {
}
