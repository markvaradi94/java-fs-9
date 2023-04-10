package ro.fasttrackit.mongodemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ro.fasttrackit.mongodemo.entity.Room;
import ro.fasttrackit.mongodemo.repository.RoomDao;
import ro.fasttrackit.mongodemo.repository.RoomRepository;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository repository;
    private final RoomDao roomDao;

    public List<Room> getAll(String number) {
        return ofNullable(number)
                .map(repository::findByRoomNumberIgnoreCase)
                .orElseGet(repository::findAll);
    }

    public Page<Room> findRooms() {
        return roomDao.findRooms();
    }
}
