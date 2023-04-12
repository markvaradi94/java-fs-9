package ro.fasttrackit.course9.homework.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.course9.homework.model.dto.ReviewDto;
import ro.fasttrackit.course9.homework.service.ReviewService;

@RestController
@RequestMapping("rooms/{roomId}/reviews")
@RequiredArgsConstructor
public class RoomReviewController {
    private final ReviewService service;

    @PostMapping
    ReviewDto addReview(@PathVariable String roomId, @RequestBody ReviewDto newReview) {
        return service.add(roomId, newReview);
    }

    @DeleteMapping("{reviewId}")
    ReviewDto deleteReview(@PathVariable String roomId, @PathVariable String reviewId) {
        return service.delete(roomId, reviewId);
    }

    @PatchMapping("{reviewId}")
    ReviewDto patchReview(@RequestBody JsonPatch jsonPatch, @PathVariable String roomId, @PathVariable String reviewId) {
        return service.patch(roomId, reviewId, jsonPatch);
    }
}
