package com.egy.clubhouse_clone.services;

import com.egy.clubhouse_clone.entity.UserEntity;
import com.egy.clubhouse_clone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserEntity createUser(UserEntity user) throws Exception {
        if(userRepository.findByEmail(user.getEmail()) != null) {
            throw new Exception("Email already in use");
        }

        userRepository.save(user);

        return user;
    }
}
