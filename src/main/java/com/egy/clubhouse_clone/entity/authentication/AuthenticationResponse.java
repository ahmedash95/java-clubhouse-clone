package com.egy.clubhouse_clone.entity.authentication;

public class AuthenticationResponse {
    private String token;

    public AuthenticationResponse(String t) {
        token = t;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
