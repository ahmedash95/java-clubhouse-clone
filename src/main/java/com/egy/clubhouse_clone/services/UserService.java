package com.egy.clubhouse_clone.services;

import com.egy.clubhouse_clone.dao.UserDAO;
import com.egy.clubhouse_clone.entity.UserEntity;
import com.egy.clubhouse_clone.exceptions.ApiException;
import com.egy.clubhouse_clone.exceptions.user.EmailAlreadyTakenException;
import com.egy.clubhouse_clone.exceptions.user.UserNotFoundException;
import com.egy.clubhouse_clone.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserEntity createUser(UserDAO user) throws ApiException {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyTakenException();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        UserEntity userEntity = new UserEntity().fromUserDao(user);

        return userEntity;
    }

    public UserEntity getById(Long userId) {
        Optional<UserDAO> user = userRepository.findById(userId);

        if(user.isEmpty()) {
            throw new UserNotFoundException();
        }

        return new UserEntity().fromUserDao(user.get());
    }
}
