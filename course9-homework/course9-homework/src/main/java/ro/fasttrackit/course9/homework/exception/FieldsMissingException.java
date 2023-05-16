package ro.fasttrackit.course9.homework.exception;

import java.util.List;

import static java.util.Arrays.asList;

public class FieldsMissingException extends ValidationException {
    private final List<String> missingFields;

    public FieldsMissingException(String... missingFields) {
        this(null, asList(missingFields));
    }

    public FieldsMissingException(String resource, List<String> missingFields) {
        super("Could not find field(s)" +
                (resource == null ? "" : (" for " + resource)) +
                " : " + String.join(", ", missingFields));
        this.missingFields = missingFields;
    }

    public List<String> getMissingFields() {
        return missingFields;
    }
}
