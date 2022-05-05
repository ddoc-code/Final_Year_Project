package com.example.finalyearproject.ui.groupRequests;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GroupRequestsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GroupRequestsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the group requests fragment");
    }

    public LiveData<String> getText() {return mText;}
}