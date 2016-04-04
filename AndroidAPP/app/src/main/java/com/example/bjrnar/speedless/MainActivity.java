package com.example.bjrnar.speedless;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seek_bar;
    public Boolean isLoggedIn = true;
    public Boolean switchedOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        greyOut(isLoggedIn, switchedOn);
    }

    public void seekBar(){
        seek_bar = (SeekBar) findViewById(R.id.seekBar);

        seek_bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener(){
                    int light_value;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                        light_value = progress;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar){

                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar){
                    }
                }
        );



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // Actions, ex button clicks

    public void changeSwitchText(View view) {
        Button b = (Button) findViewById(R.id.button1);
        String ButtonText = b.getText().toString();
        if(ButtonText.equals("Switch ON")){
            ((TextView)findViewById(R.id.button1)).setText("Switch OFF");
            switchedOn = true;
            greyOut(isLoggedIn, switchedOn);
        }
        else{
            ((TextView)findViewById(R.id.button1)).setText("Switch ON");
            switchedOn = false;
            greyOut(isLoggedIn, switchedOn);
        }
    }

    public void openLoginActivity (View view){

        Intent intentL = new Intent(this, LoginScreenActivity.class);

        startActivity(intentL);
    }

    public void setIsLoggedIn(Boolean value){
        isLoggedIn = value;
    }

    public void greyOut(Boolean LoggedInValue, Boolean switchedOn){
        if(LoggedInValue&&switchedOn){
            findViewById(R.id.button2).setBackgroundColor(Color.parseColor("#b29696"));
            findViewById(R.id.button2).setClickable(true);
            ((TextView)findViewById(R.id.button2)).setTextColor(Color.BLACK);
            findViewById(R.id.button4).setBackgroundColor(Color.parseColor("#b29696"));
            findViewById(R.id.button4).setClickable(true);
            ((TextView)findViewById(R.id.button4)).setTextColor(Color.BLACK);
            findViewById(R.id.button5).setBackgroundColor(Color.parseColor("#b29696"));
            findViewById(R.id.button5).setClickable(true);
            ((TextView)findViewById(R.id.button5)).setTextColor(Color.BLACK);
            findViewById(R.id.button6).setBackgroundColor(Color.parseColor("#b29696"));
            findViewById(R.id.button6).setClickable(true);
            ((TextView)findViewById(R.id.button6)).setTextColor(Color.BLACK);
        }
        if(!LoggedInValue&&switchedOn){
            findViewById(R.id.button2).setBackgroundColor(Color.parseColor("#b29696"));
            findViewById(R.id.button2).setClickable(true);
            ((TextView)findViewById(R.id.button2)).setTextColor(Color.BLACK);
            findViewById(R.id.button4).setBackgroundColor(Color.parseColor("#f2f2f2"));
            findViewById(R.id.button4).setClickable(false);
            ((TextView)findViewById(R.id.button4)).setTextColor(Color.GRAY);
            findViewById(R.id.button5).setBackgroundColor(Color.parseColor("#f2f2f2"));
            findViewById(R.id.button5).setClickable(false);
            ((TextView)findViewById(R.id.button5)).setTextColor(Color.GRAY);
            findViewById(R.id.button6).setBackgroundColor(Color.parseColor("#f2f2f2"));
            findViewById(R.id.button6).setClickable(false);
            ((TextView)findViewById(R.id.button6)).setTextColor(Color.GRAY);
        }
        if(LoggedInValue&&!switchedOn){
            findViewById(R.id.button2).setBackgroundColor(Color.parseColor("#f2f2f2"));
            findViewById(R.id.button2).setClickable(false);
            ((TextView)findViewById(R.id.button2)).setTextColor(Color.GRAY);
            findViewById(R.id.button4).setBackgroundColor(Color.parseColor("#f2f2f2"));
            findViewById(R.id.button4).setClickable(false);
            ((TextView)findViewById(R.id.button4)).setTextColor(Color.GRAY);
            findViewById(R.id.button5).setBackgroundColor(Color.parseColor("#f2f2f2"));
            findViewById(R.id.button5).setClickable(false);
            ((TextView)findViewById(R.id.button5)).setTextColor(Color.GRAY);
            findViewById(R.id.button6).setBackgroundColor(Color.parseColor("#f2f2f2"));
            findViewById(R.id.button6).setClickable(false);
            ((TextView)findViewById(R.id.button6)).setTextColor(Color.GRAY);
        }
        if(!LoggedInValue&&!switchedOn){
            findViewById(R.id.button2).setBackgroundColor(Color.parseColor("#f2f2f2"));
            findViewById(R.id.button2).setClickable(false);
            ((TextView)findViewById(R.id.button2)).setTextColor(Color.GRAY);
            findViewById(R.id.button4).setBackgroundColor(Color.parseColor("#f2f2f2"));
            findViewById(R.id.button4).setClickable(false);
            ((TextView)findViewById(R.id.button4)).setTextColor(Color.GRAY);
            findViewById(R.id.button5).setBackgroundColor(Color.parseColor("#f2f2f2"));
            findViewById(R.id.button5).setClickable(false);
            ((TextView)findViewById(R.id.button5)).setTextColor(Color.GRAY);
            findViewById(R.id.button6).setBackgroundColor(Color.parseColor("#f2f2f2"));
            findViewById(R.id.button6).setClickable(false);
            ((TextView)findViewById(R.id.button6)).setTextColor(Color.GRAY);
        }
    }







}//MainActivity END
