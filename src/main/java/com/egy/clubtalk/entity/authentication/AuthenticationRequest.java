package com.egy.clubtalk.entity.authentication;

import javax.validation.constraints.NotNull;

public class AuthenticationRequest {
    @NotNull(message = "Username or email is required.")
    private String email;
    @NotNull(message = "Password is required and can not be empty.")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
