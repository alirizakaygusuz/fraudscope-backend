package com.finscope.fraudscope.common.response;

public enum ResponseStatus	{
    OK(200),
    CREATED(201),
    NO_CONTENT(204),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    CONFLICT(409),
    UNPROCESSABLE_ENTITY(422),
    INTERNAL_SERVER_ERROR(500);

    private final int code;

    ResponseStatus	(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}