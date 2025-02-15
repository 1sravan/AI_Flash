package org.springai.flash.exception;

public class ApiException extends RuntimeException {
    private int httpStatusCode;
    private String errorCode;
    private String message;

    public ApiException(int httpStatusCode, String message, String errorCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.errorCode = errorCode;
    }

    public ApiException(int httpStatusCode, String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.errorCode = errorCode;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}