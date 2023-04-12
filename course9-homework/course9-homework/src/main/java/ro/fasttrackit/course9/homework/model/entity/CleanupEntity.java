package ro.fasttrackit.course9.homework.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ro.fasttrackit.course9.homework.model.domain.CleaningProcedure;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document("cleanups")
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class CleanupEntity {
    @Id
    private String id;

    private LocalDateTime date;
    private List<CleaningProcedure> procedures;
    private String roomId;
}
