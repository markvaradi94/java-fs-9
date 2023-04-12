package ro.fasttrackit.course9.homework.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import ro.fasttrackit.course9.homework.model.mappers.ModelMapper;
import ro.fasttrackit.course9.homework.model.pagination.CollectionResponse;
import ro.fasttrackit.course9.homework.model.pagination.PageInfo;

import java.util.List;

@UtilityClass
public class MongoQueryUtils {

    public static <T> void inOrIs(Criteria criteria, List<T> tokens, String key) {
        if (tokens.size() == 1) {
            criteria.and(key).is(tokens.get(0));
        } else {
            criteria.and(key).in(tokens);
        }
    }

    public static <T> CollectionResponse<T> toCollectionResponse(Page<T> elements) {
        return CollectionResponse.<T>builder()
                .items(elements.getContent())
                .pageInfo(PageInfo.builder()
                        .totalSize(elements.getTotalElements())
                        .totalPages(elements.getTotalPages())
                        .currentPage(elements.getPageable().getPageNumber())
                        .pageSize(elements.getPageable().getPageSize())
                        .build())
                .build();
    }

    public static <D, T> Page<T> mapPagedContent(ModelMapper<D, T> mapper, Page<D> page, Pageable pageable) {
        return new PageImpl<>(mapper.of(page.getContent()), pageable, page.getTotalElements());
    }
}
