package com.egy.clubtalk.dao;

import javax.persistence.*;

@Entity
public class Follower {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "followee")
    private UserDAO followee;

    @ManyToOne
    @JoinColumn(name = "follower")
    private UserDAO follower;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDAO getFollowee() {
        return followee;
    }

    public void setFollowee(UserDAO followee) {
        this.followee = followee;
    }

    public UserDAO getFollower() {
        return follower;
    }

    public void setFollower(UserDAO follower) {
        this.follower = follower;
    }


}