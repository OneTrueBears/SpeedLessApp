package nvdb;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import nvdb.GPSTracker;
import android.content.Context;

public class URLHandler {


	private Context context;
	public GPSTracker gps = new GPSTracker(context);
	
	public String getDocument(String a){
		URL url;
		String document = "";
		
		try{
			
			url = new URL(a);
			
			URLConnection conn = url.openConnection();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			
			
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


	public double GetLatitude(){
		return gps.getLatitude();
	}

	public double GetLongitude(){
		return gps.getLongitude();
	}
	

	public static void main(String[] args) {
		URLHandler urlhandler = new URLHandler();
		System.out.println(urlhandler.GetLatitude());
		String startURL = urlhandler.fromCoordinatestoURL("10.283954","63.300879"); //Example coordinates
		String document = urlhandler.getDocument(startURL);
		String veglenkeId = urlhandler.findVeglenkeID(document);
		String veglenkePosisjon = urlhandler.findVeglenkePosisjon(document);
		String a = urlhandler.getFinalURL(veglenkeId,veglenkePosisjon);
		String doc2 = urlhandler.getDocument(a);
		System.out.println(urlhandler.findFartsgrense(doc2, veglenkeId, veglenkePosisjon));
		
		

	}

}
