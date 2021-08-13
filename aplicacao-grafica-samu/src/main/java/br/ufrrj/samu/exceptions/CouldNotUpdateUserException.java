package br.ufrrj.samu.exceptions;

public class CouldNotUpdateUserException extends Exception {
    public CouldNotUpdateUserException() {
    }

    public CouldNotUpdateUserException(String message) {
        super(message);
    }

    public CouldNotUpdateUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouldNotUpdateUserException(Throwable cause) {
        super(cause);
    }
}
