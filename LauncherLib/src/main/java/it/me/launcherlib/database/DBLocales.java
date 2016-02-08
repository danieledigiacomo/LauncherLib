package it.me.launcherlib.database;


import it.me.launcherlib.model.Locale;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBLocales{

	
	private SQLiteDatabase database;
	private Database dbHelper;
	
	public DBLocales(Context context) {
		dbHelper = new Database(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close(); 
		database.close();
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