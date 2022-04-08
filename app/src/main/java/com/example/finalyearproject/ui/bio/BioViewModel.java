package com.example.finalyearproject.ui.bio;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BioViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BioViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the bio fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}