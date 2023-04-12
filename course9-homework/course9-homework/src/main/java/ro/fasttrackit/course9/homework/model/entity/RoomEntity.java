package ro.fasttrackit.course9.homework.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ro.fasttrackit.course9.homework.model.domain.RoomFacilities;

@Data
@Builder
@Document(collection = "rooms")
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class RoomEntity {
    @Id
    private String id;

    private String number;
    private int floor;
    private String hotelName;
    private RoomFacilities facilities;
}
