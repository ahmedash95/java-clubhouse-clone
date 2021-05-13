package com.egy.clubtalk.services;

import com.egy.clubtalk.dao.UserDAO;
import com.egy.clubtalk.repository.UserRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserDAO> user = userRepository.findByEmailOrUserName(email);
        UserDetails userDetails = new User(user.get().getEmail(), user.get().getPassword(), new ArrayList<>());

        return userDetails;
    }
}
