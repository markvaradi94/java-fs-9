package ro.fasttrackit.course9.homework.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class ValidationException extends RuntimeException {
    public static final String DEFAULT_VALIDATION_ERROR_CODE = "VALIDATION_ERROR";
    private final String errorCode;
    private final int code;

    public ValidationException(String message, String errorCode, int code) {
        super(message);
        this.errorCode = errorCode;
        this.code = code;
    }

    public ValidationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.code = 400;
    }

    public ValidationException(String message, int code) {
        super(message);
        this.code = code;
        this.errorCode = DEFAULT_VALIDATION_ERROR_CODE;
    }

    public ValidationException(String message) {
        this(message, DEFAULT_VALIDATION_ERROR_CODE, 400);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
