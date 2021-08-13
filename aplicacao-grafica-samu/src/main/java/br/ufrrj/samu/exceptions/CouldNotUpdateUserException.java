package br.ufrrj.samu.exceptions;

public class CouldNotUpdateUserException extends Exception {

    private long userId;

    public CouldNotUpdateUserException(long userId) {
        this.userId = userId;
    }

    public CouldNotUpdateUserException(String message, long userId) {
        super(message);
        this.userId = userId;
    }

    public CouldNotUpdateUserException(String message, Throwable cause, long userId) {
        super(message, cause);
        this.userId = userId;
    }

    public CouldNotUpdateUserException(Throwable cause, long userId) {
        super(cause);
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }
}
