package it.me.launcherlib.database;

import it.me.launcherlib.model.Firewall;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBReadedSms {

	private SQLiteDatabase database;
	private Database dbHelper;
	
	public DBReadedSms(Context context) {
		dbHelper = new Database(context);
	}

	public void open() throws SQLException {
		if(database!=null && dbHelper!=null){
			close();
		}
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close(); 
		database.close();
	}

	
	//ADD
	public void add(String id){
		this.open();
		ContentValues values = new ContentValues();
		values.put(Database.COLUMN_ID, id);
		database.replace(Database.TABLE_READED_SMS, null, values);
		this.close();
	}
	
	//REMOVE
	public void remove(String id){
		this.open();
		database.delete(Database.TABLE_READED_SMS, Database.COLUMN_ID + "=?" , new String []{   id	});
		this.close();
	}
	
	public ArrayList<String> getIdMessages() {
		this.open();
		ArrayList<String> items = new ArrayList<String>();
		Cursor cursor = database.rawQuery("SELECT * FROM " + Database.TABLE_READED_SMS,null);
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	String item = cursor.getString(cursor.getColumnIndex(Database.COLUMN_ID));
	    	items.add(item);
	    	cursor.moveToNext();
	    }
	    cursor.close();
	    this.close();
	    return items;
	}
	
	
	
}
