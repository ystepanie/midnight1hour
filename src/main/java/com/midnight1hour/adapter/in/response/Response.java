package com.midnight1hour.adapter.in.response;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
