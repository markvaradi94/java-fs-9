//package ro.fasttrackit.mongodemo.bootstrap;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import ro.fasttrackit.mongodemo.entity.Room;
//import ro.fasttrackit.mongodemo.repository.RoomRepository;
//
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class DataLoader implements CommandLineRunner {
//    private final RoomRepository repository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        repository.saveAll(List.of(
//                Room.builder()
//                        .roomNumber("123A")
//                        .build(),
//                Room.builder()
//                        .roomNumber("202")
//                        .build(),
//                Room.builder()
//                        .roomNumber("404C")
//                        .build(),
//                Room.builder()
//                        .roomNumber("10B")
//                        .build()
//        ));
//    }
//}
