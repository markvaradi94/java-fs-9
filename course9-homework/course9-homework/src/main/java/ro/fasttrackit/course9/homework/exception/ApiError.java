package ro.fasttrackit.course9.homework.exception;

import lombok.Builder;

import static java.util.Optional.ofNullable;

@Builder
public record ApiError(String errorCode, String message) {

    public static final String DEFAULT_CODE = "DEFAULT-CODE";

    public ApiError {
        errorCode = ofNullable(errorCode)
                .orElse(DEFAULT_CODE);
    }

    public ApiError(String message) {
        this(DEFAULT_CODE, message);
    }
}
