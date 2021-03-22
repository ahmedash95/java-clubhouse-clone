package com.egy.clubtalk.services;

import com.egy.clubtalk.dao.RoomDAO;
import com.egy.clubtalk.dao.UserDAO;
import com.egy.clubtalk.entity.RoomEntity;
import com.egy.clubtalk.exceptions.rooms.RoomOwnerIsInvalidException;
import com.egy.clubtalk.repository.RoomRepository;
import com.egy.clubtalk.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoomsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoomRepository roomRepository;

    public RoomEntity createRoom(RoomEntity room) {
        if (room.getOwner() == null) {
            throw new RoomOwnerIsInvalidException();
        }

        Optional<UserDAO> owner = userRepository.findById(room.getOwner().getId());
        if (owner.isEmpty()) {
            throw new RoomOwnerIsInvalidException();

        }

        RoomDAO dao = new RoomDAO().fromEntity(room, owner.get());
        roomRepository.save(dao);

        return new RoomEntity().fromDAO(dao);
    }

}
