package com.example.finalyearproject;

import java.io.Serializable;
import java.util.ArrayList;

//class implements Serializable interface to allow user data to
//be passed between activities using the Intent putExtra method
public class currentUser implements Serializable {

    Boolean loggedIn;
    int id;
    String username;
    String password;
    String email;
    String location;
    String bio;
    String interests;
    String[] interestsArr;

    public currentUser() {
        this.loggedIn = false;
    }

    public void setUserInfo(int id, String username, String password, String email, String location, String bio, String interests) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.location = location;
        this.bio = bio;
        this.interests = interests;
        this.interestsArr = interests.split(",");
        this.loggedIn = true;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
