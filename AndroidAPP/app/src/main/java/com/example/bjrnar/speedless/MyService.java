package com.example.bjrnar.speedless;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class MyService extends Service {

    /*
    Service -> 2(3)x threads
    handle inter communication 2 threads
    handle main to service communication, need?
    */

    //Prev one used workthread logic, this one should be multithread. Can comunicate accross threads w handlers.


    // FIELDS

    Bluetooth bt = new Bluetooth();
    boolean lightOn = true;
    boolean alive = true;

    //simulated speed array, its increment
    ArrayList<Double> speed = new ArrayList<Double>(Arrays.asList(45.0, 52.0, 35.0, 29.0, 39.0, 45.0, 51.0, 55.0, 59.0, 45.0, 33.0, 30.0, 35.0, 32.0, 35.0, 36.0, 45.0, 55.0));
    int increment = 0;

    URLHandler url = new URLHandler();

    HandlerThread BT_handlerThread;
    HandlerThread VDB_handlerThread;

   //LIFECYCLE

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        //return START_STICKY;
        /*try {
            boolean bol = intent.getExtras().getBoolean("msg");

            Message msgB = Message.obtain(BThandler,0);

            //Message msgSL = Message.obtain();
            msgSL.obj = URLspeedLimit;
            BThandler.sendMessage(msgSL);
            if (bol == true){


            } else {

            }

        } catch (Exception e){

        }*/


        return START_NOT_STICKY;
        /* IT WOULD NOT DIE with the other values!!! Bloody made our own intrusive little...
        *Thus - not sticky.... DIE DIE DIE o service
        *The return value from onStartCommand() must be one of the following constants:
        *START_NOT_STICKY
        *If the system kills the service after onStartCommand() returns, do not recreate the service, unless there are pending intents to deliver. This is the safest option to avoid running your service when not necessary and when your application can simply restart any unfinished jobs.
        *START_STICKY
        *If the system kills the service after onStartCommand() returns, recreate the service and call onStartCommand(), but do not redeliver the last intent. Instead, the system calls onStartCommand() with a null intent, unless there were pending intents to start the service, in which case, those intents are delivered. This is suitable for media players (or similar services) that are not executing commands, but running indefinitely and waiting for a job.
        *START_REDELIVER_INTENT
        *If the system kills the service after onStartCommand() returns, recreate the service and call onStartCommand() with the last intent that was delivered to the service. Any pending intents are delivered in turn. This is suitable for services that are actively performing a job that should be immediately resumed, such as downloading a file.
        *
        *
        * */

    }

    @Override
    public IBinder onBind(Intent intent) {
        //dont provide
        return null;
    }

   /* Need constructor?
    public MyService() { // Constructor?

    }
   */






    @Override
    public void onCreate() {
        /*
        The system calls this method when the service is first created, to perform one-time setup procedures
        (before it calls either onStartCommand() or onBind()). If the service is already running, this method is not called.
         */



        //Real work ahead -- MULTI-THREADING ---

        //Bluetooth - THREAD 1------------------------------
        //the thread
        BT_handlerThread = new HandlerThread("BT_HandlerThread");
        BT_handlerThread.setDaemon(true);
        BT_handlerThread.start();

        // Create a handler attached to the BT_handlerThread's Looper
        //use this to .sendMessage(Message) AND .post(runnable) INTO the thread
        final Handler BThandler = new Handler(BT_handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                // Process messages here
                Log.d("info", msg.obj.getClass().toString());
                if (msg.what == 0 ) { // SPEEDSTUFF from VDB

                    Log.d("info", "BT recived speedlimit: " + msg.obj.toString());
                    //float speedLimit = (float) msg.obj;
                    //float speedLimit = Float.parseFloat(msg.toString());
                    float speedLimit = Float.parseFloat(msg.obj.toString());

                    Log.d("info", "Writing speedlimit :" + speedLimit);
                    writeSpeedlimit(speedLimit);
                    try {
                        Log.d("info", "delaying 1 second");
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        Log.e("error", "Interrupted Exception when timesleep");
                    }
                    writeSpeed(speed.get(increment));
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        Log.e("error", "Interrupted Exception when timesleep");
                    }

                    //increment counter for iterating through simulated speed array
                    if (increment == speed.size() - 1) {
                        increment = 0;
                    } else {
                        increment += 1;
                    }
                } else if (msg.what == 1) { // LIGHTS STUFF, from mainactivity

                    if(Boolean.parseBoolean(msg.obj.toString()) == true){
                        writeBluetooth(true);

                    }
                    else if (Boolean.parseBoolean(msg.obj.toString()) == false){
                        writeBluetooth(false);

                    } else {

                        Log.d("error", "MSG ERROR recive 1 in bt - parse boolean ");
                    }


                } else {

                    Log.d("error", "MSG ERROR recive in bt ");

                }


            }
        };
        // BT thread stuff END



        //URLHandler - VDB speedlimit from coordinate, internett - THREAD 2-----------------
        //the thread
        VDB_handlerThread = new HandlerThread("VDB_HandlerThread");
        VDB_handlerThread.setDaemon(true);
        VDB_handlerThread.start();

        // Create a handler attached to the VDB_handlerThread's Looper
        //use this to .sendMessage(Message) AND .post(runnable) INTO the thread
        final Handler VDBhandler = new Handler(VDB_handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                // Process messages here




            }
        };
        // URL thread END

        //create runnable that the next one posts, such that BT may finish connecting first

        final Runnable vDb;
        vDb = new Runnable() {
            @Override
            public void run() {
                // Block of code to execute - DO URLhandler / VDB stuff here
                //while?

                float URLspeedLimit;

                while (alive) {

                    // Retrives and sends to BT-thread via BThandler, a speedlimit found by URLhandler as it increments
                    // through a coordinate list on each call
                    try {
                        Log.d("info", "retreiving url speedlimit");
                        URLspeedLimit = url.getSpeed(); //actually get speedlimit, currenly from internal coordinate list

                        Message msgSL = Message.obtain(BThandler,0);

                        msgSL.obj = URLspeedLimit;
                        BThandler.sendMessage(msgSL);
                    }catch(InterruptedException e){
                        Log.e("error", "Interrupted Exception");
                    }

                    //sleep

                    try {
                        Log.d("info", "delaying VDB 2 second");
                        TimeUnit.SECONDS.sleep(2);
                    }catch(InterruptedException e){
                        Log.e("error", "Interrupted Exception when VDB timesleep");
                    }


                }//while end


            }
        };



        //Create runnable to post to BTthread to get it all running
        Runnable bTr;
        bTr = new Runnable() {
            @Override
            public void run() {
                // Block of code to execute - DO bluetooth thread stuff here

                connectBluetooth();

                // to start VDB work AFTER bt has connected
                VDBhandler.post(vDb);


            }
        };

        //post the above to BT
        //this exits and both threads now do work
        //VDB in a loop, and BT by message response from VDB
        BThandler.post(bTr); // GOOOOO





        // GPS thread start? THREAD  3________-------
        //?


        // GPS thread END?


    }// onCreate end

    @Override
    public void onDestroy(){
        /*
        The system calls this method when the service is no longer used and is being destroyed. Your service should implement this
        to clean up any resources such as threads, registered listeners, receivers, etc. This is the last call the service receive
         */
        alive = false; //dead... hopefully
        BT_handlerThread.quit();
        VDB_handlerThread.quit();
        super.onDestroy();

    }


//methods -- still threaded use?

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






} //end
