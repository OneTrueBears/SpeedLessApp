package com.example.bjrnar.speedless;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.os.Handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


public class ServiceForBluetooth extends Service {

    Bluetooth bt = new Bluetooth();
    boolean lightOn = true;
    //float speedlimit;
    ArrayList<Double> speed = new ArrayList<Double>(Arrays.asList(45.0,52.0,35.0,29.0,39.0,45.0,51.0,55.0,59.0,45.0,33.0,30.0,35.0,32.0,35.0,36.0,45.0, 55.0));
    ArrayList<Integer> speedlimits = new ArrayList<Integer>(Arrays.asList(50,50,30,30,50,50,50,50,50,30,30,30,50,30,30,30,50,50));
    String[] coordinates = new String[]{"63.421943" , "10.396705" , "63.420941",
            "10.400418","63.419477","10.404227","63.419266","10.404978", "63.420305","10.405166",
            "63.421716","10.400689","63.422722","10.395389","63.421715", "10.394824",
            "63.419584", "10.395918", "63.417942", "10.395918", "63.418489", "10.394759", "63.419070",
            "10.395295","63.419843", "10.395799","63.420352", "10.394812","63.420640", "10.393664",
            "63.421082", "10.394340", "63.421994", "10.394673", "63.422752", "10.394469"};

    public void onCreate(){
        connectBluetooth();
    }

    public ServiceForBluetooth() {
        super();
        connectBluetooth();
        Log.d("info", "Bluetooth connected. Trying to send speed and speedlimit");
        try {
            Log.d("info", "delaying 3 second");
            TimeUnit.SECONDS.sleep(3);
        }catch(InterruptedException e){
            Log.e("error", "Interrupted Exception when timesleep");
        }
        mainThread();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public void mainThread(){
        for(int i= 0; i < speed.size(); i++){
            writeSpeed(speed.get(i));
            try {
                Log.d("info", "delaying 3 second");
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e){
                Log.e("error", "Interrupted Exception when timesleep");
            }
            writeSpeedlimit(speedlimits.get(i));
            try {
                Log.d("info", "delaying 3 second");
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e){
                Log.e("error", "Interrupted Exception when timesleep");
            }

        }

    }

    public void connectBluetooth() {
        try {
            bt.init();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void writeSpeed(Double speed){
        try{
            bt.write("speed:" + speed.toString());
            Log.d("info", "speed sent");
            bt.run();
        }
        catch(IOException e){
            Log.e("error", "IOException caught when writing to Bluetooth socket.");
        }
    }

    public void writeSpeedlimit(int speedlimit){
        try{
            bt.write("speedlimit:" + Float.toString(speedlimit));
            Log.d("info", "speedlimit sent");
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

    }
}
