package com.example.bjrnar.speedless;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    SeekBar seek_bar;
    public Boolean isLoggedIn = true;
    public Boolean switchedOn = false;

    //Bt - Temp?

   // boolean lightOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // alll these Overridden Activity lifecycle methods must call their super
        // Set the user interface layout for this Activity
        // The layout file is defined in the project res/layout/xxxxxxxxx.xml file
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Start service
        Intent intentService = new Intent(this, MyService.class);
        startService(intentService);

        greyOut(isLoggedIn, switchedOn);

    }

    public void seekBar(){
        seek_bar = (SeekBar) findViewById(R.id.seekBar);

        seek_bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int light_value;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        light_value = progress;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                }
        );



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
        //toggleLight();
        //Swap text
        Button b = (Button) findViewById(R.id.button1);
        String ButtonText = b.getText().toString();
        if(ButtonText.equals("Switch ON")){
            ((TextView) findViewById(R.id.button1)).setText("Switch OFF");
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

// Bluetooth
/*
    public void connectBluetooth() {
        try {
            bt.init();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void writeBluetooth(Double speed, int speedlimit){
        try{
            bt.write("speedlimit:" + Integer.toString(speedlimit));
            Log.d("info", "speedlimit sent");
            bt.run();
            bt.write("speed:" + speed.toString());
            Log.d("info", "speed sent");
            bt.run();
        }
        catch(IOException e){
            Log.e("error", "IOException caught when writing to Bluetooth socket.");
        }
    }
    public void writeBluetooth(boolean enable){
        try{
            bt.write( "enable:" + enable );
            Log.d("info", "speedlimit sent");
            bt.run();
        }
        catch(IOException e){
            Log.e("error", "IOException caught when writing to Bluetooth socket.");
        }
    }

    public void toggleLight(){
        if(lightOn){
            writeBluetooth(false);
            lightOn = false;
        }
        else{
            writeBluetooth(true);
            lightOn = true;
        }

    }*/




}//MainActivity END