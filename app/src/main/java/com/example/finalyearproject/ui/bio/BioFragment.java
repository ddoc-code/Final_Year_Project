package com.example.finalyearproject.ui.bio;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.EditText;

import com.example.finalyearproject.AsyncResponse;
import com.example.finalyearproject.R;
import com.example.finalyearproject.currentUser;
import com.example.finalyearproject.databinding.FragmentBioBinding;
import com.example.finalyearproject.updateBioProcess;

public class BioFragment extends Fragment implements AsyncResponse {

    private BioViewModel bioViewModel;
    private FragmentBioBinding binding;

    public static BioFragment newInstance() {
        return new BioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //prevents fragments from displaying on top of each other
        if (container != null) {
            container.removeAllViews();
        }

        bioViewModel = new ViewModelProvider(this).get(BioViewModel.class);

        binding = FragmentBioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //extract currentUser data from intent
        Intent intent = getActivity().getIntent();
        currentUser cu = (currentUser) intent.getSerializableExtra("current_user");

        //set title bar text
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("My Bio");

        EditText bioText = binding.bioText;

        //set text to currentUsers bio
        bioViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                bioText.setText(cu.getBio());
            }
        });

        //button to update bio
        //setting onClickListener programmatically, otherwise this would have to go in HomeActivity.java
        Button button = root.findViewById(R.id.bio_button_updateBio);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when clicked, update bio in local currentUser first
                String newBio = bioText.getText().toString();
                cu.setBio(newBio);

                //get info to update database
                int userID = cu.getId();
                String updatedBio = cu.getBio();

                //call updateBio function to update database
                updateBio(userID, updatedBio);
            }
        });

//        return inflater.inflate(R.layout.fragment_bio, container, false);
        return root; //this allowed the binding to work properly
    }

    //this had to be in a separate function to allow the AsyncResponse to function correctly
    public void updateBio(int userID, String updatedBio) {
        //create updateBioProcess
        updateBioProcess ubp = new updateBioProcess();
        //connect ubp with this activity using AsyncResponse interface
        ubp.delegate = this;
        //execute AsyncTask with id/bio parameters
        ubp.execute(String.valueOf(userID), updatedBio);
    }

    @Override
    public void processFinish(String output) {
//        System.out.println("BF: " + output);
        //do I want to parse the JSON response here?
    }

    //this was automatically added by AS when creating this fragment. Don't think it's needed.
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        bioViewModel = new ViewModelProvider(this).get(BioViewModel.class);
//        // TODO: Use the ViewModel
//    }

}