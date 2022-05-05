package com.example.finalyearproject.ui.groupRequests;

import androidx.appcompat.app.AppCompatActivity;
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

import com.example.finalyearproject.AsyncResponse;
import com.example.finalyearproject.R;
import com.example.finalyearproject.currentUser;
import com.example.finalyearproject.databinding.FragmentGroupRequestsBinding;
import com.example.finalyearproject.event;
import com.example.finalyearproject.group;
import com.example.finalyearproject.otherUser;
import com.example.finalyearproject.request;
import com.example.finalyearproject.requestRecyclerViewAdapter;
import com.example.finalyearproject.retrieveRequestsProcess;
import com.example.finalyearproject.venue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GroupRequestsFragment extends Fragment implements AsyncResponse {

    private GroupRequestsViewModel groupRequestsViewModel;
    private FragmentGroupRequestsBinding binding;
    private RecyclerView recyclerView;

    private currentUser cu;
    private com.example.finalyearproject.event event;
    private com.example.finalyearproject.venue venue;
    private com.example.finalyearproject.group group;

    public static GroupRequestsFragment newInstance() {
        return new GroupRequestsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //prevents fragments from displaying on top of each other
        if (container != null) {
            container.removeAllViews();
        }

        groupRequestsViewModel = new ViewModelProvider(this).get(GroupRequestsViewModel.class);

        binding = FragmentGroupRequestsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //extract currentUser/event/venue/group data from intent
        Intent intent = getActivity().getIntent();
        cu = (currentUser) intent.getSerializableExtra("current_user");
        event = (event) intent.getSerializableExtra("event");
        venue = (venue) intent.getSerializableExtra("venue");
        group = (group) intent.getSerializableExtra("group");

        //set title bar text
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Join Requests");

        groupRequestsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.groupRequestsTitle.setText(group.getTitle());
                binding.groupRequestsAttendees.setText("Attendees: " + group.getCurrentPeople() + "/" + group.getMaxPeople());
                binding.groupRequestsDescription.setText(group.getDescription());
            }
        });

        //call retrieve requests process
        retrieveRequests(String.valueOf(group.getId()));

//        return inflater.inflate(R.layout.fragment_group_requests, container, false);
        return root;
    }

    public void retrieveRequests(String groupID) {

        //create retrieveRequestsProcess
        retrieveRequestsProcess rrp = new retrieveRequestsProcess();
        //connect rrp with this activity using AsyncResponse interface
        rrp.delegate = this;
        //execute AsyncTask with groupID
        rrp.execute(groupID);
    }

    @Override
    public void processFinish(String output) {
//        System.out.println("GRF: " + output);

        try {
            JSONObject json = new JSONObject(output);

            //check requests were successfully retrieved
            if ((Integer) json.get("success") == 1) {

                //get value of "requests" key
                JSONArray jsonArr = (JSONArray) json.get("requests");
                JSONArray jsonArr2 = (JSONArray) json.get("users");

                //initialise arrayList to store requests
                ArrayList<request> requestList = new ArrayList<request>();

                //fill arraylist with requests
                for (int i = 0; i < jsonArr.length(); i++) {

                    //get each json request in turn
                    JSONObject jsonRequest = jsonArr.getJSONObject(i);

                    //grab JSON data for this request
                    int id = Integer.parseInt((String) jsonRequest.get("id"));
                    int senderID = Integer.parseInt((String) jsonRequest.get("senderID"));
                    int recipientID = Integer.parseInt((String) jsonRequest.get("recipientID"));
                    int groupID = Integer.parseInt((String) jsonRequest.get("groupID"));
                    String message = (String) jsonRequest.get("message");
                    boolean seen = (Integer.parseInt((String) jsonRequest.get("seen")) == 1);

                    //this crashes due to being a null value in the DB
//                    boolean response = (Integer.parseInt((String) jsonRequest.get("response")) == 1);

                    request newRequest = new request(id, senderID, recipientID, groupID, message, seen);
                    requestList.add(newRequest);
                }

                //initialise arrayList to store other users
                ArrayList<otherUser> userList = new ArrayList<otherUser>();

                //fill arraylist with requests
                for (int i = 0; i < jsonArr2.length(); i++) {

                    //get each json user in turn
                    JSONObject jsonUser = jsonArr2.getJSONObject(i);

                    //grab JSON data for this user
                    int id = Integer.parseInt((String) jsonUser.get("id"));
                    String username = (String) jsonUser.get("username");
                    String location  = (String) jsonUser.get("location");
                    String bio  = (String) jsonUser.get("bio");
                    String interests = (String) jsonUser.get("interests");

                    otherUser newOtherUser = new otherUser(id, username, location, bio, interests);
                    userList.add(newOtherUser);
                }

                //initialise recyclerView, adapter and LLManager and pass requestList
                recyclerView = getActivity().findViewById(R.id.group_requests_recyclerView);
                requestRecyclerViewAdapter adapter = new requestRecyclerViewAdapter(requestList, userList, getContext());
                LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(adapter);

            }


        } catch (JSONException e) {
//            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        groupRequestsViewModel = new ViewModelProvider(this).get(GroupRequestsViewModel.class);
        // TODO: Use the ViewModel
    }

}