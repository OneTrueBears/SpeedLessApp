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
    private static final String UsernameSave = "";
    private static final String PasswordSave = "";


    //fields for using
    private String Username;
    private String Password;

    EditText UsernameET;
    EditText PasswordET;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        if (savedInstanceState == null){

            Username = ""; // defaults, should they be the same as what is in the field initially (see the content xml and the fields)
            Password = "";

        }else{
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

    private void LoginUser(){
        //MainActivity.setIsLoggedIn();
    }

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
