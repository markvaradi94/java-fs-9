package ro.fasttrackit.course9.homework.operation.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.fasttrackit.course9.homework.exception.ValidationException;

import java.util.Collection;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.joining;

public interface OperationValidator<T> {
    default Collection<ValidationException> validate(T request) {
        Validator validator = new Validator();
        return validate(request, validator);
    }

    default Collection<ValidationException> validate(T request, Validator validator) {
        Logger log = getLogger();
        validator.check(nonNull(request))
                .ifFalse("Invalid NULL request")
                .ifTrue(() -> doValidate(validator, request));

        Collection<ValidationException> exceptions = validator.getExceptions();
        if (!exceptions.isEmpty()) {
            log.info("Validator failed : " + exceptions.stream().map(ValidationException::getMessage).collect(joining(",")));
        }
        return exceptions;
    }

    Validator doValidate(Validator validator, T request);

    default Logger getLogger() {
        return LoggerFactory.getLogger(getClass());
    }
}
