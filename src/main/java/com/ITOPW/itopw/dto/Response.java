package com.ITOPW.itopw.dto;

public class Response {
    private int code;
    private String httpStatus;
    private String message;
    private Object data;

    public Response(int code, String httpStatus, String message, Object data) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
        this.data = data;
    }

    // Getters and Setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
