package com.egy.clubtalk.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URLEncoder;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ProfileEntity {
    @JsonProperty("first_name")
    @NotBlank(message = "First name is required.")
    @Size(min = 3, max = 15)
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "Last name is required.")
    @Size(min = 3, max = 15)
    private String lastName;

    @JsonProperty("bio")
    @Size(min = 3, max = 255)
    private String bio;

    @JsonProperty("picture")
    private String picture;

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
        if(picture == null || "".equals(picture)) {
            return "https://ui-avatars.com/api/?name=" + URLEncoder.encode(getFullName());
        }

        return picture;
    }

    public String getFullName() {
        return String.format("%s %s", getFirstName(), getLastName());
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
