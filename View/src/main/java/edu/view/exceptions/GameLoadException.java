package edu.view.exceptions;

public class GameLoadException extends GameIoException {
    public GameLoadException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
