package com.example.bjrnar.speedless;

/**
 * Created by carolinekiaer on 04.04.16.
 */

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.ParcelUuid;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
// import java.util.logging.Handler;

/**
 * Created by Amanda on 09.03.16.
 */
public class Bluetooth {
    private OutputStream outputStream;
    private InputStream inStream;
    private BluetoothSocket socket;
    private final int MESSAGE_READ = 9999;
    public Handler mHandler = new Handler();

    public void init() throws IOException {
        BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
        if (blueAdapter != null) {
            if (blueAdapter.isEnabled()) {
                Set<BluetoothDevice> bondedDevices = blueAdapter.getBondedDevices();

                if(bondedDevices.size() > 0){
                    // BluetoothDevice[] devices = (BluetoothDevice[]) bondedDevices.toArray();
                    BluetoothDevice device = null;
                    for (BluetoothDevice dev : bondedDevices) {
                        if (dev.getName().equalsIgnoreCase("raspberrypi")) {
                            device = dev;
                            Log.d("Info","Found " + device.getName());
                        }
                    }
                    if (device == null) {
                        Log.d("Info", "device == null");
                    }
                    else {
                        ParcelUuid[] uuids = device.getUuids();

                        socket = device.createRfcommSocketToServiceRecord(UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee"));


                        // BluetoothSocket socket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString("0000110E-0000-1000-8000-00805F9B34FB"));

                        //for (ParcelUuid uuid: uuids) {
                        //Log.d("Info", uuid.getUuid().toString());
                        //}

                        // BluetoothSocket socket = device.createInsecureRfcommSocketToServiceRecord(uuids[0].getUuid());
                        // for (ParcelUuid uuid: uuids) {
                        //    Log.d("Info", uuid.getUuid().toString());
                        //}

                        Log.d("Info", "Connecting");
                        try{
                            Log.d("Info", "Trying to connect");
                            socket.connect();
                            Log.d("Info", "Connection succesful");
                        }
                        catch(java.io.IOException e){
                            Log.e("",e.getMessage());
                            Log.e("error", "IOException");
                            try {
                                Log.e("info", "trying fallback...");

                                socket = (BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(device, 1);
                                socket.connect();

                                Log.d("info", "Connected");
                            }
                            catch (java.lang.NoSuchMethodException e2) {
                                Log.e("Error", "NoSuchMethodException");
                            }
                            catch (java.lang.IllegalAccessException e2) {
                                Log.e("Error", "IllegalAccessException");
                            }
                            catch (java.lang.reflect.InvocationTargetException e2) {
                                Log.e("Error", "InvocationTargetException");
                            }
                            catch(java.io.IOException e3){
                                Log.e("",e3.getMessage());
                                Log.e("error", "IOException");
                            }
                        }


                        outputStream = socket.getOutputStream();
                        inStream = socket.getInputStream();
                        Log.d("info", "Outputstream and inputstream created.");
                    }
                }
                else {
                    Log.e("error", "No appropriate paired devices.");
                }
            }
            else{
                Log.e("error", "Bluetooth is disabled.");
            }
        }
    }

    public void write(String s) throws IOException {
        outputStream.write(s.getBytes());
        Log.d("Info", "Message sent");
    }

    public void run() {
        final int BUFFER_SIZE = 1024;

        // while (true) {
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytes = 0;
            bytes = inStream.read(buffer, bytes, BUFFER_SIZE - bytes);
            String s = "";
            for(int i = 0; i < bytes; i++) {
                char character = (char) buffer[i];
                s += character;
            }
            Log.d("Message", "Received message: " + s);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        // }
    }
    public void close(){
        try {
            Log.d("info", "Closing socket. Phone disconnecting from Pi.");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}