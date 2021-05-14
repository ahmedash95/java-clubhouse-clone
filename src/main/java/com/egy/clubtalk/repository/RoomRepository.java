package com.egy.clubtalk.repository;

import com.egy.clubtalk.dao.RoomDAO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<RoomDAO, Long> {
    List<RoomDAO> findByOwnerID(Long id);
}
