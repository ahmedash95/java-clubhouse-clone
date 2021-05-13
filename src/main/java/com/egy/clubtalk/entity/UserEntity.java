package com.egy.clubtalk.entity;

import com.egy.clubtalk.dao.UserDAO;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.*;

public class UserEntity extends ProfileEntity {
    private Long id;

    private String username;

    @JsonProperty("email")
    @Email(message = "Email must be valid.")
    private String email;

    @JsonProperty(value = "password")
    @Min(value = 6, message = "Password should be at least 6 characters.")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public UserEntity fromUserDao(UserDAO user) {
        UserEntity self = new UserEntity();
        self.setUsername(user.getUsername());
        self.setFirstName(user.getFirstName());
        self.setLastName(user.getLastName());
        self.setEmail(user.getEmail());
        self.setId(user.getID());
        self.setBio(user.getBio());
        self.setPicture(user.getPicture());

        return self;
    }
}
