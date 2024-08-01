package com.recreo.enums;

public enum ResponseApiErrorCodeEnum {
    UNHANDLE(-1),
    BUSINESS_ERROR(1),
    UNAUTHORIZED(2),
    INVALID_TOKEN(3);

    private final int code;
    ResponseApiErrorCodeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
