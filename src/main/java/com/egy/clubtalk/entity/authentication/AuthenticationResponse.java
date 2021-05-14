package com.egy.clubtalk.entity.authentication;

import com.egy.clubtalk.entity.UserEntity;

public class AuthenticationResponse {
    private String token;

    private UserEntity user;

    public AuthenticationResponse(String t) {
        token = t;
    }

    public AuthenticationResponse(String jwt, UserEntity userEntity) {
        token = jwt;
        user = userEntity;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
