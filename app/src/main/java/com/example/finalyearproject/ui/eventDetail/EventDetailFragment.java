package com.example.finalyearproject.ui.eventDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.finalyearproject.R;
import com.example.finalyearproject.currentUser;
import com.example.finalyearproject.databinding.FragmentEventDetailBinding;
import com.example.finalyearproject.event;
import com.example.finalyearproject.ui.eventGroups.EventGroupsFragment;
import com.example.finalyearproject.venue;

public class EventDetailFragment extends Fragment {

    private EventDetailViewModel eventDetailViewModel;
    private FragmentEventDetailBinding binding;

    public static EventDetailFragment newInstance() {
        return new EventDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //prevents fragments from displaying on top of each other
        if (container != null) {
            container.removeAllViews();
        }

        eventDetailViewModel = new ViewModelProvider(this).get(EventDetailViewModel.class);

        binding = FragmentEventDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //extract currentUser data from intent
        Intent intent = getActivity().getIntent();
        currentUser cu = (currentUser) intent.getSerializableExtra("current_user");
        event event = (event) intent.getSerializableExtra("event");
        venue venue = (venue) intent.getSerializableExtra("venue");
//        System.out.println(event.getTitle());
//        System.out.println(venue.getName());

        //set title bar text
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(venue.getName());

        //set text for selected event
        eventDetailViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.eventDetailVenue.setText(venue.getName());
                binding.eventDetailTitle.setText(event.getTitle());
                binding.eventDetailDesc.setText(event.getDescription());

                binding.eventDetailDate.setText("Date: " + event.getDate());
                binding.eventDetailTime.setText("Time: " + event.getTime());

                binding.eventDetailTicketsNeeded.setText("Tickets needed: " + DisplayBoolean(event.getTicketsNeeded()));
                binding.eventDetailTicketPrice.setText("Ticket price: " + event.getTicketPrice());
                binding.eventDetailTicketsPage.setText("Info & Tickets: " + event.getTicketsPage());

                binding.eventDetailVenue2.setText(venue.getName());
                binding.eventDetailAddress.setText(venue.getAddress());
            }
        });

        //set click listener for button
        Button button = binding.eventDetailButton;
        button.setOnClickListener(onClickListener);

//        return inflater.inflate(R.layout.fragment_event_detail, container, false);
        return root;
    }

    //Display Yes/No on page instead of true/false
    public String DisplayBoolean(Boolean bool) {
        if (bool) {return "Yes";}
        else {return "No";}
    }

    //click listener for View Groups button
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //move to eventGroupsFragment
            Fragment eventGroupsFragment = new EventGroupsFragment();
            FragmentTransaction transaction = ((AppCompatActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment_activity_home, eventGroupsFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        eventDetailViewModel = new ViewModelProvider(this).get(EventDetailViewModel.class);
        // TODO: Use the ViewModel
    }
}