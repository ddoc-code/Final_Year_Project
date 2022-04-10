package com.example.finalyearproject;

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

    //define my viewHolder class
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.cardEventTitle);
            description = view.findViewById(R.id.cardEventDesc);
        }
    }

    //constructor
    public eventRecyclerViewAdapter(ArrayList<event> arrayListEvent) {
        this.eventArrayList = arrayListEvent;
    }

    //inflate the card layout from XML (recyclerview_card)
    @NonNull
    @Override
    public eventRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_card, parent, false);
        return new MyViewHolder(view);
    }

    //bind the data from the event arraylist to each cardView
    @Override
    public void onBindViewHolder(@NonNull eventRecyclerViewAdapter.MyViewHolder holder, int position) {
        event event = eventArrayList.get(position);
        holder.title.setText(event.getTitle());
        holder.description.setText(event.getDescription());

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
            System.out.println("clicked: " + clickedPosition);

            //enter EventDetail fragment when an event card is clicked
            Fragment eventDetailFragment = new EventDetailFragment();
            FragmentTransaction transaction = ((AppCompatActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment_activity_home, eventDetailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    };
}
