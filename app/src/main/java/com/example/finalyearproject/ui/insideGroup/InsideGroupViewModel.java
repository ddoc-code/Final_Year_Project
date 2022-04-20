package com.example.finalyearproject.ui.insideGroup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InsideGroupViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public InsideGroupViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the inside group fragment");
    }

    public LiveData<String> getText() {return mText;}
}