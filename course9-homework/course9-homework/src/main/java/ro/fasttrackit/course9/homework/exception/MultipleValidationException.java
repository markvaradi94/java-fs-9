package ro.fasttrackit.course9.homework.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.Collections.unmodifiableCollection;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class MultipleValidationException extends RuntimeException {
    private final Collection<ValidationException> exceptions = new ArrayList<>();

    public MultipleValidationException(Collection<? extends ValidationException> exceptions) {
        super(collectMessage(exceptions));
        this.exceptions.addAll(exceptions);
    }

    private static String collectMessage(Collection<? extends ValidationException> exceptions) {
        return ofNullable(exceptions)
                .map(excs -> excs.stream()
                        .map(ValidationException::getMessage)
                        .collect(joining(",")))
                .orElse("");
    }

    public Collection<ValidationException> getExceptions() {
        return unmodifiableCollection(exceptions);
    }
}
