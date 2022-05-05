package com.example.finalyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.finalyearproject.databinding.FragmentBioBinding;
import com.example.finalyearproject.databinding.FragmentProfileBinding;
import com.example.finalyearproject.ui.bio.BioFragment;
import com.example.finalyearproject.ui.eventDetail.EventDetailFragment;
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

public class HomeActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

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

        //BACKSTACK STUFF
        //Listen for changes in the back stack
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        //Handle when activity is recreated like on orientation Change
        shouldDisplayHomeUp();

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

    //These functions implement the back button on the action bar after navigating to a fragment
    @Override
    public void onBackStackChanged() {
        System.out.println("BACKSTACK COUNT: " + getSupportFragmentManager().getBackStackEntryCount());
        shouldDisplayHomeUp();
    }

    public void shouldDisplayHomeUp(){
        //Enable Up button only  if there are entries in the back stack
        boolean canGoBack = getSupportFragmentManager().getBackStackEntryCount()>0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canGoBack);
    }

    @Override
    public boolean onSupportNavigateUp() {
        //This method is called when the up button is pressed. Just the pop back stack.
        getSupportFragmentManager().popBackStack();
        return true;
    }
    
}