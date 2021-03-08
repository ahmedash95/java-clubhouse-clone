package com.egy.clubhouse_clone.controllers;

import com.egy.clubhouse_clone.entity.UserEntity;
import com.egy.clubhouse_clone.services.UserService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    @Autowired
    UserService userService;

    @PostMapping("/users/")
    public ResponseEntity<Object> create(@RequestBody Map<String, Object> request) {
        UserEntity user = new UserEntity();
        user.setEmail((String) request.get("email"));
        user.setFirstName((String) request.get("first_name"));
        user.setLastName((String) request.get("last_name"));
        user.setPassword((String) request.get("password"));

        userService.createUser(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
