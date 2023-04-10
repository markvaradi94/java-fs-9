package ro.fasttrackit.dockerdemo.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ro.fasttrackit.dockerdemo.entity.Room;
import ro.fasttrackit.dockerdemo.repository.RoomRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RoomRepository repository;

    @Override
    public void run(String... args) throws Exception {
        repository.saveAll(List.of(
                Room.builder()
                        .roomNumber("110B")
                        .build(),
                Room.builder()
                        .roomNumber("225")
                        .build(),
                Room.builder()
                        .roomNumber("304C")
                        .build(),
                Room.builder()
                        .roomNumber("101")
                        .build()
        ));
    }
}
