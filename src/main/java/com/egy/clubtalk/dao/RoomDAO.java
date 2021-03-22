package com.egy.clubtalk.dao;

import com.egy.clubtalk.entity.RoomEntity;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "rooms")
public class RoomDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long ID;

    @Column(nullable = false)
    public String name;

    @ManyToOne(fetch = FetchType.LAZY)
    public UserDAO owner;

    @OneToMany
    @JoinTable(name = "room_managers", joinColumns = @JoinColumn(name = "room_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    public List<UserDAO> managers;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserDAO getOwner() {
        return owner;
    }

    public void setOwner(UserDAO owner) {
        this.owner = owner;
    }

    public List<UserDAO> getManagers() {
        return managers;
    }

    public void setManagers(List<UserDAO> managers) {
        this.managers = managers;
    }

    public RoomDAO fromEntity(RoomEntity room, UserDAO owner) {
        RoomDAO dao = new RoomDAO();
        dao.setName(room.getName());
        dao.setOwner(owner);

        return dao;
    }
}
