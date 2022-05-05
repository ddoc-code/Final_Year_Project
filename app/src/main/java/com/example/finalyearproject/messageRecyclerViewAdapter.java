package com.example.finalyearproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class messageRecyclerViewAdapter extends RecyclerView.Adapter<messageRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<message> messageArrayList;
    private Context context;
    private currentUser cu;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView messageSender, messageText;
        public LinearLayout background;

        public MyViewHolder(View view) {
            super(view);
            messageSender = view.findViewById(R.id.message_sender);
            messageText = view.findViewById(R.id.message_text);
            background = view.findViewById(R.id.message_background);
        }
    }

    //constructor
    public messageRecyclerViewAdapter(ArrayList<message> arrayListMessage, Context context) {
        this.messageArrayList = arrayListMessage;
        this.context = context;

        Intent intent = ((Activity) context).getIntent();
        cu = (currentUser) intent.getSerializableExtra("current_user");
    }

    //inflate the view from XML (recyclerview_message_insidegroup)
    @NonNull
    @Override
    public messageRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_message_insidegroup, parent, false);
        return new MyViewHolder(view);
    }

    //bind the message data to each view
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        message message = messageArrayList.get(position);
        holder.messageSender.setText(message.getUser());
        holder.messageText.setText(message.getText());


        //outgoing message colours
        if (message.getUserID() == cu.getId()) {
            holder.background.setBackgroundResource(R.drawable.outgoing_message);
            holder.messageSender.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.messageText.setTextColor(ContextCompat.getColor(context, R.color.white));
        }
        //incoming message colours
        else {
            holder.background.setBackgroundResource(R.drawable.incoming_message);
            holder.messageSender.setGravity(Gravity.LEFT);
            holder.messageText.setGravity(Gravity.LEFT);
        }
    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }
}
