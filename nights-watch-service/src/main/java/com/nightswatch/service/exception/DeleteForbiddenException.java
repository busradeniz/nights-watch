package com.nightswatch.service.exception;

public class DeleteForbiddenException extends  AbstractRuntimeException{
    public DeleteForbiddenException() {
        super();
    }

    public DeleteForbiddenException(String message) {
        super(message);
    }

    public DeleteForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}
