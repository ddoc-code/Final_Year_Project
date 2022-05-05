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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.finalyearproject.AsyncResponse;
import com.example.finalyearproject.R;
import com.example.finalyearproject.createRequestProcess;
import com.example.finalyearproject.currentUser;
import com.example.finalyearproject.databinding.FragmentGroupDetailBinding;
import com.example.finalyearproject.event;
import com.example.finalyearproject.group;
import com.example.finalyearproject.venue;

public class GroupDetailFragment extends Fragment implements AsyncResponse {

    private GroupDetailViewModel groupDetailViewModel;
    private FragmentGroupDetailBinding binding;

    private currentUser cu;
    private event event;
    private venue venue;
    private group group;

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
        cu = (currentUser) intent.getSerializableExtra("current_user");
        event = (event) intent.getSerializableExtra("event");
        venue = (venue) intent.getSerializableExtra("venue");
        group = (group) intent.getSerializableExtra("group");

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

        //set click listener for Request to Join button
        Button button = binding.groupDetailJoinButton;
        button.setOnClickListener(onClickListener);

        //set click listener for Send Request button
        Button button2 = binding.groupDetailSendButton;
        button2.setOnClickListener(onClickListener);

//        return inflater.inflate(R.layout.fragment_group_detail, container, false);
        return root;
    }

    //click listener for Request to Join button
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.group_detail_joinButton:
                    //expand the linearlayout when button is clicked
                    LinearLayout ll = binding.groupDetailExpandingLL;
                    ll.setVisibility(View.VISIBLE);
                    break;
                case R.id.group_detail_sendButton:
                    //call createRequest method when button is clicked

                    //get user input
                    EditText messageInput = getActivity().findViewById(R.id.group_detail_enterMessage);

                    //format strings pass as parameters to createRequestProcess
                    String senderID = String.valueOf(cu.getId());
                    String recipientID = String.valueOf(group.getCreatorID());
                    String groupID = String.valueOf(group.getId());
                    String message = messageInput.getText().toString();
                    String seen = String.valueOf(0);

                    if (message.length() > 0) {
                        //call createRequest function to insert record in database
                        createRequest(senderID, recipientID, groupID, message, seen);
                    }
                    else {messageInput.setText("You must enter a message!");}
                    break;
            }
        }
    };

    public void createRequest(String senderID, String recipientID, String groupID, String message, String seen) {

        //create createRequestProcess
        createRequestProcess crp = new createRequestProcess();
        //connect crp with this activity using AsyncResponse interface
        crp.delegate = this;
        //execute AsyncTask with relevant parameters
        crp.execute(senderID, recipientID, groupID, message, seen);
    }

    @Override
    public void processFinish(String output) {
//        System.out.println("GDF: " + output);
        ((AppCompatActivity) getContext()).getSupportFragmentManager().popBackStackImmediate(); //return to previous fragment after request created
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        groupDetailViewModel = new ViewModelProvider(this).get(GroupDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}