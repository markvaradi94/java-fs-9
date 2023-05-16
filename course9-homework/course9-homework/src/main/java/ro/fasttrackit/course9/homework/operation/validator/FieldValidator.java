package ro.fasttrackit.course9.homework.operation.validator;

import ro.fasttrackit.course9.homework.exception.FieldsMissingException;
import ro.fasttrackit.course9.homework.exception.ValidationException;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.Optional.*;
import static java.util.stream.Collectors.toList;

public class FieldValidator {

    private final String resource;

    public FieldValidator() {
        this(null);
    }

    public FieldValidator(String resource) {
        this.resource = resource;
    }

    public Optional<ValidationException> checkFields(FieldChecker... getters) {
        List<String> missingFields =
                ofNullable(getters)
                        .map(nGetters -> stream(getters)
                                .map(this::returnMissingField)
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .collect(toList()))
                        .orElse(emptyList());
        return missingFields.isEmpty()
                ? empty()
                : of(new FieldsMissingException(resource, missingFields));
    }

    private Optional<String> returnMissingField(FieldChecker checker) {
        return ofNullable(checker.getValueExtractor().get())
                .map(value -> Optional.<String>empty())
                .orElseGet(() -> of(checker.getFieldName()));
    }

    public static class FieldChecker {
        private final Supplier<Object> valueExtractor;
        private final String fieldName;

        FieldChecker(Supplier<Object> valueExtractor, String fieldName) {
            this.valueExtractor = valueExtractor;
            this.fieldName = fieldName;
        }

        public static FieldChecker field(String fieldName, Supplier<Object> valueExtractor) {
            return new FieldChecker(valueExtractor, fieldName);
        }

        Supplier<Object> getValueExtractor() {
            return valueExtractor;
        }

        String getFieldName() {
            return fieldName;
        }
    }
}
