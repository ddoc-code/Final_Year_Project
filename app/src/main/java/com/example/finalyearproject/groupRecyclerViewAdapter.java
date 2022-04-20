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

import com.example.finalyearproject.ui.groupDetail.GroupDetailFragment;

import java.util.ArrayList;

public class groupRecyclerViewAdapter extends RecyclerView.Adapter<groupRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<group> groupArrayList;
    private Context context;

    //define my viewHolder class
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, attendees, description;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.card_group_title);
            attendees = view.findViewById(R.id.card_group_attendees);
            description = view.findViewById(R.id.card_group_desc);
        }
    }

    //constructor
    public groupRecyclerViewAdapter(ArrayList<group> arrayListGroup, Context context) {
        this.groupArrayList = arrayListGroup;
        this.context = context;
    }

    //inflate the card layout from XML (recyclerview_card_group)
    @NonNull
    @Override
    public groupRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_card_group,parent,false);
        return new MyViewHolder(view);
    }

    //bind the data from the group arraylist to each cardView
    @Override
    public void onBindViewHolder(@NonNull groupRecyclerViewAdapter.MyViewHolder holder, int position) {
        group group = groupArrayList.get(position);
        holder.title.setText(group.getTitle());
        holder.attendees.setText("Attendees: " + group.getCurrentPeople() + "/" + group.getMaxPeople());
        holder.description.setText(group.getDescription());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(groupClickListener);
    }

    //return the number of groups to be displayed
    @Override
    public int getItemCount() {
        return groupArrayList.size();
    }

    private View.OnClickListener groupClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int clickedPosition = (int) v.getTag();

            Intent intent = ((Activity) context).getIntent();
            intent.putExtra("group", groupArrayList.get(clickedPosition)); //Serializable interface allows this

            //enter GroupDetail fragment when a group card is clicked
            Fragment groupDetailFragment = new GroupDetailFragment();
            FragmentTransaction transaction = ((AppCompatActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment_activity_home, groupDetailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    };
}
