package com.ggbackendassignment.eventmanagementsystem.exception;


public class InputPayloadException extends Exception {


    public InputPayloadException() {
        super();
    }

    public InputPayloadException(String message) {
        super(message);
    }

    public InputPayloadException(String message, Throwable cause) {
        super(message, cause);
    }

    public InputPayloadException(Throwable cause) {
        super(cause);
    }

}
