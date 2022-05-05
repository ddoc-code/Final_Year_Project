package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    //stores current user details
    currentUser cu = new currentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //button to login - calls loginProcess (AsyncTask child class)
    public void enterApp(View view) {

        //get user input and convert to string
        EditText userInput = findViewById(R.id.usernameInput);
        EditText passInput = findViewById(R.id.passwordInput);
        String user = userInput.getText().toString();
        String pass = passInput.getText().toString();

        //create loginProcess
        loginProcess lp = new loginProcess();
        //connect lp with this activity using AsyncResponse interface
        lp.delegate = this;
        //execute AsyncTask with username/password parameters
        lp.execute(user, pass);
    }

    //response from AsyncTask via AsyncResponse interface
    @Override
    public void processFinish(String output) {

        //parse JSON response to determine login success
        try {
            //outer JSON object
            JSONObject json = new JSONObject(output);
//            System.out.println("json: " + json);
//            System.out.println("success: " + json.get("success"));

            //JSON array containing inner JSON objects
            JSONArray jsonArr = (JSONArray) json.get("user");
//            System.out.println("json array: " + jsonArr);

            //inner JSON object
            JSONObject jsonChild = jsonArr.getJSONObject(0);
            int ID = Integer.parseInt((String) jsonChild.get("id"));
            String username = (String) jsonChild.get("username");
            String password = (String) jsonChild.get("password");
            String email = (String) jsonChild.get("email");
            String location = (String) jsonChild.get("location");
            String bio = (String) jsonChild.get("bio");
            String interests = (String) jsonChild.get("interests");

            //check if login was successful
            if ((Integer) json.get("success") == 1) {
                System.out.println("LOGIN SUCCESS");

                cu.setUserInfo(ID, username, password, email, location, bio, interests);

                //start next activity (HomeActivity)
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("current_user", cu); //Serializable interface allows this
                startActivity(intent);
            }

        //catch block is reached when login is unsuccessful
        } catch (JSONException e) {
//            e.printStackTrace();
            System.out.println("LOGIN FAILURE");

            TextView info = findViewById(R.id.loginInfo);
            info.setText("Login failed. Please try again!");
        }
    }
}