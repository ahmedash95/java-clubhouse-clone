package com.egy.clubtalk.helper;

import com.egy.clubtalk.entity.UserEntity;

public class UserHelper {
    public static UserEntity getUserEntity() {
        UserEntity user = new UserEntity();
        user.setFirstName("Ahmed");
        user.setLastName("Ashraf");
        user.setEmail("ahmed@email.com");
        user.setPassword("123456");
        return user;
    }
}
