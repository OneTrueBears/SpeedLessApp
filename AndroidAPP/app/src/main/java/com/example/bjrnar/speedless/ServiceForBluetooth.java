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
    float speedlimit;
    ArrayList<Double> speed = new ArrayList<Double>(Arrays.asList(20.0, 40.0, 40.0, 30.0, 30.0, 35.0, 40.0, 50.0, 45.0, 50.0));

    Looper mServiceLooper;
    ServiceHandler mServiceHandler;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            long endTime = System.currentTimeMillis() + 5*1000;
            while (System.currentTimeMillis() < endTime) {
                synchronized (this) {
                    try {
                        wait(endTime - System.currentTimeMillis());
                    } catch (Exception e) {
                    }
                }
            }
            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1);
        }
    }


    @Override
    public void onCreate() {

        HandlerThread thread = new HandlerThread("ServiceStartArguments", 10);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    public ServiceForBluetooth() {
        super();
        connectBluetooth();
        //mServiceHandler.handleMessage();
        Log.d("info", "Bluetooth connected. Trying to send speed and speedlimit");
        try {
            Log.d("info", "delaying 3 second");
            TimeUnit.SECONDS.sleep(3);
        }catch(InterruptedException e){
            Log.e("error", "Interrupted Exception when timesleep");
        }
        //mainThread();
        //writeSpeed(85.0);
        //writeSpeedlimit(80);

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }



    public void mainThread(){
        Log.d("info", "Running URL + speed");
        URLHandler url = new URLHandler();
        for(int i = 0; i < speed.size(); i++ ){
            try {
                Log.d("info", "retreiving speedlimit");
                speedlimit = url.getSpeed();
            }catch(InterruptedException e){
                Log.e("error", "Interrupted Exception");
            }
            Log.d("info", "Writing speedlimit :" + speedlimit);
            writeSpeedlimit(speedlimit);
            try {
                Log.d("info", "delaying 1 second");
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e){
                Log.e("error", "Interrupted Exception when timesleep");
            }
            writeSpeed(speed.get(i));
            try {
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

    public void writeSpeedlimit(float speedlimit){
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
