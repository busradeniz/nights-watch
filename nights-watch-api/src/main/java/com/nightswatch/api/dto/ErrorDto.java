package com.nightswatch.api.dto;

public class ErrorDto {

    /**
     * Http status code
     */
    private int code;

    /**
     * Cause for this error
     */
    private String cause;

    /**
     * Message for this error
     */
    private String message;

    /**
     * Rest service path that results error
     */
    private String path;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
