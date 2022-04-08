package com.example.finalyearproject;

//this interface is used to return data from AsyncTask child classes to other activities/fragments
public interface AsyncResponse {
    void processFinish(String output);
}
