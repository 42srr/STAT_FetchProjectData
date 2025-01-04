package com.example.STAT_FetchProjectData.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {
    private HttpStatus httpStatus;
    private int code;
    private String msg;
    private T data;

    private ApiResponse(HttpStatus httpStatus, int code, String msg, T data) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok (T data) {
        return new ApiResponse<>(HttpStatus.OK, HttpStatus.OK.value(), "Success", data);
    }

    public static <T> ApiResponse<T> badRequest (Exception e, String msg) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), msg, null);
    }
}
