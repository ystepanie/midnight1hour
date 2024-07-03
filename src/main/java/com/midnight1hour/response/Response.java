package com.midnight1hour.response;

// 컨트롤러 Response 객체
public class Response {
    int status;
    String message;
    Object data;

    public Response() {}

    public Response(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Response(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
