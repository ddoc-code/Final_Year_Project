package com.example.finalyearproject.ui.groupDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalyearproject.R;
import com.example.finalyearproject.currentUser;
import com.example.finalyearproject.databinding.FragmentGroupDetailBinding;
import com.example.finalyearproject.event;
import com.example.finalyearproject.group;
import com.example.finalyearproject.venue;

public class GroupDetailFragment extends Fragment {

    private GroupDetailViewModel groupDetailViewModel;
    private FragmentGroupDetailBinding binding;

    public static GroupDetailFragment newInstance() {
        return new GroupDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //prevents fragments from displaying on top of each other
        if (container != null) {
            container.removeAllViews();
        }

        groupDetailViewModel = new ViewModelProvider(this).get(GroupDetailViewModel.class);

        binding = FragmentGroupDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //extract currentUser data from intent
        Intent intent = getActivity().getIntent();
        currentUser cu = (currentUser) intent.getSerializableExtra("current_user");
        event event = (event) intent.getSerializableExtra("event");
        venue venue = (venue) intent.getSerializableExtra("venue");
        group group = (group) intent.getSerializableExtra("group");
//        System.out.println(event.getTitle());
//        System.out.println(venue.getName());

        //set title bar text
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(venue.getName());

        //set text for selected event/group
        groupDetailViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.groupDetailEventVenue.setText(venue.getName());
                binding.groupDetailEventTitle.setText(event.getTitle());
                binding.groupDetailEventDesc.setText(event.getDescription());

                binding.groupDetailGroupTitle.setText(group.getTitle());
                binding.groupDetailGroupAttendees.setText("Attendees: " + group.getCurrentPeople() + "/" + group.getMaxPeople());
                binding.groupDetailGroupDesc.setText(group.getDescription());
                binding.groupDetailGroupCreator.setText("Created by: " + group.getCreator());
            }
        });

//        return inflater.inflate(R.layout.fragment_group_detail, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        groupDetailViewModel = new ViewModelProvider(this).get(GroupDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}