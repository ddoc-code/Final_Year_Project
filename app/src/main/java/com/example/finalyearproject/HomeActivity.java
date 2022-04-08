package com.example.finalyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.finalyearproject.databinding.FragmentBioBinding;
import com.example.finalyearproject.databinding.FragmentProfileBinding;
import com.example.finalyearproject.ui.bio.BioFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.finalyearproject.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private currentUser cu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //extract currentUser data from intent
        Intent intent = getIntent();
        cu = (currentUser) intent.getSerializableExtra("current_user");
//        getIntent().putExtra("current_user", cu); //i think this can be deleted. comment out for now and delete if nothing breaks.

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    //My Bio button on profile fragment
    public void enterBio(View view) {

        //this block works but does not display the bottom nav bar
//        FragmentBioBinding binding2 = FragmentBioBinding.inflate(getLayoutInflater());
//        setContentView(binding2.getRoot());

        //this works, however I had to add if statements to all fragments to prevent overlapping displays
        Fragment bioFragment = new BioFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_activity_home, bioFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //Called when clicking an event card on home fragment
    public void testFunc(View view) {
        System.out.println("EVENT CLICKED!");
    }
}