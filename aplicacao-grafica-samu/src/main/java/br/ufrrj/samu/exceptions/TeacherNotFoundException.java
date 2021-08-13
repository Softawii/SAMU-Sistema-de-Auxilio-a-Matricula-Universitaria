package br.ufrrj.samu.exceptions;

public class TeacherNotFoundException extends Exception {

    public TeacherNotFoundException() {
    }

    public TeacherNotFoundException(String message) {
        super(message);
    }

    public TeacherNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TeacherNotFoundException(Throwable cause) {
        super(cause);
    }
}
