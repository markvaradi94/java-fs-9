package ro.fasttrackit.course9.homework.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ro.fasttrackit.course9.homework.model.domain.Tourist;

@Data
@Builder
@Document(collection = "reviews")
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class ReviewEntity {
    @Id
    private String id;

    private String message;
    private Integer rating;
    private Tourist tourist;
    private String roomId;
}
