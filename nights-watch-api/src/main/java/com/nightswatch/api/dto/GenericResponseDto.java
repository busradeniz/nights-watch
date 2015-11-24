package com.nightswatch.api.dto;

public abstract class GenericResponseDto {

    private String message;
    private ResponseType response;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseType getResponse() {
        return response;
    }

    public void setResponse(ResponseType response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "message='" + message + '\'' +
                ", response=" + response +
                '}';
    }
}
