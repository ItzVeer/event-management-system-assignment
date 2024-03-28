package com.ggbackendassignment.eventmanagementsystem.exception;

public class ApiCallFailedException extends RuntimeException {

    public ApiCallFailedException() {
        super();
    }

    public ApiCallFailedException(String message) {
        super(message);
    }

    public ApiCallFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiCallFailedException(Throwable cause) {
        super(cause);
    }
}

