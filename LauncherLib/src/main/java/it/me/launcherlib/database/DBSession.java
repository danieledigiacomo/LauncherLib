package it.me.launcherlib.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBSession{

	
	private SQLiteDatabase database;
	private Database dbHelper;
	
	public DBSession(Context context) {
		dbHelper = new Database(context);
	}

	public void open() throws SQLException {
		if(dbHelper!=null){
			database = dbHelper.getWritableDatabase();
		}

	}
	
	public void close() {
		dbHelper.close(); 
		database.close();
	}
	
	//UPDATE SESSION
	public void updateSession(String key, String value){
		if(!hasKey(key)){
			addKey(key);
		}
		this.open();
		ContentValues values = new ContentValues();
		values.put(Database.COLUMN_VALUE, value);
		database.update(Database.TABLE_SESSION, values, "key=?" , new String []{ key });
		this.close();
	}
	
	//GET VALUE
	public String getValue(String key){
		this.open();
		String value = "";
		Cursor cursor = database.rawQuery("SELECT value FROM " + Database.TABLE_SESSION + " WHERE key = '"+ key +"'",null);
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	value = cursor.getString(cursor.getColumnIndex(Database.COLUMN_VALUE));
	    	cursor.moveToNext();
	    }
	    cursor.close();
	    this.close();
	    
	    return value;
	}
	
	public boolean hasKey(String key){
		this.open();
		String query = "SELECT * FROM " + Database.TABLE_SESSION + " WHERE "  +Database.COLUMN_KEY+"=?"; 
		Cursor cursor = database.rawQuery(query, new String []{String.valueOf(key)});
		boolean result = cursor.getCount()>0;
		this.close();
		return result;
	}
	
	public int addKey(String key){
		this.open();
		ContentValues values = new ContentValues();
		values.put(Database.COLUMN_KEY, key);
		values.put(Database.COLUMN_VALUE, "");
		long rowId = database.insert(Database.TABLE_SESSION, null,values);
		this.close();
		return (int) rowId;
	}
	
	//UPDATE SESSION
	public void updateSession(int user_id, String game_code){
		this.open();
		ContentValues values = new ContentValues();
		values.put(Database.COLUMN_VALUE, user_id);
		database.update(Database.TABLE_SESSION, values, "key=?" , new String []{"id_user"});
		values.put("value", game_code);
		database.update(Database.TABLE_SESSION, values, "key=?" , new String []{"game_code"});
		this.close();
	  
	}
	//UPDATE SESSION
	public void updateSession(int user_id, String game_code, String game_package){
		this.open();
		ContentValues values = new ContentValues();
		values.put(Database.COLUMN_VALUE, user_id);
		database.update(Database.TABLE_SESSION, values, "key=?" , new String []{"id_user"});
		values.put("value", game_code);
		database.update(Database.TABLE_SESSION, values, "key=?" , new String []{"game_code"});
		values.put("value", game_package);
		database.update(Database.TABLE_SESSION, values, "key=?" , new String []{"game_package"});
		this.close();
	  
	}
		
	//UPDATE SESSION
	public void updateSession(String storeCdItem){
		this.open();
		ContentValues values = new ContentValues();
		values.put(Database.COLUMN_VALUE, storeCdItem);
		database.update(Database.TABLE_SESSION, values, "key=?" , new String []{"store_cd_item"});
		this.close();
	}
	
	//UPDATE SESSION
	public void updateLastNotificationTimestamp(long timestamp){
		this.open();
		ContentValues values = new ContentValues();
		values.put(Database.COLUMN_VALUE, timestamp);
		database.update(Database.TABLE_SESSION, values, "key=?" , new String []{"last_notification_timestamp"});
		this.close();
	}
	 
	public long getLastNotificationTimestamp(){
		this.open();
		String query = "SELECT * FROM session WHERE key=?;"; 
		Cursor cursor = database.rawQuery(query, new String []{String.valueOf("last_notification_timestamp")});
		cursor.moveToFirst();
    	String timestamp = cursor.getString(cursor.getColumnIndex("value"));	
    	cursor.moveToNext();
	    cursor.close();
		
		this.close();
		return Long.valueOf(timestamp);
	}
	
	//GET CURRENT USER ID
	public int getUserId(){
		this.open();
		String query = "SELECT * FROM session WHERE key=?;"; 
		Cursor cursor = database.rawQuery(query, new String []{String.valueOf("id_user")});
		cursor.moveToFirst();
    	String userId = cursor.getString(cursor.getColumnIndex("value"));	
    	cursor.moveToNext();
	    cursor.close();
		
		this.close();
		return Integer.valueOf(userId);
	}
	
	public int getIdNickname(){
		this.open();
		String query = "SELECT * FROM session WHERE key=?;"; 
		Cursor cursor = database.rawQuery(query, new String []{String.valueOf("cloud_id_nickname")});
		cursor.moveToFirst();
    	String userId = cursor.getString(cursor.getColumnIndex("value"));	
    	cursor.moveToNext();
	    cursor.close();
		if(userId==null || userId.equals("")){
			userId = "-1";
		}
		this.close();
		return Integer.valueOf(userId);
	}
		
	public String getNickname(){
		this.open();
		String query = "SELECT * FROM session WHERE key=?;"; 
		Cursor cursor = database.rawQuery(query, new String []{String.valueOf("cloud_nickname")});
		cursor.moveToFirst();
    	String nickname = cursor.getString(cursor.getColumnIndex("value"));	
    	cursor.moveToNext();
	    cursor.close();
		if(nickname==null || nickname.equals("")){
			nickname = "";
		}
		this.close();
		return nickname;
	}
	
	

	
	
}