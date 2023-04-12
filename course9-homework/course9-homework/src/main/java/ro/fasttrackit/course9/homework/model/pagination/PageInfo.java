package ro.fasttrackit.course9.homework.model.pagination;

import lombok.Builder;

@Builder
public record PageInfo(
        long totalSize,
        int totalPages,
        int currentPage,
        int pageSize
) {
}
