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

import com.example.finalyearproject.ui.eventDetail.EventDetailFragment;

import java.util.ArrayList;

public class eventRecyclerViewAdapter extends RecyclerView.Adapter<eventRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<event> eventArrayList;
    private ArrayList<venue> venueArrayList;
    private Context context;

    //define my viewHolder class
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, venue;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.card_event_title);
            description = view.findViewById(R.id.card_event_desc);
            venue = view.findViewById(R.id.card_event_venue);
        }
    }

    //constructor
    public eventRecyclerViewAdapter(ArrayList<event> arrayListEvent, ArrayList<venue> arrayListVenue, Context context) {
        this.eventArrayList = arrayListEvent;
        this.venueArrayList = arrayListVenue;
        this.context = context;
    }

    //inflate the card layout from XML (recyclerview_card_event)
    @NonNull
    @Override
    public eventRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_card_event, parent, false);
        return new MyViewHolder(view);
    }

    //bind the data from the event arraylist to each cardView
    @Override
    public void onBindViewHolder(@NonNull eventRecyclerViewAdapter.MyViewHolder holder, int position) {
        event event = eventArrayList.get(position);
        holder.title.setText(event.getTitle());
        holder.description.setText(event.getDescription());

        venue venue = venueArrayList.get(position);
        holder.venue.setText(venue.getName());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(eventClickListener);
    }

    //return the number of events to be displayed
    @Override
    public int getItemCount() {
        return eventArrayList.size();
    }

    private View.OnClickListener eventClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int clickedPosition = (int) v.getTag();
//            System.out.println("clicked: " + clickedPosition);

            Intent intent = ((Activity) context).getIntent();
            intent.putExtra("event", eventArrayList.get(clickedPosition)); //Serializable interface allows this
            intent.putExtra("venue", venueArrayList.get(clickedPosition)); //Serializable interface allows this

            //enter EventDetail fragment when an event card is clicked
            Fragment eventDetailFragment = new EventDetailFragment();
            FragmentTransaction transaction = ((AppCompatActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment_activity_home, eventDetailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    };
}
