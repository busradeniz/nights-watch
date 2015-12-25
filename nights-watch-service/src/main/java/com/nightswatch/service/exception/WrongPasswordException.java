package com.nightswatch.service.exception;

public class WrongPasswordException extends AbstractRuntimeException {
    public WrongPasswordException(String oldPassword) {
        super("Password " + oldPassword + " is wrong. Please try again.");
    }
}
