package com.example.bjrnar.speedless;


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

public class URLHandler {

	GPSListener gpsl = new GPSListener();

	public String getGPSLong(){
		return gpsl.getGPSLong();
	}

	public String getGPSLat(){
		return gpsl.getGPSLat();
	}

	public String getDocument(String a){
		URL url;
		String document = "";

		try{
            Log.d("info", "creating a new url object");
			url = new URL(a);

            Log.d("info", "opening an URL connection");
			URLConnection conn = url.openConnection();

            Log.d("info", "creating a buffered reader");
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));


            Log.d("info", "retreiving inputLines from URL-buffered reader");
			String inputLine;
			while((inputLine = br.readLine()) != null){
				document += inputLine;
			}

			br.close();

			//System.out.println("Done");
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return document;
	}

	public String findVeglenkeID(String document){
		int start = document.indexOf("veglenkeId");
		int slutt = document.indexOf("veglenkePosisjon");
		start += 12;
		slutt -= 2;
		return document.substring(start, slutt);
	}

	public String findVeglenkePosisjon(String document){
		int start = document.indexOf("veglenkePosisjon");
		int slutt = document.indexOf("vegKategori");
		start += 18;
		slutt -= 2;
		return document.substring(start, slutt);
	}

	public float findFartsgrense(String document, String veglenkeId, String veglenkePosisjon){
		int start = document.indexOf("enumVerdi");
		int slutt = document.indexOf("enumVerdi");
		start -= 5;
		slutt -= 3;
		String streng = document.substring(start, slutt);
		float f = Float.parseFloat(streng);
		return f;
	}

	public String fromCoordinatestoURL(String coord1, String coord2){
		String url = "https://www.vegvesen.no/nvdb/api/vegreferanse/koordinat?lon=";
		url+= coord1;
		url+= "&lat=";
		url+=coord2;
		return url;
	}

	public String getFinalURL(String veglenkeId, String veglenkePosisjon){
		String url = "https://www.vegvesen.no/nvdb/api/sok?kriterie={lokasjon:{veglenker:[{id:";
		url+=veglenkeId;
		url+=",fra:";
		url+=veglenkePosisjon;
		url+=",til:";
		url+=veglenkePosisjon;
		url+="}]},objektTyper:[{id:105,antall:10,filter:[]}]}";
		return url;
	}


	public float getSpeed() throws InterruptedException {
		//while(true){
		String startURL = fromCoordinatestoURL(getGPSLong(),getGPSLat());
        Log.d("info", "startURL is: " + startURL);
        String document = getDocument(startURL);
        Log.d("info", "document is: " + document);
		String veglenkeId = findVeglenkeID(document);
        Log.d("info", "veglenkeId is: " + veglenkeId);
		String veglenkePosisjon = findVeglenkePosisjon(document);
        Log.d("info", "veglenkePosisjon is: " + veglenkePosisjon);
		String a = getFinalURL(veglenkeId,veglenkePosisjon);
        Log.d("info", "FinalURL is: " + a);
		String doc2 = getDocument(a);
        Log.d("info", "Doc2 is: " + doc2);
        //TimeUnit.SECONDS.sleep(1);
        Log.d("info", "returning speedlimit");
		return(findFartsgrense(doc2, veglenkeId, veglenkePosisjon));
		//}

	}


}
