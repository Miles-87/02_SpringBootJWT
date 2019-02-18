package com.app.security;

public interface SecurityConfig {
    String SECRET = "SecretKeyToGenJwts";
    long EXPIRATION_TIME = 864_000_000; // 10 dni w ms
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
