package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateUserActivity extends AppCompatActivity implements AsyncResponse {

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        spinner = (Spinner) findViewById(R.id.create_user_locationInput);
        String[] locations = new String[]{"New Cross"};

        //create and set adapter to fill spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, locations);
        spinner.setAdapter(adapter);
    }

    public void CreateAccountButton(View view) {

        //retrieve user input
        EditText usernameInput = findViewById(R.id.create_user_usernameInput);
        EditText passwordInput = findViewById(R.id.create_user_passwordInput);
        EditText emailInput = findViewById(R.id.create_user_emailInput);

        //format strings to pass as parameters to CreateUserProcess
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        String email = emailInput.getText().toString();
        String location = spinner.getSelectedItem().toString();

        if (username.length() < 1 || password.length() < 1 || email.length() < 1 || location.length() < 1) {

            //inform user if they have left a field blank
            TextView info = findViewById(R.id.create_user_info);
            info.setText("Please ensure you have filled out all fields!");
        }
        else {
            //call create account function
            createAccount(username, password, email, location);
        }
    }

    public void createAccount(String username, String password, String email, String location) {

        //create createUserProcess
        createUserProcess cup = new createUserProcess();
        //connect cup with this activity using AsyncResponse interface
        cup.delegate = this;
        //execute AsyncTask with 4 parameters
        cup.execute(username, password, email, location);
    }

    @Override
    public void processFinish(String output) {
//        System.out.println("CUA: " + output);

        //return to previous activity after account created
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}