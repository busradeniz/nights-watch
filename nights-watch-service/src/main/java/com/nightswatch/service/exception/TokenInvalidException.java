package com.nightswatch.service.exception;

public class TokenInvalidException extends AbstractRuntimeException {

    private final String token;

    public TokenInvalidException(String token) {
        super("Token " + token + " is not valid");
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
