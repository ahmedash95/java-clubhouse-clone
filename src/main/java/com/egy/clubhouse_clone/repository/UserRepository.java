package com.egy.clubhouse_clone.repository;

import com.egy.clubhouse_clone.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDAO, Long> {
    UserDAO findByEmail(String email);
}
