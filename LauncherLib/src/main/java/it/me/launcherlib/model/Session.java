package it.me.launcherlib.model;

import it.me.launcherlib.Config;
import it.me.launcherlib.database.DBAccount;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class Session {
	
	public static Config config;
	
	
	//IS FIRST LAUNCH
	public static boolean isFirstLaunch(Activity activity){
		SharedPreferences prefs = activity.getSharedPreferences("PREFERENCES", 0);
        boolean isFirstLaunch = prefs.getBoolean("isFirstLaunch", true); 
        
        Log.e("@@ FIRST LAUCH VAR", String.valueOf(isFirstLaunch));
        
        return isFirstLaunch;
	}
	
	//SET LAUNCHED
	public static void setLaunched(android.app.Activity activity){
		SharedPreferences prefs = activity.getSharedPreferences("PREFERENCES", 0);
        Editor editor = prefs.edit();
        editor.putBoolean("isFirstLaunch", false);
        editor.commit();
        
	}
	
	public static Account getAccount(Context context){
		DBAccount database = new DBAccount(context);
		return database.getAccount();
	}
	
	

}
