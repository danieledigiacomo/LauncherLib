package it.me.launcherlib;


import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

public class WifiTool {

	public static final String TAG = " WIFITOOL ";
	public static final int STATUS_OFF = 0;
	public static final int STATUS_ON = 1;
	
	public static boolean isConnected(Context context){
		return getCurrentSSID(context)!=null /*&& isNetworkAvailable(context)*/ ;
	}
	
	public static String getCurrentSSID(Context context) {
		String ssid = null;
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo.isConnected()) {
			final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
			
            Log.i(TAG, Logger.PARAGRAPH_OPEN);
  			Log.i(TAG, Logger.title("WIFITOOL"));
  			Log.i(TAG, "isConnected: " + networkInfo.isConnected() );
  			Log.i(TAG, "isFailover: " + networkInfo.isFailover() );
  			Log.i(TAG, "Reason: " + networkInfo.getReason() );
  			Log.i(TAG, "Details: " + networkInfo.getDetailedState() );
  			Log.i(TAG, Logger.PARAGRAPH_CLOSE);
  			
			if (connectionInfo != null && !TextUtils.isEmpty(connectionInfo.getSSID())) {
				ssid = connectionInfo.getSSID();
			}
		}
		return ssid;
	}
	
	
	public static void setWifiStatus(boolean status, Activity activity){
		WifiManager wifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE); 
		wifiManager.setWifiEnabled(status);
	}
	
	
	private static boolean isNetworkAvailable(Context context) {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	public static int pingHost(String host) throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec("ping -c 1 " + host);
        proc.waitFor();     
        int exit = proc.exitValue();
        
        Log.e("@@PING RESULT @@", String.valueOf(exit));
        
        
        return exit;
    }
	

}
