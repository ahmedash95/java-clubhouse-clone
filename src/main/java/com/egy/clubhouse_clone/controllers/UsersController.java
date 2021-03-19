package com.egy.clubhouse_clone.controllers;

import com.egy.clubhouse_clone.dao.UserDAO;
import com.egy.clubhouse_clone.entity.ProfileEntity;
import com.egy.clubhouse_clone.entity.UserEntity;
import com.egy.clubhouse_clone.services.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @GetMapping("/find")
    public ResponseEntity<Object> findUser(@RequestParam("query") String query) {
        List<UserEntity> users = userService.search(query);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
