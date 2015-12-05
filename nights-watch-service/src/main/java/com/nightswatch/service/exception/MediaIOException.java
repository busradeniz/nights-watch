package com.nightswatch.service.exception;

public class MediaIOException extends AbstractRuntimeException {

    public MediaIOException(String fileName, Throwable throwable) {
        super("Unable to create file with name '" + fileName + "'", throwable);
    }
}
