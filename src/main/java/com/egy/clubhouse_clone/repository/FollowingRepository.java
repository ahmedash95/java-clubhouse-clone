package com.egy.clubhouse_clone.repository;

import com.egy.clubhouse_clone.dao.Follower;
import com.egy.clubhouse_clone.dao.UserDAO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowingRepository extends JpaRepository<Follower, Long> {
    List<Follower> getByFollowee(UserDAO user);

    List<Follower> getByFollower(UserDAO user);
}
