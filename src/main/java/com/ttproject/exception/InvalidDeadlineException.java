package com.ttproject.exception;

public class InvalidDeadlineException extends RuntimeException {

    public InvalidDeadlineException() {
        super("Deadline cannot be earlier than the start date");
    }
}
