package com.example.bjrnar.speedless;

/**
 * Created by Amanda on 13.04.16.
 */
public class GPSListener {

    int nr1;
    int nr2 = 1;


    String[] coolStringArray = new String[]{"63.421943" , "10.396705" , "63.420941",
            "10.400418","63.419477","10.404227","63.419266","10.404978", "63.420305","10.405166",
            "63.421716","10.400689","63.422722","10.395389","63.421715", "10.394824",
            "63.419584", "10.395918", "63.417942", "10.395918", "63.418489", "10.394759", "63.419070",
            "10.395295","63.419843", "10.395799","63.420352", "10.394812","63.420640", "10.393664",
            "63.421082", "10.394340", "63.421994", "10.394673", "63.422752", "10.394469"};

    public String getGPSLat(){
        String lat = coolStringArray[nr1];
        nr1+=2;
        if (nr1 > coolStringArray.length-2){
            nr1 = 0;
        }
        return lat;

    }

    public String getGPSLong(){
        String longz = coolStringArray[nr2];
        nr2+=2;
        if (nr2 > coolStringArray.length-1){
            nr2 = 1;
        }
        return longz;

    }

}
