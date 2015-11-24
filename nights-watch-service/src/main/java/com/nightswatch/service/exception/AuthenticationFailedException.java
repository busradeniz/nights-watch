package com.nightswatch.service.exception;

public class AuthenticationFailedException extends AbstractRuntimeException {

    private final String username;
    private final String password;

    public AuthenticationFailedException(String username, String password) {
        super("Authentication failed for username: " + username + " and password: " + password);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
