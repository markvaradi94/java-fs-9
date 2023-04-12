package ro.fasttrackit.course9.homework.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.course9.homework.model.dto.CleanupDto;
import ro.fasttrackit.course9.homework.service.CleanupService;

@RestController
@RequestMapping("rooms/{roomId}/cleanups")
@RequiredArgsConstructor
public class RoomCleanupController {
    private final CleanupService service;

    @PostMapping
    CleanupDto addCleanup(@PathVariable String roomId, @RequestBody CleanupDto newCleanup) {
        return service.add(roomId, newCleanup);
    }

    @DeleteMapping("{cleanupId}")
    CleanupDto deleteCleanup(@PathVariable String roomId, @PathVariable String cleanupId) {
        return service.delete(roomId, cleanupId);
    }

    @PatchMapping("{cleanupId}")
    CleanupDto patchCleanup(@RequestBody JsonPatch jsonPatch, @PathVariable String roomId, @PathVariable String cleanupId) {
        return service.patch(roomId, cleanupId, jsonPatch);
    }
}
