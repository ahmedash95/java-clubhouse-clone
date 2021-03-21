package com.egy.clubtalk.controllers;

import com.egy.clubtalk.entity.UserEntity;
import com.egy.clubtalk.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<Object> show(@PathVariable("userId") Long userId) {
        UserEntity userEntity = userService.getById(userId);

        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<Object> findUser(@RequestParam("query") String query) {
        List<UserEntity> users = userService.search(query);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}/following")
    public ResponseEntity<Object> getFollowing(@PathVariable("id") Long userId) {
        List<UserEntity> users = userService.getFolloweeByUserId(userId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}/followers")
    public ResponseEntity<Object> getFollowers(@PathVariable("id") Long userId) {
        List<UserEntity> users = userService.getFollowersByUserId(userId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
