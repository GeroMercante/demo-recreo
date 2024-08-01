package com.recreo.exceptions;

import com.recreo.enums.ResponseApiErrorCodeEnum;
import org.springframework.http.HttpStatus;

public class RecreoApiException extends Exception {
    private static final long serialVersionUID = -6593330219878485669L;

    private final HttpStatus status;
    private final ResponseApiErrorCodeEnum code;

    public RecreoApiException(HttpStatus status, String message, ResponseApiErrorCodeEnum errorCode) {
        super(message);
        this.status = status;
        this.code = errorCode;
    }

    public RecreoApiException(String message) {
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.code = ResponseApiErrorCodeEnum.BUSINESS_ERROR;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ResponseApiErrorCodeEnum getCode() { return code; }

}
