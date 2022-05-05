package com.example.finalyearproject;

import java.io.Serializable;

//class implements Serializable interface to allow event data to
//be passed between activities using the Intent putExtra method
public class event implements Serializable {

    int id;
    int venueID;
    String title;
    String description;
    String date;
    String time;
    String category;
    String category2;
    Boolean ticketsNeeded;
    String ticketPrice;
    String ticketsPage;

    public event(int id, int venueID, String title, String description, String date, String time, String category, String category2, Boolean ticketsNeeded, String ticketPrice, String ticketsPage) {
        this.id = id;
        this.venueID = venueID;
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.category = category;
        this.category2 = category2;
        this.ticketsNeeded = ticketsNeeded;
        this.ticketPrice = ticketPrice;
        this.ticketsPage = ticketsPage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVenueID() {
        return venueID;
    }

    public void setVenueID(int venueID) {
        this.venueID = venueID;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public Boolean getTicketsNeeded() {
        return ticketsNeeded;
    }

    public void setTicketsNeeded(Boolean ticketsNeeded) {
        this.ticketsNeeded = ticketsNeeded;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getTicketsPage() {
        return ticketsPage;
    }

    public void setTicketsPage(String ticketsPage) {
        this.ticketsPage = ticketsPage;
    }
}