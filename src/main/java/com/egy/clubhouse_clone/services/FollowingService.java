package com.egy.clubhouse_clone.services;

import com.egy.clubhouse_clone.dao.Follower;
import com.egy.clubhouse_clone.dao.UserDAO;
import com.egy.clubhouse_clone.entity.UserEntity;
import com.egy.clubhouse_clone.exceptions.user.UserCanNotFollowHimselfException;
import com.egy.clubhouse_clone.exceptions.user.UserNotFoundException;
import com.egy.clubhouse_clone.repository.FollowingRepository;
import com.egy.clubhouse_clone.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowingService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FollowingRepository followingRepository;

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

        if(user.get().getFollowing().stream().filter((f) -> f.getFollowee().getID().equals(toFollow.get().getID())).toArray().length > 0) {
            return;
        }

        Follower follower = new Follower();
        follower.setFollowee(toFollow.get());
        follower.setFollower(user.get());

        followingRepository.save(follower);
    }

    public List<UserEntity> getFollowersByUserId(Long userId) {
        Optional<UserDAO> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new UserNotFoundException();
        }

        List<Follower> followers = followingRepository.getByFollowee(user.get());
        List<UserDAO> users = followers.stream().map((f) -> f.getFollower()).collect(Collectors.toList());
        return users.stream().map((u) -> new UserEntity().fromUserDao(u)).collect(Collectors.toList());
    }

    public List<UserEntity> getFolloweeByUserId(Long userId) {
        Optional<UserDAO> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new UserNotFoundException();
        }

        List<Follower> followers = followingRepository.getByFollower(user.get());
        List<UserDAO> users = followers.stream().map((f) -> f.getFollowee()).collect(Collectors.toList());
        return users.stream().map((u) -> new UserEntity().fromUserDao(u)).collect(Collectors.toList());
    }
}
