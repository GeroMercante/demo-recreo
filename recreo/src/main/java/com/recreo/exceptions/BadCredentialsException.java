package com.recreo.exceptions;


import com.recreo.enums.ResponseApiErrorCodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadCredentialsException extends RecreoApiException {
    public BadCredentialsException(String message) {
        super(HttpStatus.BAD_REQUEST, message, ResponseApiErrorCodeEnum.BUSINESS_ERROR);
    }
}
