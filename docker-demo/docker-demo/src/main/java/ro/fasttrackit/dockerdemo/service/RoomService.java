package ro.fasttrackit.dockerdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.fasttrackit.dockerdemo.entity.Room;
import ro.fasttrackit.dockerdemo.repository.RoomRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository repo;

    public List<Room> getAll() {
        return repo.findAll();
    }
}
