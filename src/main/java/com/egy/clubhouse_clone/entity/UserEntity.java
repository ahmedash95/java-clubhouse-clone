package com.egy.clubhouse_clone.entity;

import com.egy.clubhouse_clone.dao.UserDAO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserEntity {
    private Long id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserEntity fromUserDao(UserDAO user) {
        UserEntity self = new UserEntity();
        self.setFirstName(user.getFirstName());
        self.setLastName(user.getLastName());
        self.setEmail(user.getEmail());
        self.setId(user.getID());

        return self;
    }
}
