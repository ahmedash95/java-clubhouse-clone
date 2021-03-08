package com.egy.clubhouse_clone.repository;

import com.egy.clubhouse_clone.dao.UserDAO;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDAO, Long> {
    Optional<UserDAO> findByEmail(String email);
}
