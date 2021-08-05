package br.ufrrj.samu.exceptions;

public class SubjectNotFoundException extends Exception {

    public SubjectNotFoundException() {
    }

    public SubjectNotFoundException(String message) {
        super(message);
    }

    public SubjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
