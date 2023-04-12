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
import ro.fasttrackit.course9.homework.model.entity.CleanupEntity;
import ro.fasttrackit.course9.homework.model.filters.CleanupFilters;
import ro.fasttrackit.course9.homework.model.mappers.CleanupMappers;
import ro.fasttrackit.course9.homework.model.mappers.ModelMapper;
import ro.fasttrackit.course9.homework.repository.CleanupRepository;
import ro.fasttrackit.course9.homework.validator.CleanupValidator;

import static ro.fasttrackit.course9.homework.util.MongoQueryUtils.mapPagedContent;

@Service
@RequiredArgsConstructor
public class CleanupService {
    private final CleanupRepository repository;
    private final CleanupValidator validator;
    private final ObjectMapper objectMapper;
    private final CleanupMappers cleanupMappers = new CleanupMappers();
    private final ModelMapper<CleanupEntity, CleanupDto> apiMapper = cleanupMappers.toApiMapper();
    private final ModelMapper<CleanupDto, CleanupEntity> dbMapper = cleanupMappers.toDbMapper();

    public Page<CleanupDto> findAll(CleanupFilters filters, Pageable pageable) {
        return mapPagedContent(apiMapper, repository.findAll(filters, pageable), pageable);
    }

    public CleanupDto add(String roomId, CleanupDto newCleanup) {
        validator.validateRoomExistsOrThrow(roomId);
        CleanupDto cleanupWithRoomId = newCleanup.withRoomId(roomId);
        validator.validateNewThrow(dbMapper.of(cleanupWithRoomId));
        return apiMapper.of(repository.addCleanup(dbMapper.of(cleanupWithRoomId)));
    }

    public CleanupDto delete(String roomId, String cleanupId) {
        validator.validateRoomExistsOrThrow(roomId);
        validator.validateExistsOrThrow(cleanupId);
        CleanupDto cleanupToDelete = getOrThrow(cleanupId, roomId);
        return repository.deleteById(cleanupToDelete.id(), roomId)
                .map(apiMapper::of)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find cleanup with id " + cleanupId + "for room with id " + roomId));
    }

    private CleanupDto getOrThrow(String cleanupId, String roomId) {
        return repository.findByCleanupIdAndRoomId(cleanupId, roomId)
                .map(apiMapper::of)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find cleanup with id " + cleanupId + "for room with id " + roomId));
    }

    @SneakyThrows
    public CleanupDto patch(String roomId, String cleanupId, JsonPatch patch) {
        validator.validateRoomExistsOrThrow(roomId);
        validator.validateExistsOrThrow(cleanupId);
        CleanupEntity cleanupToPatch = repository.findByCleanupIdAndRoomId(cleanupId, roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find cleanup with id " + cleanupId + "for room with id " + roomId));
        JsonNode patchedCleanupJson = patch.apply(objectMapper.valueToTree(cleanupToPatch));
        CleanupEntity patchedCleanup = objectMapper.treeToValue(patchedCleanupJson, CleanupEntity.class);
        validator.validateReplaceThrow(cleanupId, patchedCleanup);
        return apiMapper.of(repository.addCleanup(patchedCleanup));
    }
}
