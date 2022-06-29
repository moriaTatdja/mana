package com.hervmo.manager.utils;

import java.util.HashMap;

public class Constants {
    public static final String HASH_CART = "HASH_CART";
    public static final String ALGORITHM_SECRET = "secret";
    public static final String JWT_CLAIMS = "roles";
    public static final String AUTHORIZATION_BEARER_START = "Bearer ";
    public static final String WAREHOUSE_ROUTE_NAME = "warehouse";
    public static final String ARTICLE_ROUTE_NAME = "article";
    public static final String ORDER_ROUTE_NAME = "order";
    public static final String USERS_ROUTE_NAME = "users";
    public static final String CUSTOMER_ROUTE_NAME = "customer";
    public static final String ROLE_ROUTE_NAME = "role";
    public static final String LOGIN_ROUTE_NAME = "login";
    public static final String REGISTRATION_ROUTE_NAME = "registration";
    public static final String VERIFY_REGISTRATION_ROUTE_NAME = "verifyRegistration";
    public static final String TOKEN_REQUEST_PARAMETER = "token";

    public static final int ACCESS_TOKEN_DURATION = (30 * 60 * 1000);
    public static final int REFRESH_TOKEN_DURATION = 2;

    public static HashMap<String, String> GENERATED_JWT(String accessToken, String refreshToken, Long unixTime, Long refreshUnixTime) {
        HashMap<String, String> jwt = new HashMap<>();
        jwt.put("accessToken", accessToken);
        jwt.put("refreshToken", refreshToken);
        jwt.put("expiredAt", unixTime.toString());
        jwt.put("refreshExpiredAt", refreshUnixTime.toString());

        return jwt;
    }
}
