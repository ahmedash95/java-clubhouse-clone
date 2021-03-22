package com.egy.clubtalk.repository;

import com.egy.clubtalk.dao.RoomDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<RoomDAO, Long> {
}
