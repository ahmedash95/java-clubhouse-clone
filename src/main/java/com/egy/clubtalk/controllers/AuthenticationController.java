package com.egy.clubtalk.controllers;

import com.egy.clubtalk.entity.UserEntity;
import com.egy.clubtalk.entity.authentication.AuthenticationRequest;
import com.egy.clubtalk.entity.authentication.AuthenticationResponse;
import com.egy.clubtalk.exceptions.ApiException;
import com.egy.clubtalk.exceptions.authentication.InvalidCredentialsException;
import com.egy.clubtalk.services.UserAuthenticationService;
import com.egy.clubtalk.services.UserService;
import com.egy.clubtalk.util.JWTUtil;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserAuthenticationService userDetailsService;

    @Autowired
    UserService userService;

    @PostMapping("/login/")
    public ResponseEntity<Object> login(@RequestBody AuthenticationRequest request) throws ApiException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException();
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String jwt = new JWTUtil().generateToken(userDetails);

        AuthenticationResponse response = new AuthenticationResponse(jwt);

        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @PostMapping("/register/")
    public ResponseEntity<Object> register(@Valid @RequestBody UserEntity user) {
        UserEntity userEntity = userService.createUser(user);

        return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
    }
}
