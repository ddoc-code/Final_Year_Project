package com.example.finalyearproject.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.AsyncResponse;
import com.example.finalyearproject.HomeActivity;
import com.example.finalyearproject.R;
import com.example.finalyearproject.currentUser;
import com.example.finalyearproject.databinding.FragmentHomeBinding;
import com.example.finalyearproject.event;
import com.example.finalyearproject.eventRecyclerViewAdapter;
import com.example.finalyearproject.loginProcess;
import com.example.finalyearproject.retrieveEventsProcess;
import com.example.finalyearproject.venue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements AsyncResponse {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //prevents fragments from displaying on top of each other
        if (container != null) {
            container.removeAllViews();
        }

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //extract currentUser data from intent
        Intent intent = getActivity().getIntent();
        currentUser cu = (currentUser) intent.getSerializableExtra("current_user");

        //set title bar text
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");

        //create retrieveEventsProcess
        retrieveEventsProcess rep = new retrieveEventsProcess();
        //connect rep with this activity using AsyncResponse interface
        rep.delegate = this;
        //execute AsyncTask with users id
        rep.execute(String.valueOf(cu.getId()));

//        final TextView textView = binding.textHome;
        TextView textView = binding.homeGreeting; //this is the one we want to change
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
            public void onChanged(@Nullable String s) {
                textView.setText("Hello, " + cu.getUsername() + "!");
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void processFinish(String output) {

        try {
            //outer JSON object
            //contains "success", "events" and "venues" keys and their values
            JSONObject json = new JSONObject(output);
//            System.out.println("json: " + json);

            //check if events were successfully retrieved
            if ((Integer) json.get("success") == 1) {

                //JSON array containing inner JSON objects (value of above "events" key)
                //contains events
                JSONArray jsonArr = (JSONArray) json.get("events");
//                System.out.println("json array: " + jsonArr);
//                System.out.println("Length: " + jsonArr.length());

                //JSON array containing inner JSON objects (value of above "venues" key)
                //contains venues
                JSONArray jsonArr2 = (JSONArray) json.get("venues");
//                System.out.println("json array2: " + jsonArr2);
//                System.out.println("Length: " + jsonArr2.length());

                //initialise arraylist to store events
                ArrayList<event> eventList = new ArrayList<event>();

                //fill arraylist with events
                for (int i = 0; i < jsonArr.length(); i++) {

                    //get each json event in turn
                    JSONObject jsonEvent = jsonArr.getJSONObject(i);

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
                for (int i = 0; i < jsonArr2.length(); i ++) {

                    //get each json venue in turn
                    JSONObject jsonVenue = jsonArr2.getJSONObject(i);

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

                //initialise recyclerView, adapter and LLManager and pass eventList/venueList
                recyclerView = getActivity().findViewById(R.id.home_recyclerview);
                eventRecyclerViewAdapter adapter = new eventRecyclerViewAdapter(eventList, venueList, getContext());
                LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(adapter);
            }

            else {
//                TextView tv = getActivity().findViewById(R.id.home_yourEvents);
//                tv.setText("You have no interests!");
            }

            //catch JSON errors
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}