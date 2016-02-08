package it.me.launcherlib.database;


import it.me.launcherlib.database.Database;
import it.me.launcherlib.model.PInfo;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBPackagesWhitelist {

	private SQLiteDatabase database;
	private Database dbHelper;
	
	public DBPackagesWhitelist(Context context) {
		dbHelper = new Database(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close(); 
		database.close();
	}
	 
	public ArrayList<String> getPackages(int idUser){
		this.open();
		ArrayList<String> packages = new ArrayList<String>();
		Cursor cursor = database.rawQuery("SELECT package from "+ Database.TABLE_PACKAGES_WHITELIST+ " where id_user=? ",new String []{String.valueOf(idUser)});
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			packages.add(cursor.getString(0));
			cursor.moveToNext();
		}
		cursor.close();
		    
		this.close();
		return packages;
	}
	
	//INSERT SITE
	public void insertApp(int userId,PInfo pinfo){
		this.open();
		ContentValues values = new ContentValues();
		values.put(Database.COLUMN_ID_USER, userId);
		values.put(Database.COLUMN_PACKAGE, pinfo.pname);
		database.insert(Database.TABLE_PACKAGES_WHITELIST, null,values);
		this.close();
	}
	
	//DELETE SITE
	public void deleteApp(String packageName){
		this.open();
		database.delete(Database.TABLE_PACKAGES_WHITELIST, Database.COLUMN_PACKAGE+"=? " , new String []{   packageName   });
		this.close();
	}
	
}
