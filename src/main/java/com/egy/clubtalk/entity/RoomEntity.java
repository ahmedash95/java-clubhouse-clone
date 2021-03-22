package com.egy.clubtalk.entity;

import com.egy.clubtalk.dao.RoomDAO;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RoomEntity {
    private Long id;

    @JsonProperty("title")
    @NotBlank(message = "Room title is required.")
    @Size(min = 3, max = 15)
    private String name;

    private UserEntity owner;

    private List<UserEntity> managers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public List<UserEntity> getManagers() {
        return managers;
    }

    public void setManagers(List<UserEntity> managers) {
        this.managers = managers;
    }

    public RoomEntity fromDAO(RoomDAO dao) {
        RoomEntity entity = new RoomEntity();
        entity.setId(dao.getID());
        entity.setName(dao.getName());
        entity.setOwner(new UserEntity().fromUserDao(dao.getOwner()));
        if(dao.getManagers() != null && dao.getManagers().size() > 0) {
            entity.setManagers(dao.getManagers().stream().map((u) -> new UserEntity().fromUserDao(u)).collect(Collectors.toList()));
        }

        return entity;
    }
}
