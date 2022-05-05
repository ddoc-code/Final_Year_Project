package com.example.finalyearproject;

import java.io.Serializable;

//class to store details of non-logged in users
//class implements Serializable interface to allow otherUser data to
//be passed between activities using the Intent putExtra method
public class otherUser implements Serializable {

    int id;
    String username;
    String location;
    String bio;
    String interests;
    String[] interestsArr;

    public otherUser(int id, String username, String location, String bio, String interests) {
        this.id = id;
        this.username = username;
        this.location = location;
        this.bio = bio;
        this.interests = interests;
        this.interestsArr = interests.split(",");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String[] getInterestsArr() {
        return interestsArr;
    }

    public void setInterestsArr(String[] interestsArr) {
        this.interestsArr = interestsArr;
    }
}
