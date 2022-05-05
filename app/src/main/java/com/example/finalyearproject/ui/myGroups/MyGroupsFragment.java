package com.example.finalyearproject.ui.myGroups;

import androidx.appcompat.app.AppCompatActivity;
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

import com.example.finalyearproject.AsyncResponse;
import com.example.finalyearproject.R;
import com.example.finalyearproject.currentUser;
import com.example.finalyearproject.databinding.FragmentMyGroupsBinding;
import com.example.finalyearproject.event;
import com.example.finalyearproject.group;
import com.example.finalyearproject.myGroupRecyclerViewAdapter;
import com.example.finalyearproject.retrieveMyGroupsProcess;
import com.example.finalyearproject.venue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyGroupsFragment extends Fragment implements AsyncResponse {

    private MyGroupsViewModel myGroupsViewModel;
    private FragmentMyGroupsBinding binding;
    private RecyclerView recyclerView;

    public static MyGroupsFragment newInstance() {
        return new MyGroupsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //prevents fragments from displaying on top of each other
        if (container != null) {
            container.removeAllViews();
        }

        myGroupsViewModel = new ViewModelProvider(this).get(MyGroupsViewModel.class);

        binding = FragmentMyGroupsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //extract currentUser data from intent
        Intent intent = getActivity().getIntent();
        currentUser cu = (currentUser) intent.getSerializableExtra("current_user");

        //set title bar text
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("My Groups");

        //create retrieveMyGroupsProcess
        retrieveMyGroupsProcess rmgp = new retrieveMyGroupsProcess();
        //connect rep with this activity using AsyncResponse interface
        rmgp.delegate = this;
        //execute AsyncTask with username
        rmgp.execute(cu.getUsername());

//        return inflater.inflate(R.layout.fragment_my_groups, container, false);
        return root;
    }

    @Override
    public void processFinish(String output) {
//        System.out.println("MGF: " + output);

        try {
            JSONObject json = new JSONObject(output);

            //check groups were successfully retrieved
            if ((Integer) json.get("success") == 1) {

                //get array values from keys
                JSONArray jsonArr = (JSONArray) json.get("groups");
                JSONArray jsonArr2 = (JSONArray) json.get("events");
                JSONArray jsonArr3 = (JSONArray) json.get("venues");

                //initialise arraylist to store groups
                ArrayList<group> groupList = new ArrayList<group>();

                //fill arraylist with groups
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
                    int creatorID = Integer.parseInt((String) jsonGroup.get("creatorID"));

                    //create new group and add to arraylist
                    group newGroup = new group(id, eventID, title, desc, max, current, attendees, creator, creatorID);
                    groupList.add(newGroup);
                }

                //initialise arraylist to store events
                ArrayList<event> eventList = new ArrayList<event>();

                //fill arraylist with events
                for (int i = 0; i < jsonArr2.length(); i++) {

                    //get each json event in turn
                    JSONObject jsonEvent = jsonArr2.getJSONObject(i);

                    //grab JSON data for this event
                    int id = Integer.parseInt((String) jsonEvent.get("id"));
                    int venueID = Integer.parseInt((String) jsonEvent.get("venueID"));
                    String title = (String) jsonEvent.get("title");
                    String desc = (String) jsonEvent.get("description");
                    String date = (String) jsonEvent.get("date");
                    String time = (String) jsonEvent.get("time");
                    String category = (String) jsonEvent.get("category");
                    String category2 = (String) jsonEvent.get("category2");
                    Boolean ticketsNeeded = (Integer.parseInt((String) jsonEvent.get("ticketsNeeded")) == 1);
                    String ticketPrice = (String) jsonEvent.get("ticketPrice");
                    String page = (String) jsonEvent.get("ticketsPage");

                    //create new event and add to arraylist
                    event newEvent = new event(id, venueID, title, desc, date, time, category, category2, ticketsNeeded, ticketPrice, page);
                    eventList.add(newEvent);
                }

                //initialise arraylist to store venues
                ArrayList<venue> venueList = new ArrayList<venue>();

                //fill arraylist with venues
                for (int i = 0; i < jsonArr3.length(); i ++) {

                    //get each json venue in turn
                    JSONObject jsonVenue = jsonArr3.getJSONObject(i);

                    //grab JSON data for this venue
                    int id = Integer.parseInt((String) jsonVenue.get("id"));
                    String name = (String) jsonVenue.get("name");
                    String address = (String) jsonVenue.get("address");
                    String phone = (String) jsonVenue.get("phone");
                    String email = (String) jsonVenue.get("email");

                    //create new venue and add to arraylist
                    venue newVenue = new venue(id, name, address, phone, email);
                    venueList.add(newVenue);
                }

                //initialise recyclerView, adapter and LLManager and pass groupList/eventList/venueList
                recyclerView = getActivity().findViewById(R.id.my_groups_recyclerView);
                myGroupRecyclerViewAdapter adapter = new myGroupRecyclerViewAdapter(groupList, eventList, venueList, getContext());
                LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(adapter);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myGroupsViewModel = new ViewModelProvider(this).get(MyGroupsViewModel.class);
        // TODO: Use the ViewModel
    }

}