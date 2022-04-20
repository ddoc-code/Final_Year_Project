package com.example.finalyearproject.ui.createGroup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateGroupViewModel extends ViewModel {

    private MutableLiveData<String> mtext;

    public CreateGroupViewModel() {
        mtext = new MutableLiveData<>();
        mtext.setValue("This is the create group fragment");
    }

    public LiveData<String> getText() {return mtext;}
}