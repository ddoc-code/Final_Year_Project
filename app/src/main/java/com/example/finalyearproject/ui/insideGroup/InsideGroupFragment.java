package com.example.finalyearproject.ui.insideGroup;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.finalyearproject.R;
import com.example.finalyearproject.currentUser;
import com.example.finalyearproject.databinding.FragmentInsideGroupBinding;
import com.example.finalyearproject.event;
import com.example.finalyearproject.group;
import com.example.finalyearproject.venue;

public class InsideGroupFragment extends Fragment {

    private InsideGroupViewModel insideGroupViewModel;
    private FragmentInsideGroupBinding binding;

    public static InsideGroupFragment newInstance() {
        return new InsideGroupFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //prevents fragments from displaying on top of each other
        if (container != null) {
            container.removeAllViews();
        }

        insideGroupViewModel = new ViewModelProvider((this)).get(InsideGroupViewModel.class);

        binding = FragmentInsideGroupBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //extract currentUser data from intent
        Intent intent = getActivity().getIntent();
        currentUser cu = (currentUser) intent.getSerializableExtra("current_user");
        event event = (event) intent.getSerializableExtra("event");
        venue venue = (venue) intent.getSerializableExtra("venue");
        group group = (group) intent.getSerializableExtra("group");

        //set title bar text
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(venue.getName());

        insideGroupViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.insideGroupTitle.setText(group.getTitle());
                binding.insideGroupAttendees.setText("Attendees: " + group.getCurrentPeople() + "/" + group.getMaxPeople());
                binding.insideGroupDescription.setText(group.getDescription());
            }
        });

        //fill linearlayout with group members
        LinearLayout linearLayout = binding.insideGroupMembersLL;

        for (int i = 0; i < group.getAttendeesArr().length; i++) {
            TextView textView = new TextView(getContext());
            if (i == 0) {
                textView.setText(group.getAttendeesArr()[i] + (" (Creator)"));
            }
            else {
                textView.setText(group.getAttendeesArr()[i]);
            }

            linearLayout.addView(textView);
        }

//        return inflater.inflate(R.layout.fragment_inside_group, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        insideGroupViewModel = new ViewModelProvider(this).get(InsideGroupViewModel.class);
        // TODO: Use the ViewModel
    }

}