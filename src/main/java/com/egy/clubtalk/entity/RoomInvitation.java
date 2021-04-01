package com.egy.clubtalk.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoomInvitation {
    @JsonProperty("user_id")
    public Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
