package com.example.bjrnar.speedless;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class LoginScreenActivity extends AppCompatActivity {

    //fields for saving
    private static final String UsernameSave = "UsernameSave";
    private static final String PasswordSave = "UsernameSave";


    //fields for using
    private String Username;
    private String Password;

    EditText UsernameET;
    EditText PasswordET;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen); // Inflate the GUI

        // Check if app just started, or if it is being restored


        if (savedInstanceState == null){

            // Just started


            Username = ""; // defaults, should they be the same as what is in the field initially (see the content xml and the fields)
            Password = "";

        }else{

            // App is being restored
            Username = savedInstanceState.getString(UsernameSave); //Done?
            Password = savedInstanceState.getString(PasswordSave);

        }

        UsernameET = (EditText) findViewById(R.id.editTextLoginUsername);
        PasswordET = (EditText) findViewById(R.id.editTextLoginPassword);


        UsernameET.addTextChangedListener(UsernameETListener);
        PasswordET.addTextChangedListener(PasswordETListener);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // Activity lifecycle callbacks, for when we need them
    //See documentation,

    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
    }
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }

    //Activity lifecycle callbacks END




    private TextWatcher UsernameETListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        //THIS ONE
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            try {
                Username = s.toString();

            } catch (Exception e){
                Username = ""; // default
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher PasswordETListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        //THIS ONE
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            try {
                Password = s.toString();

            } catch (Exception e){
                Password = ""; // default
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    protected void onSaveInstanceState(Bundle outState){

        super.onSaveInstanceState(outState);

        outState.putString(UsernameSave,Username);
        outState.putString(PasswordSave,Password);

    }



}