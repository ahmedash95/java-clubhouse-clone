package com.egy.clubtalk.services;

import com.egy.clubtalk.dao.UserDAO;
import com.egy.clubtalk.entity.ProfileEntity;
import com.egy.clubtalk.entity.UserEntity;
import com.egy.clubtalk.exceptions.ApiException;
import com.egy.clubtalk.exceptions.user.EmailAlreadyTakenException;
import com.egy.clubtalk.exceptions.user.UserAlreadyFollowedException;
import com.egy.clubtalk.exceptions.user.UserCanNotFollowHimselfException;
import com.egy.clubtalk.exceptions.user.UserNotFoundException;
import com.egy.clubtalk.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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

    public UserEntity getByEmailOrUserName(String email) {
        Optional<UserDAO> user = userRepository.findByEmailOrUserName(email);

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

    public void follow(String from, Long to) {
        Optional<UserDAO> user = userRepository.findByEmail(from);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        Optional<UserDAO> toFollow = userRepository.findById(to);
        if (toFollow.isEmpty()) {
            throw new UserNotFoundException();
        }

        if(user.get().getID().equals(toFollow.get().getID())) {
            throw new UserCanNotFollowHimselfException();
        }

        Set<UserDAO> followings = user.get().getFollowing();
        if(followings.contains(toFollow.get())) {
            throw new UserAlreadyFollowedException();
        }

        user.get().getFollowing().add(toFollow.get());

        userRepository.save(user.get());
    }

    public List<UserEntity> getFollowersByUserId(Long userId) {
        Optional<UserDAO> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new UserNotFoundException();
        }

        return user.get().getFollowers().stream().map((u) -> new UserEntity().fromUserDao(u)).collect(Collectors.toList());
    }

    public List<UserEntity> getFolloweeByUserId(Long userId) {
        Optional<UserDAO> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new UserNotFoundException();
        }

        return user.get().getFollowing().stream().map((u) -> new UserEntity().fromUserDao(u)).collect(Collectors.toList());
    }
}
