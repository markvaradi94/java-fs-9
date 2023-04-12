package ro.fasttrackit.course9.homework.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.fasttrackit.course9.homework.exception.ResourceNotFoundException;
import ro.fasttrackit.course9.homework.model.dto.CleanupDto;
import ro.fasttrackit.course9.homework.model.dto.ReviewDto;
import ro.fasttrackit.course9.homework.model.dto.RoomDto;
import ro.fasttrackit.course9.homework.model.entity.RoomEntity;
import ro.fasttrackit.course9.homework.model.filters.CleanupFilters;
import ro.fasttrackit.course9.homework.model.filters.ReviewFilters;
import ro.fasttrackit.course9.homework.model.filters.RoomFilters;
import ro.fasttrackit.course9.homework.model.mappers.ModelMapper;
import ro.fasttrackit.course9.homework.model.mappers.RoomMappers;
import ro.fasttrackit.course9.homework.repository.RoomRepository;
import ro.fasttrackit.course9.homework.validator.RoomValidator;

import static java.util.Collections.singletonList;
import static org.springframework.data.domain.Pageable.ofSize;
import static ro.fasttrackit.course9.homework.util.MongoQueryUtils.mapPagedContent;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository repository;
    private final RoomValidator validator;
    private final ObjectMapper objectMapper;
    private final RoomMappers roomMappers = new RoomMappers();
    private final ModelMapper<RoomEntity, RoomDto> apiMapper = roomMappers.toApiMapper();
    private final ModelMapper<RoomDto, RoomEntity> dbMapper = roomMappers.toDbMapper();
    private final CleanupService cleanupService;
    private final ReviewService reviewService;

    public Page<RoomDto> findAll(RoomFilters filters, Pageable pageable) {
        return mapPagedContent(apiMapper, repository.findAll(filters, pageable), pageable);
    }

    public RoomDto findById(String roomId) {
        return getOrThrow(roomId);
    }

    public RoomDto addRoom(RoomDto newRoom) {
        validator.validateNewThrow(dbMapper.of(newRoom));
        return apiMapper.of(repository.addRoom(dbMapper.of(newRoom)));
    }

    public RoomDto delete(String roomId) {
        validator.validateExistsOrThrow(roomId);
        RoomDto roomToDelete = getOrThrow(roomId);
        return repository.deleteById(roomToDelete.id())
                .map(apiMapper::of)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find room with id " + roomId));
    }

    private RoomDto getOrThrow(String roomId) {
        return repository.findById(roomId)
                .map(apiMapper::of)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find room with id " + roomId));
    }

    @SneakyThrows
    public RoomDto patch(String roomId, JsonPatch patch) {
        validator.validateExistsOrThrow(roomId);
        RoomEntity roomToPatch = repository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not patch room with id " + roomId + ". Could not find room."));
        JsonNode patchedRoomJson = patch.apply(objectMapper.valueToTree(roomToPatch));
        RoomEntity patchedRoom = objectMapper.treeToValue(patchedRoomJson, RoomEntity.class);
        validator.validateReplaceThrow(roomId, patchedRoom);
        return apiMapper.of(repository.addRoom(patchedRoom));
    }

    public Page<CleanupDto> findCleanups(String roomId) {
        return cleanupService.findAll(
                CleanupFilters.builder()
                        .roomId(singletonList(roomId))
                        .build(),
                ofSize(10));
    }

    public Page<ReviewDto> findReviews(String roomId) {
        return reviewService.findAll(ReviewFilters.builder()
                        .roomId(singletonList(roomId))
                        .build(),
                ofSize(10));
    }
}
