package edu.view.exceptions;

import java.io.IOException;

public class GameIoException extends IOException {
    public GameIoException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
