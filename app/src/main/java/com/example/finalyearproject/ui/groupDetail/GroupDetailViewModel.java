package com.example.finalyearproject.ui.groupDetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GroupDetailViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GroupDetailViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the group detail fragment");
    }

    public LiveData<String> getText() {return mText;}
}