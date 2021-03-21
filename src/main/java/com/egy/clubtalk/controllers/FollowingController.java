package com.egy.clubtalk.controllers;

import com.egy.clubtalk.services.FollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/follow")
public class FollowingController {

    @Autowired
    FollowingService followingService;

    @PostMapping("/{id}")
    public ResponseEntity<Object> followUser(@AuthenticationPrincipal UserDetails auth, @PathVariable("id") Long userId) {
        followingService.follow(auth.getUsername(), userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
