package com.example.finalyearproject.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalyearproject.MainActivity;
import com.example.finalyearproject.R;
import com.example.finalyearproject.currentUser;
import com.example.finalyearproject.databinding.FragmentBioBinding;
import com.example.finalyearproject.databinding.FragmentProfileBinding;
import com.example.finalyearproject.ui.bio.BioFragment;
import com.example.finalyearproject.ui.myGroups.MyGroupsFragment;

import java.util.Arrays;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
//    private FragmentNotificationsBinding binding; //old name for this fragment
    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //prevents fragments from displaying on top of each other
        if (container != null) {
            container.removeAllViews();
        }

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //extract currentUser data from intent
        Intent intent = getActivity().getIntent();
        currentUser cu = (currentUser) intent.getSerializableExtra("current_user");
//        System.out.println(Arrays.toString(cu.getInterestsArr()));

        //set title bar text
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Profile");

//        final TextView textView = binding.textProfile;
        TextView textView = binding.profileUsername;
        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(cu.getUsername());
            }
        });

        //set onClickListener for four buttons
        Button button = binding.profileButtonMyBio;
        button.setOnClickListener(onClickListener);

        Button button2 = binding.profileButtonMyGroups;
        button2.setOnClickListener(onClickListener);

        Button button3 = binding.profileButtonMyInterests;
        button3.setOnClickListener(onClickListener);

        Button button4 = binding.profileButtonSignOut;
        button4.setOnClickListener(onClickListener);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //onClickListener for all buttons, calls chosen fragment function
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.profile_button_myBio:
                    enterBio();
                    break;
                case R.id.profile_button_myGroups:
                    enterMyGroups();
                    break;
                case R.id.profile_button_myInterests:
                    System.out.println("MY INTERESTS");
                    break;
                case R.id.profile_button_signOut:
                    signOut();
                    break;
            }
        }
    };

    //enter Bio fragment when My Bio button is clicked
    public void enterBio() {

        //this block works but does not display the bottom nav bar
//        FragmentBioBinding binding2 = FragmentBioBinding.inflate(getLayoutInflater());
//        setContentView(binding2.getRoot());

        //this works, however I had to add if statements to all fragments to prevent overlapping displays
        Fragment bioFragment = new BioFragment();
        FragmentTransaction transaction = ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_activity_home, bioFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //navigate to MyGroupsFragment when My Groups button is clicked
    public void enterMyGroups() {

        Fragment myGroupsFragment = new MyGroupsFragment();
        FragmentTransaction transaction = ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_activity_home, myGroupsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //implement my interests page here

    //implement sign out button (returns to the main activity/login screen)
    public void signOut() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); //this clears the backstack so the user cannot press 'back' to log back in
        startActivity(intent);
    }

}