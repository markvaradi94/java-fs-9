package ro.fasttrackit.course9.homework.model.mappers;

import java.util.Collection;
import java.util.List;

public interface Mappers<S, T> {
    default S toApi(T source) {
        return toApiMapper().of(source);
    }

    default T toDb(S source) {
        return toDbMapper().of(source);
    }

    default List<S> toApi(Collection<T> source) {
        return toApiMapper().of(source);
    }

    default List<T> toDb(Collection<S> source) {
        return toDbMapper().of(source);
    }

    ModelMapper<T, S> toApiMapper();

    ModelMapper<S, T> toDbMapper();
}
