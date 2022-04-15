package com.example.finalyearproject.ui.eventGroups;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EventGroupsViewModel extends ViewModel {

    private MutableLiveData<String> mtext;

    public EventGroupsViewModel() {
        mtext = new MutableLiveData<>();
        mtext.setValue("This is the event groups fragment");
    }

    public LiveData<String> getText() {return mtext;}
}