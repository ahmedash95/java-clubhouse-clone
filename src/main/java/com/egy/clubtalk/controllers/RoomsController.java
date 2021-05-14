package com.egy.clubtalk.controllers;

import com.egy.clubtalk.entity.RoomEntity;
import com.egy.clubtalk.entity.RoomInvitation;
import com.egy.clubtalk.entity.UserEntity;
import com.egy.clubtalk.services.RoomsService;
import com.egy.clubtalk.services.UserService;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
public class RoomsController {

    @Autowired
    RoomsService roomService;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public ResponseEntity<Object> getRooms(@AuthenticationPrincipal UserDetails user) {
        UserEntity owner = userService.getByEmail(user.getUsername());
        List<RoomEntity> rooms = roomService.getRoomsByOwner(owner);

        return new ResponseEntity<>(rooms, HttpStatus.CREATED);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createRoom(@Validated @RequestBody RoomEntity room, @AuthenticationPrincipal UserDetails user) {
        room.setOwner(userService.getByEmail(user.getUsername()));
        RoomEntity roomEntity = roomService.createRoom(room);

        return new ResponseEntity<>(roomEntity, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> showRoom(@PathVariable("id") Long roomId) {
        RoomEntity room = roomService.find(roomId);

        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }
    @PostMapping("/{id}/invite")
    public  ResponseEntity<Object> inviteToRoom(@PathVariable("id") Long roomId, @Validated @RequestBody RoomInvitation invitation, @AuthenticationPrincipal UserDetails user) {
        UserEntity sender = userService.getByEmail(user.getUsername());
        roomService.inviteToRoom(roomId, sender, invitation);

        HashMap<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "User has been invited to room.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
