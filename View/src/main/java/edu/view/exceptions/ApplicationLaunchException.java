package edu.view.exceptions;

public class ApplicationLaunchException extends GameIoException {
    public ApplicationLaunchException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
