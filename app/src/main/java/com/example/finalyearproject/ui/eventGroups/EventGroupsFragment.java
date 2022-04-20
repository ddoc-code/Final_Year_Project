package com.example.finalyearproject.ui.eventGroups;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalyearproject.AsyncResponse;
import com.example.finalyearproject.R;
import com.example.finalyearproject.currentUser;
import com.example.finalyearproject.databinding.FragmentEventGroupsBinding;
import com.example.finalyearproject.event;
import com.example.finalyearproject.group;
import com.example.finalyearproject.groupRecyclerViewAdapter;
import com.example.finalyearproject.retrieveGroupsProcess;
import com.example.finalyearproject.ui.createGroup.CreateGroupFragment;
import com.example.finalyearproject.venue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EventGroupsFragment extends Fragment implements AsyncResponse {

    private EventGroupsViewModel eventGroupsViewModel;
    private FragmentEventGroupsBinding binding;
    private RecyclerView recyclerView;

    public static EventGroupsFragment newInstance() {
        return new EventGroupsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //prevents fragments from displaying on top of each other
        if (container != null) {
            container.removeAllViews();
        }

        eventGroupsViewModel = new ViewModelProvider(this).get(EventGroupsViewModel.class);

        binding = FragmentEventGroupsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //extract currentUser/event/venue data from intent
        Intent intent = getActivity().getIntent();
        currentUser cu = (currentUser) intent.getSerializableExtra("current_user");
        event event = (event) intent.getSerializableExtra("event");
        venue venue = (venue) intent.getSerializableExtra("venue");
//        System.out.println(event.getTitle());
//        System.out.println(venue.getName());

        //set title bar text
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(venue.getName());

        //set text for selected event
        eventGroupsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.eventGroupsVenue.setText(venue.getName());
                binding.eventGroupsTitle.setText(event.getTitle());
                binding.eventGroupsDesc.setText(event.getDescription());
            }
        });

        //set click listener for button
        Button button = binding.eventGroupsButton;
        button.setOnClickListener(onClickListener);

        //create retrieveGroupsProcess
        retrieveGroupsProcess rgp = new retrieveGroupsProcess();
        //connect rgp with this activity using AsyncResponse interface
        rgp.delegate = this;
        //execute AsyncTask with event id
        rgp.execute(String.valueOf(event.getId()));

//        return inflater.inflate(R.layout.fragment_event_groups, container, false);
        return root;
    }

    @Override
    public void processFinish(String output) {

        try {
            //outer JSON object
            //contains "success" and "groups" keys and their values
            JSONObject json = new JSONObject(output);
//            System.out.println(json);

            //check if groups were successfully retrieved
            if ((Integer) json.get("success") == 1) {

                //initialise arraylist to store groups
                ArrayList<group> groupList = new ArrayList<group>();

                //JSON array containing inner JSON objects (value of "groups" key)
                //contains groups
                JSONArray jsonArr = (JSONArray) json.get("groups");

                for (int i = 0; i < jsonArr.length(); i++) {

                    //get each json group in turn
                    JSONObject jsonGroup = jsonArr.getJSONObject(i);

                    //grab JSON data for this group
                    int id = Integer.parseInt((String) jsonGroup.get("id"));
                    int eventID = Integer.parseInt((String) jsonGroup.get("eventID"));
                    String title = (String) jsonGroup.get("title");
                    String desc = (String) jsonGroup.get("description");
                    int max = Integer.parseInt((String) jsonGroup.get("maxPeople"));
                    int current = Integer.parseInt((String) jsonGroup.get("currentPeople"));
                    String attendees = (String) jsonGroup.get("attendees");
                    String creator = (String) jsonGroup.get("creator");

                    //create new group and add to arraylist
                    group newGroup = new group(id, eventID, title, desc, max, current, attendees, creator);
                    groupList.add(newGroup);
                }

                //initialise recyclerView, adapter and LLManager and pass groupList
                recyclerView = getActivity().findViewById(R.id.groups_recyclerview);
                groupRecyclerViewAdapter adapter = new groupRecyclerViewAdapter(groupList, getContext());
                LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(adapter);
            }

            else {
                TextView tv = getActivity().findViewById(R.id.event_groups_header);
                tv.setText("No groups exist for this event! Create one below.");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //click listener for Create New Group button
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //go to createGroupFragment
            Fragment createGroupFragment = new CreateGroupFragment();
            FragmentTransaction transaction = ((AppCompatActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment_activity_home, createGroupFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        eventGroupsViewModel = new ViewModelProvider(this).get(EventGroupsViewModel.class);
        // TODO: Use the ViewModel
    }

}