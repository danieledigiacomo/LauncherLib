package it.me.launcherlib.database;

import it.me.launcherlib.Config;

import java.util.Hashtable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBConfig{

	public static String KEY_STORE_PACKAGE = "store_package";
	public static String KEY_STORE_DETAIL_ACT = "store_detail_act";
	public static String KEY_SMS_APP_PACKAGE = "sms_package";
	public static String KEY_CALL_APP_PACKAGE = "call_package";
	public static String KEY_CONTAT_APP_PACKAGE = "contact_package";
	public static String KEY_CONTAT_APP_PACKAGE_DETAIL = "contact_package_detail";
	
	private SQLiteDatabase database;
	private Database dbHelper;
	
	public DBConfig(Context context) {
		dbHelper = new Database(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close(); 
		database.close();
	}

	//UPDATE ROW
	public void update(String key,String value){
		this.open();
		ContentValues values = new ContentValues();
		values.put(Database.COLUMN_VALUE, value);
		database.update(Database.TABLE_CONFIG, values, "key=?" , new String []{key});
		this.close();
	  
	}
	
	//GET VALUE
	public String getValue(String key){
		this.open();
		String value = "";
		Cursor cursor = database.rawQuery("SELECT value FROM config WHERE key = '"+ key +"'",null);
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	value = cursor.getString(cursor.getColumnIndex(Database.COLUMN_VALUE));
	    	cursor.moveToNext();
	    }
	    cursor.close();
	    this.close();
	    
	    return value;
	}
	
	
	public Config getConfig(){
		this.open();
		Config config = new Config();
		Hashtable<String, String> hashTable = new Hashtable<String, String>();
    	
		Cursor cursor = database.rawQuery("SELECT * FROM config ",null);
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	
	    	hashTable = cursorToConfig(cursor,hashTable);
	    	cursor.moveToNext();
	    }
	    cursor.close();
		
	    config = hashToConfig(hashTable);
		
		this.close();
		return config;
	}
	
	//CURSOR
	private Hashtable<String, String> cursorToConfig(Cursor cursor,Hashtable<String, String> hashTable ) {
		String key = cursor.getString(cursor.getColumnIndex(Database.COLUMN_KEY));
		String value = cursor.getString(cursor.getColumnIndex(Database.COLUMN_VALUE));
		hashTable.put(key, value);
		
		return hashTable;
	}
	
	private Config hashToConfig(Hashtable<String, String> hashTable){
		Config config = new Config();

		config.setDataPath(hashTable.get(Database.KEY_DATA_LISCIANI_PATH));
		config.setImagesPath(hashTable.get(Database.KEY_DATA_IMAGES_PATH));
		config.setAvatarsPath(hashTable.get(Database.KEY_DATA_AVATARS_PATH));
		
		config.setBackgroundsPath(hashTable.get(Database.KEY_DATA_BACKGROUNDS_PATH));
		config.setSessionPath(hashTable.get(Database.KEY_DATA_SESSION_PATH));
		config.setFlagsPath(hashTable.get(Database.KEY_DATA_FLAGS_PATH));
		config.setDBApiPath(hashTable.get(Database.KEY_DATA_DBAPI_PATH));
		config.setDBPath(hashTable.get(Database.KEY_DATA_DB_PATH));
		config.setInstallerPath(hashTable.get(Database.KEY_INSTALLER_PATH));
		config.setAppFolderPath(hashTable.get(Database.KEY_APPS_FOLDER_PATH));
		config.setAppImagesPath(hashTable.get(Database.KEY_APPS_IMAGES_PATH));
		config.setAppAudioPath(hashTable.get(Database.KEY_APPS_AUDIO_PATH));
		config.setAppVideoPath(hashTable.get(Database.KEY_APPS_VIDEO_PATH));
		config.setCloudUrl(hashTable.get(Database.KEY_CLOUD_URL));
		config.setNotifyStatusGeneric(hashTable.get(Database.KEY_NOTIFY_STATUS_GENERIC));
		config.setNotifyStatusApp(hashTable.get(Database.KEY_NOTIFY_STATUS_APP));
		config.setNotifyStatusEco(hashTable.get(Database.KEY_NOTIFY_STATUS_ECO));
		config.setNotifyStatusPromos(hashTable.get(Database.KEY_NOTIFY_STATUS_PROMOS));
		
		return config;
	}

	  
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}