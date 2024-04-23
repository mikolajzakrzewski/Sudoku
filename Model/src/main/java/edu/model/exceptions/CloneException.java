package edu.model.exceptions;

public class CloneException extends RuntimeException {
    public CloneException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
