package com.example.finalyearproject.ui.insideGroup;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.finalyearproject.AsyncResponse;
import com.example.finalyearproject.R;
import com.example.finalyearproject.createGroupProcess;
import com.example.finalyearproject.createMessageProcess;
import com.example.finalyearproject.currentUser;
import com.example.finalyearproject.databinding.FragmentGroupRequestsBinding;
import com.example.finalyearproject.databinding.FragmentInsideGroupBinding;
import com.example.finalyearproject.event;
import com.example.finalyearproject.group;
import com.example.finalyearproject.message;
import com.example.finalyearproject.messageRecyclerViewAdapter;
import com.example.finalyearproject.retrieveMessagesProcess;
import com.example.finalyearproject.retrieveMyGroupsProcess;
import com.example.finalyearproject.ui.groupRequests.GroupRequestsFragment;
import com.example.finalyearproject.venue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InsideGroupFragment extends Fragment implements AsyncResponse {

    private InsideGroupViewModel insideGroupViewModel;
    private FragmentInsideGroupBinding binding;
    private RecyclerView recyclerView;

    private currentUser cu;
    private event event;
    private venue venue;
    private group group;
    private java.util.Timer timer;

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

        insideGroupViewModel = new ViewModelProvider(this).get(InsideGroupViewModel.class);

        binding = FragmentInsideGroupBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //extract currentUser/event/venue/group data from intent
        Intent intent = getActivity().getIntent();
        cu = (currentUser) intent.getSerializableExtra("current_user");
        event = (event) intent.getSerializableExtra("event");
        venue = (venue) intent.getSerializableExtra("venue");
        group = (group) intent.getSerializableExtra("group");

        //set title bar text
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(venue.getName());

        //set group information text
        insideGroupViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.insideGroupTitle.setText(group.getTitle());
                binding.insideGroupAttendees.setText("Attendees: " + group.getCurrentPeople() + "/" + group.getMaxPeople());
                binding.insideGroupDescription.setText(group.getDescription());
            }
        });

        //if current user is the group creator, display the View Requests button
        if (group.getCreatorID() == cu.getId()) {
            Button button = binding.insideGroupViewRequestsButton;
            button.setVisibility(View.VISIBLE);
        }

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

        //retrieve messages
        retrieveMessages(String.valueOf(group.getId()));

        System.out.println("START TIMER");
        //create TimerTask to retrieve messages regularly
        java.util.TimerTask timerTask = new java.util.TimerTask() {
            @Override
            public void run() {

                //if statement checks if the recyclerView exists (if not we have moved to a different fragment)
                if (getActivity().findViewById(R.id.inside_group_recyclerView) == null) {
                    System.out.println("CANCEL TIMER");
                    timer.cancel(); //cancel the timer
                }
                else {
//                    System.out.println("EXECUTE TIMERTASK");
                    retrieveMessages(String.valueOf(group.getId()));
                }
            }
        };

        //start new timer
        timer = new java.util.Timer(true);
        timer.schedule(timerTask, 2000, 2000);

        //set onClickListener for View Join Requests button
        Button button = binding.insideGroupViewRequestsButton;
        button.setOnClickListener(onClickListener);

        //set onClickListener for Send Message button
        Button button2 = binding.insideGroupSendMessageButton;
        button2.setOnClickListener(onClickListener);

//        return inflater.inflate(R.layout.fragment_inside_group, container, false);
        return root;
    }

    @Override
    public void processFinish(String output) {
//        System.out.println("IGF: " + output);

        try {
            JSONObject json = new JSONObject(output);

            //check messages were successfully retrieved
            if ((Integer) json.get("success") == 1) {

                //get value of "messages" key
                JSONArray jsonArr = (JSONArray) json.get("messages");

                //initialise arraylist to store groups
                ArrayList<message> messageList = new ArrayList<message>();

                //fill arraylist with messages
                for (int i = 0; i < jsonArr.length(); i++) {

                    //get each json message in turn
                    JSONObject jsonMessage = jsonArr.getJSONObject(i);

                    //grab JSON data for this message
                    int id = Integer.parseInt((String) jsonMessage.get("id"));
                    int groupID = Integer.parseInt((String) jsonMessage.get("groupID"));
                    int userID = Integer.parseInt((String) jsonMessage.get("userID"));
                    String user = (String) jsonMessage.get("user");
                    String text = (String) jsonMessage.get("text");
                    Boolean deleted = (Integer.parseInt((String) jsonMessage.get("deleted")) == 1);

                    message newMessage = new message(id,groupID, userID, user, text, deleted);
                    messageList.add(newMessage);
                }

                //initialise recyclerView, adapter and LLManager and pass messageList
                recyclerView = getActivity().findViewById(R.id.inside_group_recyclerView);
                messageRecyclerViewAdapter adapter = new messageRecyclerViewAdapter(messageList, getContext());
                LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                if (getActivity().findViewById(R.id.inside_group_recyclerView) != null) { //extra security against possible crash when changing fragments
                    recyclerView.setLayoutManager(llm);
                    recyclerView.setAdapter(adapter);
                }
            }

        } catch (JSONException e) {
//            e.printStackTrace();
        }
    }

    //retrieve messages from database
    public void retrieveMessages(String groupID){

        //create retrieveMessagesProcess
        retrieveMessagesProcess rmp = new retrieveMessagesProcess();
        //connect rmp with this activity using AsyncResponse interface
        rmp.delegate = this;
        //execute AsyncTask with groupID
        rmp.execute(groupID);
    }

    //click listener for Send Message button
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.inside_group_viewRequests_button:
                    enterGroupRequests(); //button only visible for group creator
                    break;

                case R.id.inside_group_sendMessage_button:
                    //get user input
                    EditText messageInput = getActivity().findViewById(R.id.inside_group_textInput);

                    //format strings to pass as parameters to createMessageProcess
                    String groupID = String.valueOf(group.getId());
                    String userID = String.valueOf(cu.getId());
                    String user = cu.getUsername();
                    String text = messageInput.getText().toString();
                    String deleted = String.valueOf(0);

                    //do not send message if box is empty
                    if (text.length() > 0) {

                        //call create message function
                        createMessage(groupID, userID, user, text, deleted);
                        //clear the input box
                        messageInput.getText().clear();
                    }
                    break;
            }
        }
    };

    //this has to be in a separate function to allow the AsyncResponse to function correctly
    public void createMessage(String groupID, String userID, String user, String text, String deleted) {

        //create createMessageProcess
        createMessageProcess cmp = new createMessageProcess();
        //connect cmp with this activity using AsyncResponse interface
        cmp.delegate = this;
        //execute AsyncTask with relevant parameters
        cmp.execute(groupID, userID, user, text, deleted);
    }

    public void enterGroupRequests() {

        Fragment groupRequestsFragment = new GroupRequestsFragment();
        FragmentTransaction transaction = ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_activity_home, groupRequestsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        insideGroupViewModel = new ViewModelProvider(this).get(InsideGroupViewModel.class);
        // TODO: Use the ViewModel
    }

}