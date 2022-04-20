package com.example.finalyearproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.ui.insideGroup.InsideGroupFragment;

import java.util.ArrayList;

public class myGroupRecyclerViewAdapter extends RecyclerView.Adapter<myGroupRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<group> groupArrayList;
    private ArrayList<event> eventArrayList;
    private ArrayList<venue> venueArrayList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView eventVenue, eventTitle, groupTitle, groupAttendees;

        public MyViewHolder(View view) {
            super(view);
            eventVenue = view.findViewById(R.id.card_myGroup_eventVenue);
            eventTitle = view.findViewById(R.id.card_myGroup_eventTitle);
            groupTitle = view.findViewById(R.id.card_myGroup_title);
            groupAttendees = view.findViewById(R.id.card_myGroup_attendees);
        }
    }

    //constructor
    public myGroupRecyclerViewAdapter(ArrayList<group> arrayListGroup, ArrayList<event> arrayListEvent, ArrayList<venue> arrayListVenue, Context context) {
        this.groupArrayList = arrayListGroup;
        this.eventArrayList = arrayListEvent;
        this.venueArrayList = arrayListVenue;
        this.context = context;
    }

    //inflate the card layout from XML (recyclerview_card_mygroup)
    @NonNull
    @Override
    public myGroupRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_card_mygroup, parent, false);
        return new MyViewHolder(view);
    }

    //bind the data from the group/venue arraylists to each cardView
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        venue venue = venueArrayList.get(position);
        holder.eventVenue.setText(venue.getName());

        event event = eventArrayList.get(position);
        holder.eventTitle.setText(event.getTitle());

        group group = groupArrayList.get(position);
        holder.groupTitle.setText(group.getTitle());
        holder.groupAttendees.setText("Attendees: " + group.getCurrentPeople() + "/" + group.getMaxPeople());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return groupArrayList.size();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int clickedPosition = (int) v.getTag();

            Intent intent = ((Activity) context).getIntent();
            intent.putExtra("group", groupArrayList.get(clickedPosition)); //Serializable interface allows this
            intent.putExtra("event", eventArrayList.get(clickedPosition)); //Serializable interface allows this
            intent.putExtra("venue", venueArrayList.get(clickedPosition)); //Serializable interface allows this

            //enter InsideGroup fragment fragment when a group card is clicked
            Fragment insideGroupFragment = new InsideGroupFragment();
            FragmentTransaction transaction = ((AppCompatActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment_activity_home, insideGroupFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    };

}
