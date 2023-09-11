package com.fincheck.fincheckapijava.exceptions;

public class ExceptionResponse {
    private String message;
    private String error;
    private int statusCode;
    
    public ExceptionResponse(String message, String error, int statusCode) {
        this.message = message;
        this.error = error;
        this.statusCode = statusCode;
    }

    //#region Getters and Setters
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    public int getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    //#endregion
}
