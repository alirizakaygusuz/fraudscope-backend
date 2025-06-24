package com.finscope.fraudscope.common.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Getter
@Setter
@JsonPropertyOrder({ "status", "payload", "timestamp" })
public class ApiResponse<T> {
    private int status;
    private T payload;
    private LocalDateTime timestamp = LocalDateTime.now();

    private ApiResponse(ResponseStatus status, T payload) {
        this.status = status.getCode();
        this.payload = payload;
    }

    //200
    public static <T> ApiResponse<T> ok(T payload) {
        return new ApiResponse<>(ResponseStatus.OK, payload);
    }

    //201
    public static <T> ApiResponse<T> created(T payload) {
        return new ApiResponse<>(ResponseStatus.CREATED, payload);
    }

    //204
    public static <T> ApiResponse<T> noContent() {
        return new ApiResponse<>(ResponseStatus.NO_CONTENT, null);
    }

    //400
    public static <T> ApiResponse<T> badRequest(T errorPayload) {
        return new ApiResponse<>(ResponseStatus.BAD_REQUEST, errorPayload);
    }

    //401
    public static <T> ApiResponse<T> unauthorized(T errorPayload) {
        return new ApiResponse<>(ResponseStatus.UNAUTHORIZED, errorPayload);
    }

    //403
    public static <T> ApiResponse<T> forbidden(T errorPayload) {
        return new ApiResponse<>(ResponseStatus.FORBIDDEN, errorPayload);
    }

    //404 
    public static <T> ApiResponse<T> notFound(T errorPayload) {
        return new ApiResponse<>(ResponseStatus.NOT_FOUND, errorPayload);
    }

    //409
    public static <T> ApiResponse<T> conflict(T errorPayload) {
        return new ApiResponse<>(ResponseStatus.CONFLICT, errorPayload);
    }

    //422
    public static <T> ApiResponse<T> unprocessableEntity(T errorPayload) {
        return new ApiResponse<>(ResponseStatus.UNPROCESSABLE_ENTITY, errorPayload);
    }

    //500
    public static <T> ApiResponse<T> error(T errorPayload) {
        return new ApiResponse<>(ResponseStatus.INTERNAL_SERVER_ERROR, errorPayload);
    }
}