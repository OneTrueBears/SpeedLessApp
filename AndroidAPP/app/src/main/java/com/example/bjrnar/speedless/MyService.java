package com.example.bjrnar.speedless;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Looper;
import android.util.Log;

public class MyService extends Service {

    /*
    Service -> 2(3)x threads
    handle inter communication 2 threads
    handle main to service communication, need?
    */

    //Prev one used workthread logic, this one should be multithread. Can comunicate accross threads w handlers.

   //LIFECYCLE

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        //return START_STICKY;
        return START_REDELIVER_INTENT;
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
    public void onDestroy(){
        /*
        The system calls this method when the service is no longer used and is being destroyed. Your service should implement this
        to clean up any resources such as threads, registered listeners, receivers, etc. This is the last call the service receive
         */


        //lol infinite loop?V
        //stopSelf();
    }



    @Override
    public void onCreate() {
        /*
        The system calls this method when the service is first created, to perform one-time setup procedures
        (before it calls either onStartCommand() or onBind()). If the service is already running, this method is not called.
         */

        //Real work ahead -- MULTI-THREADING ---

        //Bluetooth - THREAD 1------------------------------
        //the thread
        HandlerThread BT_handlerThread = new HandlerThread("BT_HandlerThread");
        BT_handlerThread.start();

        // Create a handler attached to the BT_handlerThread's Looper
        //use this to .sendMessage(Message) AND .post(runnable) INTO the thread
        Handler BThandler = new Handler(BT_handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                // Process messages here
            }
        };

        //Create runnable to post
        Runnable bTr = new Runnable() {
            @Override
            public void run() {
                // Block of code to execute - DO bluetooth thread stuff here
                //while?

            }
        };

        BThandler.post(bTr); // GOOOOO BT

        // BT thread stuff END



        //URLHandler - VDB speedlimit from coordinate, internett - THREAD 2-----------------
        //the thread
        HandlerThread VDB_handlerThread = new HandlerThread("VDB_HandlerThread");
        VDB_handlerThread.start();

        // Create a handler attached to the VDB_handlerThread's Looper
        //use this to .sendMessage(Message) AND .post(runnable) INTO the thread
        Handler VDBhandler = new Handler(VDB_handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                // Process messages here
            }
        };

        //Create runnable to post
        Runnable vDBr = new Runnable() {
            @Override
            public void run() {
                // Block of code to execute - DO URLhandler / VDB stuff here
                //while?

            }
        };

        VDBhandler.post(vDBr); // GOOOOO VDB

        // URL thread END

        // GPS thread start? THREAD  3________-------


    }// onCreate end














} //end
