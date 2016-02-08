package it.me.launcherlib.database;


import it.me.launcherlib.database.Database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBPackagesBlacklist {

	private SQLiteDatabase database;
	private Database dbHelper;
	
	public DBPackagesBlacklist(Context context) {
		dbHelper = new Database(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close(); 
		database.close();
	}
	 
	public ArrayList<String> getPackages(){
		this.open();
		ArrayList<String> packages = new ArrayList<String>();
		Cursor cursor = database.rawQuery("SELECT package from " + Database.TABLE_PACKAGES_BLACKLIST,null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			packages.add(cursor.getString(0));
			cursor.moveToNext();
		}
		cursor.close();
		    
		this.close();
		return packages;
	}
	
	
	
}
