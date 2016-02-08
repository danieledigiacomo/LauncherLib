package it.me.launcherlib;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.webkit.URLUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class URLValidator {

	private static String TAG = "URL VALIDATOR";
	
	//VALIDATE URL
	public static boolean isValidUrl(String url){
		boolean isValidUrl  = URLUtil.isValidUrl(url) ;
		if(isValidUrl){
			try {
				int responseCode = getResponseCode(url);
				return responseCode == 200;
				
			} catch (MalformedURLException e) {
				Logger.createErrorLog(TAG, "MalformedURLException", e.getMessage());
				return false;

			} catch (IOException e) {
				Logger.createErrorLog(TAG, "IOException", e.getMessage());
				return false;
			}
		}
		
		
		return false;
	}
	
	//GET RESPONSE CODE
	@SuppressLint("NewApi")
	public static int getResponseCode(String urlString) throws MalformedURLException, IOException {
		
		StrictMode.ThreadPolicy policy = new
				StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
				StrictMode.setThreadPolicy(policy);
		
	    URL u = new URL(urlString); 
	    HttpURLConnection huc =  (HttpURLConnection)  u.openConnection(); 
	    huc.setRequestMethod("GET"); 
	    huc.connect(); 
	    return huc.getResponseCode();
	}
	
}
