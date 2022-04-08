package com.example.finalyearproject.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalyearproject.AsyncResponse;
import com.example.finalyearproject.HomeActivity;
import com.example.finalyearproject.R;
import com.example.finalyearproject.currentUser;
import com.example.finalyearproject.databinding.FragmentHomeBinding;
import com.example.finalyearproject.loginProcess;
import com.example.finalyearproject.retrieveEventsProcess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment implements AsyncResponse {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

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
            //contains "success" and "events" keys and values
            JSONObject json = new JSONObject(output);
            System.out.println("json: " + json);

            //check events were successfully retrieved
            if ((Integer) json.get("success") == 1) {

                //JSON array containing inner JSON object (value of above "events" key)
                //contains events
                JSONArray jsonArr = (JSONArray) json.get("events");
                System.out.println("json array: " + jsonArr);
                System.out.println("Length: " + jsonArr.length());

                //inner JSON object
                JSONObject jsonChild = jsonArr.getJSONObject(0);
//                JSONObject jsonChild2 = jsonArr.getJSONObject(1);
                System.out.println("0:" + jsonArr.getJSONObject(0));
                System.out.println("1:" + jsonArr.getJSONObject(1));
                System.out.println("2:" + jsonArr.getJSONObject(2));
                //TODO: Continue from here. Do I want to use a recyclerview to display the event cards?

                //grab JSON data (currently grabs only from 1st event)
                String title = (String) jsonChild.get("title");
                String desc = (String) jsonChild.get("description");
                String date = (String) jsonChild.get("date");
                String time = (String) jsonChild.get("time");
                String category = (String) jsonChild.get("category");
                String ticketPrice = (String) jsonChild.get("ticketPrice");
                String page = (String) jsonChild.get("ticketsPage");

                String out = title + desc + date + time + category + ticketPrice + page;

                TextView tv = getActivity().findViewById(R.id.textView);
                tv.setText(out);
            }

            else {
                TextView tv = getActivity().findViewById(R.id.home_yourEvents);
                tv.setText("You have no interests!");
            }

            //catch block is reached when login is unsuccessful
        } catch (JSONException e) {
            e.printStackTrace();
//            System.out.println("SOME FAILURE");
        }
    }
}