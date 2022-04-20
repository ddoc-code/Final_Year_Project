package com.example.finalyearproject;

public class message {

    int id;
    int groupID;
    int userID;
    String user;
    String text;
    Boolean deleted;

    public message(int id, int groupID, int userID, String user, String text, Boolean deleted) {
        this.id = id;
        this.groupID = groupID;
        this.userID = userID;
        this.user = user;
        this.text = text;
        this.deleted = deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
