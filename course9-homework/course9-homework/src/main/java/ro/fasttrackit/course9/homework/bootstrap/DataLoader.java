package ro.fasttrackit.course9.homework.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ro.fasttrackit.course9.homework.model.domain.CleaningProcedure;
import ro.fasttrackit.course9.homework.model.domain.Tourist;
import ro.fasttrackit.course9.homework.model.entity.CleanupEntity;
import ro.fasttrackit.course9.homework.model.entity.ReviewEntity;
import ro.fasttrackit.course9.homework.repository.CleanupRepo;
import ro.fasttrackit.course9.homework.repository.ReviewRepo;
import ro.fasttrackit.course9.homework.repository.RoomRepo;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RoomRepo roomRepo;
    private final ReviewRepo reviewRepo;
    private final CleanupRepo cleanupRepo;

    @Override
    public void run(String... args) throws Exception {
//        roomRepo.saveAll(
//                List.of(RoomEntity.builder()
//                                .number("202")
//                                .hotelName("Hotel California")
//                                .floor(2)
//                                .facilities(RoomFacilities.builder()
//                                        .doubleBed(true)
//                                        .tv(true)
//                                        .build())
//                                .build(),
//                        RoomEntity.builder()
//                                .number("312")
//                                .hotelName("Hotel Padis")
//                                .floor(3)
//                                .facilities(RoomFacilities.builder()
//                                        .doubleBed(true)
//                                        .tv(false)
//                                        .build())
//                                .build(),
//                        RoomEntity.builder()
//                                .number("11A")
//                                .hotelName("Hotel Nevis")
//                                .floor(1)
//                                .facilities(RoomFacilities.builder()
//                                        .doubleBed(false)
//                                        .tv(true)
//                                        .build())
//                                .build(),
//                        RoomEntity.builder()
//                                .number("15C")
//                                .hotelName("Hotel Nevis")
//                                .floor(1)
//                                .facilities(RoomFacilities.builder()
//                                        .doubleBed(true)
//                                        .tv(false)
//                                        .build())
//                                .build(),
//                        RoomEntity.builder()
//                                .number("401")
//                                .hotelName("Hotel Iris")
//                                .floor(4)
//                                .facilities(RoomFacilities.builder()
//                                        .doubleBed(false)
//                                        .tv(false)
//                                        .build())
//                                .build()
//                ));
//
//        reviewRepo.saveAll(List.of(
//                ReviewEntity.builder()
//                        .roomId("643669657cc4b16e404c3dc9")
//                        .message("Good stuff")
//                        .rating(4)
//                        .tourist(Tourist.builder()
//                                .name("Gyuszi")
//                                .age(53)
//                                .build())
//                        .build(),
//                ReviewEntity.builder()
//                        .roomId("643669657cc4b16e404c3dca")
//                        .message("Meh")
//                        .rating(3)
//                        .tourist(Tourist.builder()
//                                .name("Pista")
//                                .age(60)
//                                .build())
//                        .build(),
//                ReviewEntity.builder()
//                        .roomId("643669657cc4b16e404c3dcb")
//                        .message("Excellent")
//                        .rating(5)
//                        .tourist(Tourist.builder()
//                                .name("Laci")
//                                .age(47)
//                                .build())
//                        .build(),
//                ReviewEntity.builder()
//                        .roomId("643669657cc4b16e404c3dcb")
//                        .message("Alright")
//                        .rating(4)
//                        .tourist(Tourist.builder()
//                                .name("Karcsi")
//                                .age(19)
//                                .build())
//                        .build()
//        ));
//
//        cleanupRepo.saveAll(List.of(
//                CleanupEntity.builder()
//                        .roomId("643669657cc4b16e404c3dcb")
//                        .date(LocalDateTime.now().minusHours(5))
//                        .procedures(List.of(CleaningProcedure.builder()
//                                        .name("Vacuuming")
//                                        .outcome(1)
//                                        .build(),
//                                CleaningProcedure.builder()
//                                        .name("Dusting")
//                                        .outcome(1)
//                                        .build()))
//                        .build(),
//                CleanupEntity.builder()
//                        .roomId("643669657cc4b16e404c3dca")
//                        .date(LocalDateTime.now().minusHours(8))
//                        .procedures(List.of(CleaningProcedure.builder()
//                                        .name("Vacuuming")
//                                        .outcome(1)
//                                        .build()))
//                        .build(),
//                CleanupEntity.builder()
//                        .roomId("643669657cc4b16e404c3dcb")
//                        .date(LocalDateTime.now().minusHours(1))
//                        .procedures(List.of(CleaningProcedure.builder()
//                                        .name("Vacuuming")
//                                        .outcome(2)
//                                        .build(),
//                                CleaningProcedure.builder()
//                                        .name("Toilet cleaning")
//                                        .outcome(1)
//                                        .build()))
//                        .build()
//        ));
    }
}
