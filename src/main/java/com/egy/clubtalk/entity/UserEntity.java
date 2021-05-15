package com.egy.clubtalk.entity;

import com.egy.clubtalk.dao.UserDAO;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.*;

public class UserEntity extends ProfileEntity {
    private Long id;

    @JsonProperty(value = "username")
    @NotNull(message = "Username must be entered")
    @Size(min = 3, max = 15, message = "Username should be at least 3 characters.")
    private String username;

    @JsonProperty("email")
    @Email(message = "Email must be valid.")
    private String email;

    @JsonProperty(value = "password")
    @Size(min = 6, max = 15, message = "Password should be at least 6 characters.")
    private String password;

    private Long followersCount;
    private Long followingCount;

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

    public void setFollowersCount(Long followersCount) {
        this.followersCount = followersCount;
    }

    public Long getFollowersCount() {
        return followersCount;
    }

    public Long getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(Long followingCount) {
        this.followingCount = followingCount;
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
        self.setFollowersCount(user.getFollowersCount());
        self.setFollowingCount(user.getFollowingsCount());


        return self;
    }
}
