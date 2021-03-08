package com.egy.clubhouse_clone.controllers;

import com.egy.clubhouse_clone.dao.UserDAO;
import com.egy.clubhouse_clone.entity.UserEntity;
import com.egy.clubhouse_clone.services.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController {

    @Autowired
    UserService userService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<Object> show(@PathVariable("userId") Long userId) {
        UserEntity userEntity = userService.getById(userId);

        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    @PostMapping("/users/")
    public ResponseEntity<Object> create(@RequestBody Map<String, Object> request) {
        UserDAO user = new UserDAO();
        user.setEmail((String) request.get("email"));
        user.setFirstName((String) request.get("first_name"));
        user.setLastName((String) request.get("last_name"));
        user.setPassword((String) request.get("password"));

        UserEntity userEntity = userService.createUser(user);

        return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
    }
}
