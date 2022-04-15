package com.example.finalyearproject.ui.eventDetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EventDetailViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EventDetailViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the event detail fragment");
    }

    public LiveData<String> getText() {return mText;}
}