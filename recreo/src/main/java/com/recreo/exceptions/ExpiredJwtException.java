package com.recreo.exceptions;

import com.recreo.enums.ResponseApiErrorCodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ExpiredJwtException extends RecreoApiException {
    public ExpiredJwtException() {
        super(HttpStatus.UNAUTHORIZED, "El token expiro.", ResponseApiErrorCodeEnum.INVALID_TOKEN);
    }
}
