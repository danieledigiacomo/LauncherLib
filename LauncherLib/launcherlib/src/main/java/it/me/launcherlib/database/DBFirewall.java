package it.me.launcherlib.database;

import it.me.launcherlib.model.Firewall;

import java.util.Hashtable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBFirewall{

	public static String KEY_CALLS = "calls";
	public static String KEY_SMS = "sms";
	public static String KEY_EMAIL = "email";
	public static String KEY_KEYBOARD = "keyboard";
	public static String KEY_EARPHONES = "earphones";
	
	private SQLiteDatabase database;
	private Database dbHelper;
	
	public DBFirewall(Context context) {
		dbHelper = new Database(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close(); 
		database.close();
	}

	/*
	 * INSERISCE LA CHIAVE EMAIL 
	 * SE NO PRESENTE (v2)
	 */
	public void insertKeyEmail(){
		if(!hasKey(KEY_EMAIL)){
			this.open();
			ContentValues values = new ContentValues();
			values.put(Database.COLUMN_KEY, KEY_EMAIL);
			values.put(Database.COLUMN_VALUE, Firewall.OFF);
			database.insert(Database.TABLE_FIREWALL, null,values);
			this.close();	
		}
	}
	
	
	
	public boolean hasKey(String key){
		this.open(); 
		Cursor cursor = database.rawQuery("SELECT value FROM " + Database.TABLE_FIREWALL+ " WHERE key = '"+ key +"'",null);
	    boolean result = cursor.getCount()>0;
		this.close();
		return result;
	}
	
	
	//UPDATE ROW
	public void update(String key,int value){
		this.open();
		ContentValues values = new ContentValues();
		values.put(Database.COLUMN_VALUE, value);
		database.update(Database.TABLE_FIREWALL, values, "key=?" , new String []{key});
		this.close();
	  
	}
	
	//GET VALUE
	public String getValue(String key){
		this.open();
		String value = "";
		Cursor cursor = database.rawQuery("SELECT value FROM " + Database.TABLE_FIREWALL+ " WHERE key = '"+ key +"'",null);
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	value = cursor.getString(cursor.getColumnIndex(Database.COLUMN_VALUE));
	    	cursor.moveToNext();
	    }
	    cursor.close();
	    this.close();
	    
	    return value;
	}
	
	
	public Firewall getFirewall(){
		this.open();
		Firewall firewall = new Firewall();
		Hashtable<String, Integer> hashTable = new Hashtable<String, Integer>();
    	
		Cursor cursor = database.rawQuery("SELECT * FROM " +Database.TABLE_FIREWALL ,null);
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	
	    	hashTable = cursorToFirewall(cursor,hashTable);
	    	cursor.moveToNext();
	    }
	    cursor.close();
		firewall = hashToFirewall(hashTable);
		this.close();
		return firewall;
	}
	
	//CURSOR
	private Hashtable<String, Integer> cursorToFirewall(Cursor cursor,Hashtable<String, Integer> hashTable ) {
		String key = cursor.getString(cursor.getColumnIndex(Database.COLUMN_KEY));
		int value = cursor.getInt(cursor.getColumnIndex(Database.COLUMN_VALUE));
		hashTable.put(key, value);
		
		return hashTable;
	}
	
	private Firewall hashToFirewall(Hashtable<String, Integer> hashTable){
		Firewall firewall = new Firewall();
		firewall.setCalls(hashTable.get(KEY_CALLS));	
		firewall.setSms(hashTable.get(KEY_SMS));
		firewall.setKeyboard(hashTable.get(KEY_KEYBOARD));
		firewall.setEarphones(hashTable.get(KEY_EARPHONES));
	
		if(hashTable.containsKey(KEY_EMAIL)){
			firewall.setEmail(hashTable.get(KEY_EMAIL));
		}else{
			firewall.setEmail(Firewall.OFF);
		}
		
		
		return firewall;
	}

	  
	
	
}