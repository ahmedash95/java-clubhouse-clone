package com.egy.clubtalk.controllers;

import com.egy.clubtalk.entity.ProfileEntity;
import com.egy.clubtalk.entity.UserEntity;
import com.egy.clubtalk.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public ResponseEntity<Object> profile(@AuthenticationPrincipal UserDetails user) {
        UserEntity userEntity = userService.getByEmail(user.getUsername());

        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Object> updateProfile(@AuthenticationPrincipal UserDetails user, @RequestBody ProfileEntity profileEntity) {
        UserEntity userEntity = userService.updateProfile(user.getUsername(), profileEntity);

        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }
}
