package it.me.launcherlib;

import android.util.Log;

public class Logger {

	
	public static String PARAGRAPH_OPEN = "\n******************************************************";
	public static String PARAGRAPH_CLOSE= "******************************************************\n";
	
	public static String title(String title){
		return "-- " + title + " --" ;
	}

	public static void createInfoLog(String tag, String title, String message){
		Log.i(tag, Logger.PARAGRAPH_OPEN);
		Log.i(tag, Logger.title(title));
		Log.i(tag, message);
		Log.i(tag, Logger.PARAGRAPH_CLOSE);
	}

	public static void createWarningLog(String tag, String title, String message){
		Log.w(tag, Logger.PARAGRAPH_OPEN);
		Log.w(tag, Logger.title(title));
		Log.w(tag, message);
		Log.w(tag, Logger.PARAGRAPH_CLOSE);
	}

	public static void createErrorLog(String tag, String title, String message){
		Log.e(tag, Logger.PARAGRAPH_OPEN);
		Log.e(tag, Logger.title(title));
		Log.e(tag, message);
		Log.e(tag, Logger.PARAGRAPH_CLOSE);
	}

}
