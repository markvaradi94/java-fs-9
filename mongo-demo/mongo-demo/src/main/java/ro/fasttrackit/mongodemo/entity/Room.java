package ro.fasttrackit.mongodemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "rooms")
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    private String id;

    private String roomNumber;

    public Room(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
