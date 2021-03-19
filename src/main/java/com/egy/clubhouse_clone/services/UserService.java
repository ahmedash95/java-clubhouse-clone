package com.egy.clubhouse_clone.services;

import com.egy.clubhouse_clone.dao.UserDAO;
import com.egy.clubhouse_clone.entity.ProfileEntity;
import com.egy.clubhouse_clone.entity.UserEntity;
import com.egy.clubhouse_clone.exceptions.ApiException;
import com.egy.clubhouse_clone.exceptions.user.UserCanNotFollowHimselfException;
import com.egy.clubhouse_clone.exceptions.user.EmailAlreadyTakenException;
import com.egy.clubhouse_clone.exceptions.user.UserNotFoundException;
import com.egy.clubhouse_clone.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserEntity createUser(UserEntity user) throws ApiException {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyTakenException();
        }

        UserDAO dao = new UserDAO(user);

        dao.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(dao);

        UserEntity userEntity = new UserEntity().fromUserDao(dao);

        return userEntity;
    }

    public UserEntity getById(Long userId) {
        Optional<UserDAO> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        return new UserEntity().fromUserDao(user.get());
    }

    public UserEntity getByEmail(String email) {
        Optional<UserDAO> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        return new UserEntity().fromUserDao(user.get());
    }

    public UserEntity updateProfile(String userEmail, ProfileEntity profileEntity) {
        Optional<UserDAO> user = userRepository.findByEmail(userEmail);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        UserDAO dao = user.get();

        dao.fillFromProfile(profileEntity);

        userRepository.save(dao);

        return new UserEntity().fromUserDao(dao);
    }

    public List<UserEntity> search(String query) {
        List<UserDAO> users = userRepository.search(query);

        return users.stream().map((dao) -> new UserEntity().fromUserDao(dao)).collect(Collectors.toList());
    }

    public void follow(UserDetails user, Long userId) {
        Optional<UserDAO> dao = userRepository.findByEmail(user.getUsername());
        if (dao.isEmpty()) {
            throw new UserNotFoundException();
        }

        Optional<UserDAO> target = userRepository.findById(userId);
        if (target.isEmpty()) {
            throw new UserNotFoundException();
        }

        if(target.get().getID() == dao.get().getID()) {
            throw new UserCanNotFollowHimselfException();
        }

        if(dao.get().getFollowing().stream().filter((f) -> f.getID().equals(userId)).toArray().length > 0) {
            return;
        }

        dao.get().getFollowing().add(target.get());
        userRepository.save(dao.get());
    }
}
