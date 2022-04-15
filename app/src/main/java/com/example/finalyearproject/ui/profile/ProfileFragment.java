package com.example.finalyearproject.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalyearproject.currentUser;
import com.example.finalyearproject.databinding.FragmentBioBinding;
import com.example.finalyearproject.databinding.FragmentProfileBinding;

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

        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

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
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
            public void onChanged(@Nullable String s) {
                textView.setText(cu.getUsername());
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}