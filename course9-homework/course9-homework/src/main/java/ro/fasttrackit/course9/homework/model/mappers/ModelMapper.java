package ro.fasttrackit.course9.homework.model.mappers;

import java.util.List;

import static java.util.Optional.ofNullable;
import static java.util.stream.StreamSupport.stream;

public interface ModelMapper<S, T> {
    default List<T> of(Iterable<S> source) {
        return ofNullable(source)
                .map(nonNullSource ->
                        stream(nonNullSource.spliterator(), false)
                                .map(this::of)
                                .toList())
                .orElse(null);
    }

    default T of(S source) {
        return ofNullable(source)
                .map(this::nullSafeMap)
                .orElse(null);
    }

    T nullSafeMap(S source);
}
