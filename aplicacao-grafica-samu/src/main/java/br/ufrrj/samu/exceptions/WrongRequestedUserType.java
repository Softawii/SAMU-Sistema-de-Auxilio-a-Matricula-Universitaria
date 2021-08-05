package br.ufrrj.samu.exceptions;

public class WrongRequestedUserType extends Throwable {
    public WrongRequestedUserType() {
    }

    public WrongRequestedUserType(String message) {
        super(message);
    }

    public WrongRequestedUserType(String message, Throwable cause) {
        super(message, cause);
    }
}
