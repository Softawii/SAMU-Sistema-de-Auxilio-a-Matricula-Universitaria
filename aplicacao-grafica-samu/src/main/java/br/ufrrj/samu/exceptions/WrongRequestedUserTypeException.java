package br.ufrrj.samu.exceptions;

public class WrongRequestedUserTypeException extends Throwable {
    public WrongRequestedUserTypeException() {
    }

    public WrongRequestedUserTypeException(String message) {
        super(message);
    }

    public WrongRequestedUserTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
