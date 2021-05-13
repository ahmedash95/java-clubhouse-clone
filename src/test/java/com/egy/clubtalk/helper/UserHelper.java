package com.egy.clubtalk.helper;

import com.egy.clubtalk.entity.UserEntity;
import java.util.Random;

public class UserHelper {
    public static UserEntity getUserEntity() {
        UserEntity user = new UserEntity();
        user.setUsername(new Random().nextInt() + "user");
        user.setFirstName("Ahmed");
        user.setLastName("Ashraf");
        user.setEmail("ahmed@email.com");
        user.setPassword("123456");
        return user;
    }

    public static UserEntity getUserEntity(String email) {
        UserEntity u = getUserEntity();
        u.setEmail(email);
        return u;
    }

    public static UserEntity getUserEntity(String email, String username) {
        UserEntity u = getUserEntity();
        u.setEmail(email);
        u.setUsername(username);
        return u;
    }
}
