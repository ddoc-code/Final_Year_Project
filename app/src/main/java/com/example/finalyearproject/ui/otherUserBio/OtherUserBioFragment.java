package com.example.finalyearproject.ui.otherUserBio;

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
import android.widget.TextView;

import com.example.finalyearproject.R;
import com.example.finalyearproject.databinding.FragmentOtherUserBioBinding;
import com.example.finalyearproject.otherUser;

public class OtherUserBioFragment extends Fragment {

    private OtherUserBioViewModel otherUserBioViewModel;
    private FragmentOtherUserBioBinding binding;

    public static OtherUserBioFragment newInstance() {
        return new OtherUserBioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //prevents fragments from displaying on top of each other
        if (container != null) {
            container.removeAllViews();
        }

        otherUserBioViewModel = new ViewModelProvider(this).get(OtherUserBioViewModel.class);

        binding = FragmentOtherUserBioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Intent intent = getActivity().getIntent();
        otherUser user = (otherUser) intent.getSerializableExtra("otherUser");

        //set title bar text
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Bio");

        TextView userBioHeader = binding.otherUserBioHeader;
        TextView userBioText = binding.otherUserBioText;

        //set text to otherUsers bio
        otherUserBioViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                userBioHeader.setText(user.getUsername() + "'s bio:");
                userBioText.setText(user.getBio());
            }
        });

        //button to go back
        Button button = binding.otherUserBioButton;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity) getContext()).getSupportFragmentManager().popBackStackImmediate(); //return to previous fragment
            }
        });

//        return inflater.inflate(R.layout.fragment_other_user_bio, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        otherUserBioViewModel = new ViewModelProvider(this).get(OtherUserBioViewModel.class);
        // TODO: Use the ViewModel
    }

}