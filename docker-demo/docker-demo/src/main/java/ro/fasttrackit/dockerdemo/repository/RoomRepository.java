package ro.fasttrackit.dockerdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.fasttrackit.dockerdemo.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
