package com.example.finalyearproject;

import java.io.Serializable;

//class implements Serializable interface to allow group data to
//be passed between activities using the Intent putExtra method
public class group implements Serializable {

    int id;
    int eventID;
    String title;
    String description;
    int maxPeople;
    int currentPeople;
    String[] attendeesArr;
    String creator;
    int creatorID;

    public group(int id, int eventID, String title, String description, int maxPeople, int currentPeople, String attendees, String creator, int creatorID) {
        this.id = id;
        this.eventID = eventID;
        this.title = title;
        this.description = description;
        this.maxPeople = maxPeople;
        this.currentPeople = currentPeople;
        this.attendeesArr = attendees.split(",");
        this.creator = creator;
        this.creatorID = creatorID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public int getCurrentPeople() {
        return currentPeople;
    }

    public void setCurrentPeople(int currentPeople) {
        this.currentPeople = currentPeople;
    }

    public String[] getAttendeesArr() {
        return attendeesArr;
    }

    public void setAttendeesArr(String[] attendeesArr) {
        this.attendeesArr = attendeesArr;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(int creatorID) {
        this.creatorID = creatorID;
    }
}
