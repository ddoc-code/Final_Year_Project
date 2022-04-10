package com.example.finalyearproject.ui.eventDetail;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalyearproject.R;
import com.example.finalyearproject.databinding.FragmentEventDetailBinding;

public class EventDetailFragment extends Fragment {

    private EventDetailViewModel eventDetailViewModel;
    private FragmentEventDetailBinding binding;

    public static EventDetailFragment newInstance() {
        return new EventDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //prevents fragments from displaying on top of each other
        if (container != null) {
            container.removeAllViews();
        }
        //TODO: Continue from here. Implement the rest of EventDetailFragment.java + xml

        return inflater.inflate(R.layout.fragment_event_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        eventDetailViewModel = new ViewModelProvider(this).get(EventDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}