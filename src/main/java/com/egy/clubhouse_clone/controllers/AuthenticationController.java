package com.egy.clubhouse_clone.controllers;

import com.egy.clubhouse_clone.dao.UserDAO;
import com.egy.clubhouse_clone.entity.UserEntity;
import com.egy.clubhouse_clone.entity.authentication.AuthenticationRequest;
import com.egy.clubhouse_clone.entity.authentication.AuthenticationResponse;
import com.egy.clubhouse_clone.exceptions.ApiException;
import com.egy.clubhouse_clone.exceptions.authentication.InvalidCredentialsException;
import com.egy.clubhouse_clone.services.UserAuthenticationService;
import com.egy.clubhouse_clone.services.UserService;
import com.egy.clubhouse_clone.util.JWTUtil;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
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
