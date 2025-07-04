package com.finscope.fraudscope.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.finscope.fraudscope.common.response.StandartResponse;

public abstract class BaseResponseController {

	//200
    public <T> ResponseEntity<StandartResponse<T>> ok(T payload) {
        return ResponseEntity.ok(StandartResponse.ok(payload));
    }

    //201
    public <T> ResponseEntity<StandartResponse<T>> created(T payload) {
        return ResponseEntity.status(HttpStatus.CREATED).body(StandartResponse.created(payload));
    }

    //204
    public <T> ResponseEntity<StandartResponse<T>> noContent() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(StandartResponse.noContent());
    }

    //400
    public <T> ResponseEntity<StandartResponse<T>> badRequest(T errorPayload) {
        return ResponseEntity.badRequest().body(StandartResponse.badRequest(errorPayload));
    }

    //401
    public <T> ResponseEntity<StandartResponse<T>> unauthorized(T errorPayload) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(StandartResponse.unauthorized(errorPayload));
    }

    //403
    public <T> ResponseEntity<StandartResponse<T>> forbidden(T errorPayload) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(StandartResponse.forbidden(errorPayload));
    }

    //404
    public <T> ResponseEntity<StandartResponse<T>> notFound(T errorPayload) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(StandartResponse.notFound(errorPayload));
    }

    //409
    public <T> ResponseEntity<StandartResponse<T>> conflict(T errorPayload) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(StandartResponse.conflict(errorPayload));
    }
    
    //422
    public <T> ResponseEntity<StandartResponse<T>> unprocessableEntity(T errorPayload){
    		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(StandartResponse.unprocessableEntity(errorPayload));
    }

    //500
    public <T> ResponseEntity<StandartResponse<T>> error(T errorPayload) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(StandartResponse.error(errorPayload));
    }
}
