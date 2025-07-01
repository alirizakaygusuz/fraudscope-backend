package com.finscope.fraudscope.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.finscope.fraudscope.common.response.ApiResponse;

public abstract class BaseResponseController {

	//200
    public <T> ResponseEntity<ApiResponse<T>> ok(T payload) {
        return ResponseEntity.ok(ApiResponse.ok(payload));
    }

    //201
    public <T> ResponseEntity<ApiResponse<T>> created(T payload) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(payload));
    }

    //204
    public <T> ResponseEntity<ApiResponse<T>> noContent() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.noContent());
    }

    //400
    public <T> ResponseEntity<ApiResponse<T>> badRequest(T errorPayload) {
        return ResponseEntity.badRequest().body(ApiResponse.badRequest(errorPayload));
    }

    //401
    public <T> ResponseEntity<ApiResponse<T>> unauthorized(T errorPayload) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.unauthorized(errorPayload));
    }

    //403
    public <T> ResponseEntity<ApiResponse<T>> forbidden(T errorPayload) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.forbidden(errorPayload));
    }

    //404
    public <T> ResponseEntity<ApiResponse<T>> notFound(T errorPayload) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.notFound(errorPayload));
    }

    //409
    public <T> ResponseEntity<ApiResponse<T>> conflict(T errorPayload) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.conflict(errorPayload));
    }
    
    //422
    public <T> ResponseEntity<ApiResponse<T>> unprocessableEntity(T errorPayload){
    		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ApiResponse.unprocessableEntity(errorPayload));
    }

    //500
    public <T> ResponseEntity<ApiResponse<T>> error(T errorPayload) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(errorPayload));
    }
}
