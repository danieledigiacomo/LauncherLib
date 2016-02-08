package it.me.launcherlib.database;


import it.me.launcherlib.model.Locale;
import it.me.launcherlib.model.User;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBNickname{

	
	private SQLiteDatabase database;
	private Database dbHelper;
	
	public DBNickname(Context context) {
		dbHelper = new Database(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close(); 
		database.close();
	}

	public void insert(int idUser, int idNickname){
		if(nickAssociated(idNickname)){
			updateUser(idUser, idNickname);
		}else{
			this.open();
			ContentValues values = new ContentValues();
			values.put(Database.COLUMN_ID_USER, idUser);
			values.put(Database.COLUMN_ID_NICKNAME, idNickname);
			database.insert(Database.TABLE_USERS_NICKNAMES, null,values);
			this.close();	
		}
		
	}
	
	public boolean userProfiled(int idUser){
		this.open();
		String query = "SELECT * FROM " + Database.TABLE_USERS_NICKNAMES + " WHERE " + Database.COLUMN_ID_USER + "=?;"; 
		Cursor cursor = database.rawQuery(query, new String []{String.valueOf(idUser)});
		boolean result = cursor.getCount()>0;
		this.close();
		return result;
	}
	
	public boolean nickAssociated(int idNickname){
		this.open();
		String query = "SELECT * FROM " + Database.TABLE_USERS_NICKNAMES + " WHERE " + Database.COLUMN_ID_NICKNAME+ "=?;"; 
		Cursor cursor = database.rawQuery(query, new String []{String.valueOf(idNickname)});
		boolean result = cursor.getCount()>0;
		this.close();
		return result;
	}
	
	public boolean nickAssociatedByUser(int idUser){
		this.open();
		String query = "SELECT * FROM " + Database.TABLE_USERS_NICKNAMES + " WHERE " + Database.COLUMN_ID_USER+ "=?;"; 
		Cursor cursor = database.rawQuery(query, new String []{String.valueOf(idUser)});
		boolean result = cursor.getCount()>0;
		this.close();
		return result;
	}
	
	public int getIdUser(int idNickname) {
		this.open();
		int idUser = -1;
		String query = "SELECT * FROM " + Database.TABLE_USERS_NICKNAMES + " WHERE " + Database.COLUMN_ID_NICKNAME+ "=?;"; 
		Cursor cursor = database.rawQuery(query, new String []{String.valueOf(idNickname)});
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	idUser = cursor.getInt(cursor.getColumnIndex(Database.COLUMN_ID_USER));
	    	cursor.moveToNext();
	    }
	    cursor.close();
	    this.close();
	    return idUser;
	}
	
	
	public void updateNickname(int idUser, int idNickname){
		this.open();
		ContentValues values = new ContentValues();
		values.put(Database.COLUMN_ID_NICKNAME, idNickname);
		database.update(Database.TABLE_USERS_NICKNAMES, values, Database.COLUMN_ID_USER + "=?" , new String []{	String.valueOf(idUser)});
		this.close();
	}
	
	public void updateUser(int idUser, int idNickname){
		this.open();
		ContentValues values = new ContentValues();
		values.put(Database.COLUMN_ID_USER, idUser);
		database.update(Database.TABLE_USERS_NICKNAMES, values, Database.COLUMN_ID_NICKNAME+ "=?" , new String []{	String.valueOf(idNickname)});
		this.close();
	}
	
	//DELETE SITE
	public void delete(int idUser){
		this.open();
		database.delete(Database.TABLE_USERS_NICKNAMES, Database.COLUMN_ID_USER + "=? " , new String []{   String.valueOf(idUser)   });
		this.close();
	}
	
	//GET LOCALES
	public ArrayList<Locale> getLocales(){
		this.open();
		ArrayList<Locale> locales = new ArrayList<Locale>();
		String query = "SELECT * FROM " + Database.TABLE_LOCALES + ";";
		Cursor cursor = database.rawQuery(query, new String []{				});
		cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	Locale locale = cursorToLocale(cursor);
	    	locales.add(locale);
	    	cursor.moveToNext();
	    }
	    cursor.close();
	    this.close();
	    return locales;
	}
	

	//CURSOR
	private Locale cursorToLocale(Cursor cursor) {
		String code = cursor.getString(cursor.getColumnIndex(Database.COLUMN_CD));
		String label = cursor.getString(cursor.getColumnIndex(Database.COLUMN_LABEL));
		Locale locale = new Locale(code, label);
		
		return locale;
	}
	
	

	
}