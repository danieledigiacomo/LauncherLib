package it.me.launcherlib.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHistory extends SQLiteOpenHelper {
	  
	private static final String DATABASE_NAME = "/data/lisciani/lisciani.db";
	private static final int DATABASE_VERSION = 1;
	  
	public static final String TABLE_HISTORY = "history";
	public static final String COLUMN_ID_USER = "id_user";
	public static final String COLUMN_LABEL = "label";
	public static final String COLUMN_PACKAGE_NAME = "package";
	public static final String COLUMN_TIMESTAMP = "timestamp";
	
	
	private static final String DATABASE_CREATE_HISTORY = "create table "
			+ TABLE_HISTORY + "(" 
				+ COLUMN_ID_USER + " INTEGER, "
				+ COLUMN_PACKAGE_NAME + " TEXT, "
				+ COLUMN_LABEL + " TEXT, "
				+ COLUMN_TIMESTAMP + " INTEGER);";  
	
	
	
	public DatabaseHistory(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION); 
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_HISTORY);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DatabaseHistory.class.getName(),"Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
	    
	    onCreate(db);
	    
	    
	}
	  
} 