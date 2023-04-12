package ro.fasttrackit.course9.homework.model.mappers;

import ro.fasttrackit.course9.homework.model.domain.RoomFacilities;
import ro.fasttrackit.course9.homework.model.dto.RoomDto;
import ro.fasttrackit.course9.homework.model.entity.RoomEntity;

import static java.util.Optional.ofNullable;

public class RoomMappers implements Mappers<RoomDto, RoomEntity> {
    private final DbRoomMapper toDbMapper = new DbRoomMapper();
    private final RoomMapper toApiMapper = new RoomMapper();

    @Override
    public ModelMapper<RoomEntity, RoomDto> toApiMapper() {
        return toApiMapper;
    }

    @Override
    public ModelMapper<RoomDto, RoomEntity> toDbMapper() {
        return toDbMapper;
    }
}

class DbRoomMapper implements ModelMapper<RoomDto, RoomEntity> {

    @Override
    public RoomEntity nullSafeMap(RoomDto source) {
        return RoomEntity.builder()
                .id(source.id())
                .hotelName(source.hotelName())
                .floor(source.floor())
                .number(source.number())
                .facilities(RoomFacilities.builder()
                        .doubleBed(source.doubleBed())
                        .tv(source.tv())
                        .build())
                .build();
    }
}

class RoomMapper implements ModelMapper<RoomEntity, RoomDto> {

    @Override
    public RoomDto nullSafeMap(RoomEntity source) {
        return RoomDto.builder()
                .id(source.getId())
                .number(source.getNumber())
                .floor(source.getFloor())
                .hotelName(source.getHotelName())
                .doubleBed(ofNullable(source.getFacilities())
                        .map(RoomFacilities::doubleBed)
                        .orElse(null))
                .tv(ofNullable(source.getFacilities())
                        .map(RoomFacilities::tv)
                        .orElse(null))
                .build();
    }
}
