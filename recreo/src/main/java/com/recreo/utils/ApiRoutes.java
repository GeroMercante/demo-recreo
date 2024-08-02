package com.recreo.utils;

public final class ApiRoutes {

    private ApiRoutes() {}

    public static final String[] PUBLIC_ROUTES = {
            "/actuator/**",
            "/api/v1/bo/auth/**",
            "/api/v1/app/auth/**"
    };

    public static final String BO_USERS_BASE = "/api/v1/bo/users/**";
    public static final String BO_AUTH_CHANGE_PASSWORD = "/api/v1/bo/auth/change/password";
}
