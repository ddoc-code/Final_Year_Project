package com.example.finalyearproject;

public class request {

    int id;
    int senderID;
    int recipientID;
    int groupID;
    String message;
    boolean seen;
    boolean response;

    public request(int id, int senderID, int recipientID, int groupID, String message, boolean seen) {
        this.id = id;
        this.senderID = senderID;
        this.recipientID = recipientID;
        this.groupID = groupID;
        this.message = message;
        this.seen = seen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getRecipientID() {
        return recipientID;
    }

    public void setRecipientID(int recipientID) {
        this.recipientID = recipientID;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }
}