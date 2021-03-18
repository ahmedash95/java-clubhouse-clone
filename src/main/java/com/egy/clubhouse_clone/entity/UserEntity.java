package com.egy.clubhouse_clone.entity;

import com.egy.clubhouse_clone.dao.UserDAO;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.*;

public class UserEntity {
    private Long id;

    @JsonProperty("email")
    @Email(message = "Email must be valid.")
    private String email;

    @JsonProperty("first_name")
    @NotBlank(message = "First name is required.")
    @Size(min = 3, max = 15)
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "Last name is required.")
    @Size(min = 3, max = 15)
    private String lastName;

    @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
    @Min(value = 6, message = "Password should be at least 6 characters.")
    private String password;

    @JsonProperty("bio")
    @Max(255)
    private String bio;

    @JsonProperty("picture")
    private String picture;

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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserEntity fromUserDao(UserDAO user) {
        UserEntity self = new UserEntity();
        self.setFirstName(user.getFirstName());
        self.setLastName(user.getLastName());
        self.setEmail(user.getEmail());
        self.setId(user.getID());
        self.setBio(user.getBio());
        self.setPicture(user.getPicture());

        return self;
    }
}
