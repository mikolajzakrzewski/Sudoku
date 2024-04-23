package edu.view.exceptions;

public class GameStartException extends GameIoException {
    public GameStartException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
