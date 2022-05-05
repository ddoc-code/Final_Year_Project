package com.example.finalyearproject.ui.otherUserBio;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OtherUserBioViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OtherUserBioViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the other user bio fragment");
    }

    public LiveData<String> getText() {return mText;}
}