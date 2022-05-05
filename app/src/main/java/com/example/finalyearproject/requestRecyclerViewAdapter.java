package com.example.finalyearproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.ui.otherUserBio.OtherUserBioFragment;

import java.util.ArrayList;

public class requestRecyclerViewAdapter extends RecyclerView.Adapter<requestRecyclerViewAdapter.MyViewHolder> implements AsyncResponse {

    private ArrayList<request> requestArrayList;
    private ArrayList<otherUser> userArrayList;
    private Context context;

    //define my viewHolder class
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username, message, prompt, newIndicator;
        public Button acceptButton, declineButton;

        public MyViewHolder(View view) {
            super(view);
            username = view.findViewById(R.id.card_request_username);
            message = view.findViewById(R.id.card_request_message);
            prompt = view.findViewById(R.id.card_request_prompt);
            newIndicator = view.findViewById(R.id.card_request_newIndicator);

            acceptButton = view.findViewById(R.id.card_request_acceptButton);
            declineButton = view.findViewById(R.id.card_request_declineButton);
        }
    }

    //constructor
    public requestRecyclerViewAdapter(ArrayList<request> arrayListRequest, ArrayList<otherUser> arrayListUser, Context context) {
        this.requestArrayList = arrayListRequest;
        this.userArrayList = arrayListUser;
        this.context = context;
    }

    //inflate the card layout from XML (recyclerview_card_request)
    @NonNull
    @Override
    public requestRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_card_request, parent, false);
        return new MyViewHolder(view);
    }

    //bind the data from the request / user arraylist to each cardView
    @Override
    public void onBindViewHolder(@NonNull requestRecyclerViewAdapter.MyViewHolder holder, int position) {
        request request = requestArrayList.get(position);
        otherUser otherUser = userArrayList.get(position);

        holder.username.setText(otherUser.getUsername());
        holder.message.setText(request.getMessage());

        if (!request.isSeen()) {
            holder.newIndicator.setVisibility(View.VISIBLE);
        }

        holder.acceptButton.setTag(position);
        holder.acceptButton.setOnClickListener(onClickListener);

        holder.declineButton.setTag(position);
        holder.declineButton.setOnClickListener(onClickListener);

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(onClickListener);
    }

    //return the number of requests to be displayed
    @Override
    public int getItemCount() {
        return requestArrayList.size();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int clickedPosition = (int) v.getTag();
            System.out.println("TAG: " + clickedPosition);

            switch (v.getId()) {
                case R.id.card_request_acceptButton:
                    //call acceptRequest function
                    acceptRequest(clickedPosition);

                    //remove this request from arrayList (and equivalent user)
                    requestArrayList.remove(clickedPosition);
                    userArrayList.remove(clickedPosition);
                    notifyItemRemoved(clickedPosition);
                    notifyItemRangeChanged(clickedPosition, requestArrayList.size());
                    break;

                case R.id.card_request_declineButton:
                    //call declineRequest function
                    declineRequest(clickedPosition);

                    //remove this request from arrayList (and equivalent user)
                    requestArrayList.remove(clickedPosition);
                    userArrayList.remove(clickedPosition);
                    notifyItemRemoved(clickedPosition);
                    notifyItemRangeChanged(clickedPosition, requestArrayList.size());
                    break;
                    
                default:
                    //display otherUserBio fragment
                    viewUserBio(clickedPosition, context);
            }
        }
    };

    public void acceptRequest(int position) {

        //get id for the request
        int id = requestArrayList.get(position).getId();
        int senderID = requestArrayList.get(position).getSenderID();

        //create requestAcceptProcess
        requestAcceptProcess rap = new requestAcceptProcess();
        //connect rap with this activity using AsyncResponse interface
        rap.delegate = this;
        //execute AsyncTask with requestID and senderID
        rap.execute(String.valueOf(id), String.valueOf(senderID));
    }

    //call requestDeclineProcess when decline button is clicked
    public void declineRequest(int position) {

        //get id for this request
        int id = requestArrayList.get(position).getId();

        //create requestDeclineProcess
        requestDeclineProcess rdp = new requestDeclineProcess();
        //connect rdp with this activity using AsyncResponse interface
        rdp.delegate = this;
        //execute AsyncTask with requestID
        rdp.execute(String.valueOf(id));
    }

    //display otherUserBio fragment when a card is clicked
    public void viewUserBio(int position, Context context) {
        Intent intent = ((Activity) context).getIntent();
        intent.putExtra("otherUser", userArrayList.get(position)); //Serializable interface allows this

        Fragment otherUserBioFragment = new OtherUserBioFragment();
        FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_activity_home, otherUserBioFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void processFinish(String output) {
        System.out.println("RRVA: " + output);
    }
}