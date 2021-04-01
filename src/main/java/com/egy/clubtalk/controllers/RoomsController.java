package com.egy.clubtalk.controllers;

import com.egy.clubtalk.entity.RoomEntity;
import com.egy.clubtalk.entity.RoomInvitation;
import com.egy.clubtalk.entity.UserEntity;
import com.egy.clubtalk.services.RoomsService;
import com.egy.clubtalk.services.UserService;
import java.util.HashMap;
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

    @PostMapping("/")
    public ResponseEntity<Object> createRoom(@Validated @RequestBody RoomEntity room, @AuthenticationPrincipal UserDetails user) {
        room.setOwner(userService.getByEmail(user.getUsername()));
        roomService.createRoom(room);

        return new ResponseEntity<>(HttpStatus.CREATED);
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
