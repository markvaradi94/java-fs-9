package ro.fasttrackit.course9.homework.model.pagination;

import lombok.Builder;

import java.util.List;

@Builder
public record CollectionResponse<T>(
        List<T> items,
        PageInfo pageInfo
) {
}
