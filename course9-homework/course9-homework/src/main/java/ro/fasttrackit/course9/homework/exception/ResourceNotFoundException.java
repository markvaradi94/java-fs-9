package ro.fasttrackit.course9.homework.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class ResourceNotFoundException extends RuntimeException {
    public static final String DEFAULT_RESOURCE_ERROR_CODE = "RESOURCE_ERROR";
    private final String errorCode;
    private final int code;

    public ResourceNotFoundException(String message, String errorCode, int code) {
        super(message);
        this.errorCode = errorCode;
        this.code = code;
    }

    public ResourceNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.code = 400;
    }

    public ResourceNotFoundException(String message, int code) {
        super(message);
        this.code = code;
        this.errorCode = DEFAULT_RESOURCE_ERROR_CODE;
    }

    public ResourceNotFoundException(String message) {
        this(message, DEFAULT_RESOURCE_ERROR_CODE, 400);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
