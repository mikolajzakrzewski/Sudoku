package edu.view.exceptions;

public class GameReloadException extends GameIoException {
    public GameReloadException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
