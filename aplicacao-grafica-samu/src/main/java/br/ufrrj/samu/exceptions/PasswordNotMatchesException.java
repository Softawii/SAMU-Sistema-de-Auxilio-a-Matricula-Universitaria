package br.ufrrj.samu.exceptions;

public class PasswordNotMatchesException extends Exception {
    public PasswordNotMatchesException() {
    }

    public PasswordNotMatchesException(String message) {
        super(message);
    }

    public PasswordNotMatchesException(String message, Throwable cause) {
        super(message, cause);
    }
}
