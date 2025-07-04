package com.finscope.fraudscope.common.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@JsonPropertyOrder({ "status", "payload", "timestamp" })
@Schema(name = "StandartResponse" ,description =  "Standart response wrapper for all API endpoints")
public class StandartResponse<T> {
	
	@Schema(description = "HTTP status code")
    private int status;
	
	@Schema(description = "Actual data returned by the API such as login details or error information.")
    private T payload;
	
	@Schema(
		    description = "Timestamp of the response in 'YYYY-MM-DDThh:mm:ss' format",
		    example = "2025-07-04T22:12:00"
		)    private LocalDateTime timestamp = LocalDateTime.now();

    private StandartResponse(ResponseStatus status, T payload) {
        this.status = status.getCode();
        this.payload = payload;
    }

    //200
    public static <T> StandartResponse<T> ok(T payload) {
        return new StandartResponse<>(ResponseStatus.OK, payload);
    }

    //201
    public static <T> StandartResponse<T> created(T payload) {
        return new StandartResponse<>(ResponseStatus.CREATED, payload);
    }

    //204
    public static <T> StandartResponse<T> noContent() {
        return new StandartResponse<>(ResponseStatus.NO_CONTENT, null);
    }

    //400
    public static <T> StandartResponse<T> badRequest(T errorPayload) {
        return new StandartResponse<>(ResponseStatus.BAD_REQUEST, errorPayload);
    }

    //401
    public static <T> StandartResponse<T> unauthorized(T errorPayload) {
        return new StandartResponse<>(ResponseStatus.UNAUTHORIZED, errorPayload);
    }

    //403
    public static <T> StandartResponse<T> forbidden(T errorPayload) {
        return new StandartResponse<>(ResponseStatus.FORBIDDEN, errorPayload);
    }

    //404 
    public static <T> StandartResponse<T> notFound(T errorPayload) {
        return new StandartResponse<>(ResponseStatus.NOT_FOUND, errorPayload);
    }

    //409
    public static <T> StandartResponse<T> conflict(T errorPayload) {
        return new StandartResponse<>(ResponseStatus.CONFLICT, errorPayload);
    }

    //422
    public static <T> StandartResponse<T> unprocessableEntity(T errorPayload) {
        return new StandartResponse<>(ResponseStatus.UNPROCESSABLE_ENTITY, errorPayload);
    }

    //500
    public static <T> StandartResponse<T> error(T errorPayload) {
        return new StandartResponse<>(ResponseStatus.INTERNAL_SERVER_ERROR, errorPayload);
    }
}