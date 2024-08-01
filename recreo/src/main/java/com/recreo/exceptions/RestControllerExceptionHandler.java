package com.recreo.exceptions;

import com.recreo.enums.ResponseApiErrorCodeEnum;
import com.recreo.http.response.ApiResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseError> handleException(Exception e) {
        if (e instanceof RecreoApiException apiException) {
            ApiResponseError apiResponse = new ApiResponseError();
            apiResponse.setCode(apiException.getCode().getCode());
            apiResponse.setStatus(apiException.getStatus());
            apiResponse.setMessage(apiException.getMessage());
            return new ResponseEntity<>(apiResponse, apiException.getStatus());
        } else {
            ApiResponseError apiResponse = new ApiResponseError();
            apiResponse.setCode(ResponseApiErrorCodeEnum.UNHANDLE.getCode());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            apiResponse.setMessage(e.getMessage());
            return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
        }
    }
}
