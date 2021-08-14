package br.ufrrj.samu.exceptions;

public class LectureNotFoundException extends Throwable {
    public LectureNotFoundException() {
    }

    public LectureNotFoundException(String message) {
        super(message);
    }

    public LectureNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
