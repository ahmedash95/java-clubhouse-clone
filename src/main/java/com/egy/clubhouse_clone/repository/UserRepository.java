package com.egy.clubhouse_clone.repository;

import com.egy.clubhouse_clone.dao.UserDAO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserDAO, Long> {
    Optional<UserDAO> findByEmail(String email);

    @Query("SELECT u FROM UserDAO u WHERE (u.firstName LIKE %?1% OR u.lastName LIKE %?1%) AND u.verified = true")
    List<UserDAO> search(String query);
}
