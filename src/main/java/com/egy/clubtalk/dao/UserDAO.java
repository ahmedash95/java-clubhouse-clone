package com.egy.clubtalk.dao;

import com.egy.clubtalk.entity.ProfileEntity;
import com.egy.clubtalk.entity.UserEntity;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long ID;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    private String bio;
    private String picture;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private boolean verified;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_following", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "following_id"))
    private Set<UserDAO> following;

    @ManyToMany(mappedBy = "following")
    private Set<UserDAO> followers;

    @OneToMany(
        mappedBy = "owner",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private List<RoomDAO> rooms;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Set<UserDAO> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<UserDAO> followers) {
        this.followers = followers;
    }

    public Set<UserDAO> getFollowing() {
        return following;
    }

    public void setFollowing(Set<UserDAO> following) {
        this.following = following;
    }

    public List<RoomDAO> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDAO> rooms) {
        this.rooms = rooms;
    }

    public UserDAO() {
    }

    public UserDAO(UserEntity user) {
        this.setID(user.getId());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setEmail(user.getEmail());
        this.setBio(user.getBio());
        this.setPicture(user.getPicture());
    }

    public void fillFromProfile(ProfileEntity profileEntity) {
        this.setFirstName(profileEntity.getFirstName());
        this.setLastName(profileEntity.getLastName());
        this.setBio(profileEntity.getBio());
    }
}
