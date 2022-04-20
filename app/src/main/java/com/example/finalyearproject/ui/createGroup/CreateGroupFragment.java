package com.example.finalyearproject.ui.createGroup;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.finalyearproject.AsyncResponse;
import com.example.finalyearproject.R;
import com.example.finalyearproject.createGroupProcess;
import com.example.finalyearproject.currentUser;
import com.example.finalyearproject.databinding.FragmentCreateGroupBinding;
import com.example.finalyearproject.event;
import com.example.finalyearproject.venue;

public class CreateGroupFragment extends Fragment implements AsyncResponse {

    private CreateGroupViewModel createGroupViewModel;
    private FragmentCreateGroupBinding binding;
    private currentUser cu;
    private event event;
    private venue venue;
    private Spinner spinner;

    public static CreateGroupFragment newInstance() {
        return new CreateGroupFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //prevents fragments from displaying on top of each other
        if (container != null) {
            container.removeAllViews();
        }

        createGroupViewModel = new ViewModelProvider(this).get(CreateGroupViewModel.class);

        binding = FragmentCreateGroupBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //extract currentUser/event/venue data from intent
        Intent intent = getActivity().getIntent();
        cu = (currentUser) intent.getSerializableExtra("current_user");
        event = (event) intent.getSerializableExtra("event");
        venue = (venue) intent.getSerializableExtra("venue");

        //set title bar text
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(venue.getName());

        //set text for selected event
        createGroupViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.createGroupEventVenue.setText(venue.getName());
                binding.createGroupEventTitle.setText(event.getTitle());
            }
        });

        //find spinner and initialise Integer array to fill it
        spinner = (Spinner) binding.createGroupMaxPeopleInput;
        Integer[] spinnerItems = new Integer[]{2,3,4,5,6,7,8,9,10};

        //create and set adapter to fill spinner
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getContext(),R.layout.support_simple_spinner_dropdown_item, spinnerItems);
        spinner.setAdapter(adapter);

        //set click listener for button
        Button button = binding.createGroupButton;
        button.setOnClickListener(onClickListener);

//        return inflater.inflate(R.layout.fragment_create_group, container, false);
        return root;
    }

    //click listener for Create Group button
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //retrieve user input
            EditText titleInput = getActivity().findViewById(R.id.create_group_titleInput);
            EditText descInput = getActivity().findViewById(R.id.create_group_descriptionInput);

            //format strings to pass to createGroupProcess
            String eventID = String.valueOf(event.getId());
            String groupTitle = titleInput.getText().toString();
            String groupDesc = descInput.getText().toString();
            String maxPeople = spinner.getSelectedItem().toString();
            String currentPeople = String.valueOf(1);
            String attendees = cu.getUsername();
            String creator = cu.getUsername();

            //call createGroup function to insert record in database
            createGroup(eventID, groupTitle, groupDesc, maxPeople, currentPeople, attendees, creator);
        }
    };

    //this had to be in a separate function to allow the AsyncResponse to function correctly
    public void createGroup(String eventID, String title, String desc, String max, String current, String attendees, String creator) {
        //create createGroupProcess
        createGroupProcess cgp = new createGroupProcess();
        //connect rgp with this activity using AsyncResponse interface
        cgp.delegate = this;
        //execute AsyncTask with relevant parameters
        cgp.execute(eventID, title, desc, max, current, attendees, creator);
    }

    @Override
    public void processFinish(String output) {
//        System.out.println("CGF: " + output);
        ((AppCompatActivity) getContext()).getSupportFragmentManager().popBackStackImmediate(); //return to previous fragment after group created
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createGroupViewModel = new ViewModelProvider(this).get(CreateGroupViewModel.class);
        // TODO: Use the ViewModel
    }

}