package ro.fasttrackit.course9.homework.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.course9.homework.model.dto.CleanupDto;
import ro.fasttrackit.course9.homework.model.dto.ReviewDto;
import ro.fasttrackit.course9.homework.model.dto.RoomDto;
import ro.fasttrackit.course9.homework.model.filters.RoomFilters;
import ro.fasttrackit.course9.homework.model.pagination.CollectionResponse;
import ro.fasttrackit.course9.homework.service.RoomService;

import static ro.fasttrackit.course9.homework.util.MongoQueryUtils.toCollectionResponse;

@RestController
@RequestMapping("rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService service;

    @GetMapping
    CollectionResponse<RoomDto> getAll(RoomFilters filters, Pageable pageable) {
        return toCollectionResponse(service.findAll(filters, pageable));
    }

    @GetMapping("{roomId}")
    RoomDto getRoom(@PathVariable String roomId) {
        return service.findById(roomId);
    }

    @GetMapping("{roomId}/cleanups")
    CollectionResponse<CleanupDto> getCleanupsForRoom(@PathVariable String roomId) {
        return toCollectionResponse(service.findCleanups(roomId));
    }

    @GetMapping("{roomId}/reviews")
    CollectionResponse<ReviewDto> getReviewsForRoom(@PathVariable String roomId) {
        return toCollectionResponse(service.findReviews(roomId));
    }

    @PostMapping
    RoomDto addRoom(@RequestBody RoomDto newRoom) {
        return service.addRoom(newRoom);
    }

    @DeleteMapping("{roomId}")
    RoomDto deleteRoom(@PathVariable String roomId) {
        return service.delete(roomId);
    }

    @PatchMapping("{roomId}")
    RoomDto patchRoom(@RequestBody JsonPatch jsonPatch, @PathVariable String roomId) {
        return service.patch(roomId, jsonPatch);
    }
}
