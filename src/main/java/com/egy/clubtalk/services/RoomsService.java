package com.egy.clubtalk.services;

import com.egy.clubtalk.dao.RoomAudience;
import com.egy.clubtalk.dao.RoomDAO;
import com.egy.clubtalk.dao.UserDAO;
import com.egy.clubtalk.entity.RoomEntity;
import com.egy.clubtalk.entity.RoomInvitation;
import com.egy.clubtalk.entity.UserEntity;
import com.egy.clubtalk.exceptions.rooms.RoomNotFoundException;
import com.egy.clubtalk.exceptions.rooms.RoomOwnerIsInvalidException;
import com.egy.clubtalk.exceptions.rooms.UserAlreadyInvitedToRoomException;
import com.egy.clubtalk.exceptions.user.UserNotFoundException;
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

    public void inviteToRoom(Long roomId, UserEntity sender, RoomInvitation invitation) {
        Optional<UserDAO> receiver = userRepository.findById(invitation.getUserId());
        if (receiver.isEmpty()) {
            throw new UserNotFoundException();
        }

        Optional<RoomDAO> room = roomRepository.findById(roomId);
        if (room.isEmpty()) {
            throw new RoomNotFoundException();
        }

        if (room.get().getAudience().stream().filter((RoomAudience u) -> u.getUser().getID() == receiver.get().getID()).toArray().length > 0) {
            throw new UserAlreadyInvitedToRoomException();
        }

        UserDAO senderDAO = userRepository.findById(sender.getId()).get();

        room.get().getAudience().add(new RoomAudience(receiver.get(), senderDAO, room.get()));
    }

    public RoomEntity find(Long id) {
        Optional<RoomDAO> room = roomRepository.findById(id);
        if (room.isEmpty()) {
            throw new RoomNotFoundException();
        }

        return new RoomEntity().fromDAO(room.get());
    }
}
