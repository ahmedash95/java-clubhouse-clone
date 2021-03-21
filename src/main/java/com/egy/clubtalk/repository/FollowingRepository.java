package com.egy.clubtalk.repository;

import com.egy.clubtalk.dao.Follower;
import com.egy.clubtalk.dao.UserDAO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowingRepository extends JpaRepository<Follower, Long> {
    List<Follower> getByFollowee(UserDAO user);

    List<Follower> getByFollower(UserDAO user);
}
