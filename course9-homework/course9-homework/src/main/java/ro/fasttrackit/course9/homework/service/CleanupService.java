package ro.fasttrackit.course9.homework.service;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.fasttrackit.course9.homework.exception.ResourceNotFoundException;
import ro.fasttrackit.course9.homework.model.dto.CleanupDto;
import ro.fasttrackit.course9.homework.model.filters.CleanupFilters;
import ro.fasttrackit.course9.homework.model.mappers.CleanupMappers;
import ro.fasttrackit.course9.homework.model.request.cleanup.AddCleanupRequest;
import ro.fasttrackit.course9.homework.model.request.cleanup.DeleteCleanupRequest;
import ro.fasttrackit.course9.homework.model.request.cleanup.GetCleanupsRequest;
import ro.fasttrackit.course9.homework.model.request.cleanup.UpdateCleanupRequest;
import ro.fasttrackit.course9.homework.service.operation.cleanup.AddCleanupOperation;
import ro.fasttrackit.course9.homework.service.operation.cleanup.DeleteCleanupOperation;
import ro.fasttrackit.course9.homework.service.operation.cleanup.GetCleanupsOperation;
import ro.fasttrackit.course9.homework.service.operation.cleanup.UpdateCleanupOperation;

import static ro.fasttrackit.course9.homework.util.MongoQueryUtils.mapPagedContent;

@Service
@RequiredArgsConstructor
public class CleanupService {
    private final CleanupMappers cleanupMappers = new CleanupMappers();
    private final GetCleanupsOperation getCleanupsOperation;
    private final AddCleanupOperation addCleanupOperation;
    private final DeleteCleanupOperation deleteCleanupOperation;
    private final UpdateCleanupOperation updateCleanupOperation;

    public Page<CleanupDto> findAll(CleanupFilters filters, Pageable pageable) {
        return mapPagedContent(cleanupMappers.toApiMapper(),
                getCleanupsOperation.execute(GetCleanupsRequest.builder()
                        .filters(filters)
                        .pageable(pageable)
                        .build()),
                pageable);
    }

    public CleanupDto add(String roomId, CleanupDto newCleanup) {
        return cleanupMappers.toApi(addCleanupOperation.execute(AddCleanupRequest.builder()
                .roomId(roomId)
                .cleanup(newCleanup)
                .build()));
    }


    public CleanupDto delete(String roomId, String cleanupId) {
        return deleteCleanupOperation.execute(DeleteCleanupRequest.builder()
                        .cleanupId(cleanupId)
                        .roomId(roomId)
                        .build())
                .map(cleanupMappers::toApi)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find cleanup with id " + cleanupId + "for room with id " + roomId));
    }

    public CleanupDto patch(String roomId, String cleanupId, JsonPatch patch) {
        return cleanupMappers.toApi(updateCleanupOperation.execute(UpdateCleanupRequest.builder()
                .roomId(roomId)
                .cleanupId(cleanupId)
                .jsonPatch(patch)
                .build()));
    }
}
