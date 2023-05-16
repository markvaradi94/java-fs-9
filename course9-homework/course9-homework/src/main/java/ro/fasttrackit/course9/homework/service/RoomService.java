package ro.fasttrackit.course9.homework.service;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.fasttrackit.course9.homework.exception.ResourceNotFoundException;
import ro.fasttrackit.course9.homework.model.dto.CleanupDto;
import ro.fasttrackit.course9.homework.model.dto.ReviewDto;
import ro.fasttrackit.course9.homework.model.dto.RoomDto;
import ro.fasttrackit.course9.homework.model.filters.RoomFilters;
import ro.fasttrackit.course9.homework.model.mappers.CleanupMappers;
import ro.fasttrackit.course9.homework.model.mappers.ReviewMappers;
import ro.fasttrackit.course9.homework.model.mappers.RoomMappers;
import ro.fasttrackit.course9.homework.model.request.room.GetRoomsRequest;
import ro.fasttrackit.course9.homework.model.request.room.UpdateRoomRequest;
import ro.fasttrackit.course9.homework.service.operation.room.*;

import static org.springframework.data.domain.Pageable.ofSize;
import static ro.fasttrackit.course9.homework.util.MongoQueryUtils.mapPagedContent;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomMappers roomMappers = new RoomMappers();
    private final CleanupMappers cleanupMappers = new CleanupMappers();
    private final ReviewMappers reviewMappers = new ReviewMappers();
    private final GetRoomOperation getRoomOperation;
    private final GetRoomsOperation getRoomsOperation;
    private final AddRoomOperation addRoomOperation;
    private final DeleteRoomOperation deleteRoomOperation;
    private final UpdateRoomOperation updateRoomOperation;
    private final FindCleanupsForRoomOperation findCleanupsForRoomOperation;
    private final FindReviewsForRoomOperation findReviewsForRoomOperation;

    public Page<RoomDto> findAll(RoomFilters filters, Pageable pageable) {
        return mapPagedContent(roomMappers.toApiMapper(),
                getRoomsOperation.execute(GetRoomsRequest.builder()
                        .filters(filters)
                        .pageable(pageable)
                        .build()),
                pageable);
    }

    public RoomDto findById(String roomId) {
        return getRoomOperation.execute(roomId)
                .map(roomMappers::toApi)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find room with id " + roomId));
    }

    public RoomDto addRoom(RoomDto newRoom) {
        return roomMappers.toApi(addRoomOperation.execute(newRoom));
    }

    public RoomDto delete(String roomId) {
        return deleteRoomOperation.execute(roomId)
                .map(roomMappers::toApi)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find room with id " + roomId));
    }

    public RoomDto patch(String roomId, JsonPatch patch) {
        return roomMappers.toApi(updateRoomOperation.execute(UpdateRoomRequest.builder()
                .roomId(roomId)
                .jsonPatch(patch)
                .build()));
    }

    public Page<CleanupDto> findCleanups(String roomId) {
        return mapPagedContent(cleanupMappers.toApiMapper(),
                findCleanupsForRoomOperation.execute(roomId),
                ofSize(10));
    }

    public Page<ReviewDto> findReviews(String roomId) {
        return mapPagedContent(reviewMappers.toApiMapper(),
                findReviewsForRoomOperation.execute(roomId),
                ofSize(10));
    }
}
