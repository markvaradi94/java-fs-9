package ro.fasttrackit.course9.homework.model.mappers;

import ro.fasttrackit.course9.homework.model.dto.CleanupDto;
import ro.fasttrackit.course9.homework.model.entity.CleanupEntity;

public class CleanupMappers implements Mappers<CleanupDto, CleanupEntity> {
    private final DbCleanupMapper toDbMapper = new DbCleanupMapper();
    private final CleanupMapper toApiMapper = new CleanupMapper();


    @Override
    public ModelMapper<CleanupEntity, CleanupDto> toApiMapper() {
        return toApiMapper;
    }

    @Override
    public ModelMapper<CleanupDto, CleanupEntity> toDbMapper() {
        return toDbMapper;
    }
}

class DbCleanupMapper implements ModelMapper<CleanupDto, CleanupEntity> {
    @Override
    public CleanupEntity nullSafeMap(CleanupDto source) {
        return CleanupEntity.builder()
                .id(source.id())
                .date(source.date())
                .procedures(source.procedures())
                .roomId(source.roomId())
                .build();
    }
}

class CleanupMapper implements ModelMapper<CleanupEntity, CleanupDto> {
    @Override
    public CleanupDto nullSafeMap(CleanupEntity source) {
        return CleanupDto.builder()
                .id(source.getId())
                .date(source.getDate())
                .procedures(source.getProcedures())
                .roomId(source.getRoomId())
                .build();
    }
}
