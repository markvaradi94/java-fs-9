package ro.fasttrackit.mongodemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.fasttrackit.mongodemo.entity.Room;
import ro.fasttrackit.mongodemo.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService service;

    @GetMapping
    List<Room> getAll(@RequestParam(required = false) String number) {
        return service.getAll(number);
    }

    @GetMapping("dao")
    Page<Room> findRooms() {
        return service.findRooms();
    }
}
