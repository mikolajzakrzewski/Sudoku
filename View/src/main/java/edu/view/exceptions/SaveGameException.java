package edu.view.exceptions;

public class SaveGameException extends GameIoException {
    public SaveGameException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
