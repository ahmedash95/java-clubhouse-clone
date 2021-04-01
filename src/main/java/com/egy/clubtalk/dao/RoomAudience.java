package com.egy.clubtalk.dao;

import javax.persistence.*;

@Entity
@Table(name = "room_audience")
public class RoomAudience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserDAO user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sender_id")
    private UserDAO sender;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id")
    private RoomDAO room;

    public RoomAudience() {}

    public RoomAudience(UserDAO user, UserDAO sender, RoomDAO room) {
        this.user = user;
        this.sender = sender;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDAO getUser() {
        return user;
    }

    public void setUser(UserDAO user) {
        this.user = user;
    }

    public UserDAO getSender() {
        return sender;
    }

    public void setSender(UserDAO sender) {
        this.sender = sender;
    }

    public RoomDAO getRoom() {
        return room;
    }

    public void setRoom(RoomDAO room) {
        this.room = room;
    }
}
