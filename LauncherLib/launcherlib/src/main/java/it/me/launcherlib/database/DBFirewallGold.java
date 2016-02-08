package it.me.launcherlib.database;

import it.me.launcherlib.model.Firewall;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBFirewallGold {

	private SQLiteDatabase database;
	private Database dbHelper;
	
	public DBFirewallGold(Context context) {
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
	public void add(String number){
		this.open();
		number = number.replace(" ", "");
		if(!number.contains(Firewall.PREFIX)){
			ContentValues values = new ContentValues();
			values.put(Database.COLUMN_NUMBER, number);
			database.replace(Database.TABLE_FIREWALL_GOLD_NUMBER, null, values);
			
			values = new ContentValues();
			values.put(Database.COLUMN_NUMBER, Firewall.PREFIX + number);
			database.replace(Database.TABLE_FIREWALL_GOLD_NUMBER, null, values);	
		}else{
			ContentValues values = new ContentValues();
			values.put(Database.COLUMN_NUMBER, number);
			database.replace(Database.TABLE_FIREWALL_GOLD_NUMBER, null, values);
			
			values = new ContentValues();
			values.put(Database.COLUMN_NUMBER, number.replace(Firewall.PREFIX, ""));
			database.replace(Database.TABLE_FIREWALL_GOLD_NUMBER, null, values);	
		}
		
		this.close();
	}
	
	//REMOVE
	public void remove(String number){
		this.open();
		Log.e("AAA", "REMOVE NUMBER: " + number);
		number = number.replace(" ", "");
		Log.e("AAA", "REMOVE NUMBER: " + number);
		database.delete(Database.TABLE_FIREWALL_GOLD_NUMBER, Database.COLUMN_NUMBER + "=? OR "+ Database.COLUMN_NUMBER + "=? OR "+ Database.COLUMN_NUMBER + "=?" , new String []{   number  , Firewall.PREFIX + number, number.replace(Firewall.PREFIX,"")});
		this.close();
	}
	
	//REMOVE
	public void remove(){
		this.open();
		database.delete(Database.TABLE_FIREWALL_GOLD_NUMBER, null, null);
		this.close();
	}

	public boolean has(String number){
		this.open();
		number = number.replace(" ", "");
		Log.e("AAA",number);
		String query = "SELECT * FROM " + Database.TABLE_FIREWALL_GOLD_NUMBER + " WHERE "  +Database.COLUMN_NUMBER+"=? OR "+Database.COLUMN_NUMBER+"=?"; 
		Cursor cursor = database.rawQuery(query, new String []{String.valueOf(number), String.valueOf(Firewall.PREFIX + number)});
		boolean result = cursor.getCount()>0;
		this.close();
		return result;
	}
	
	public ArrayList<String> getNumbers() {
		this.open();
		ArrayList<String> numbers = new ArrayList<String>();
		Cursor cursor = database.rawQuery("SELECT * FROM " + Database.TABLE_FIREWALL_GOLD_NUMBER,null);
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	String number = cursor.getString(cursor.getColumnIndex(Database.COLUMN_NUMBER));
	    	numbers.add(number);
	    	cursor.moveToNext();
	    }
	    cursor.close();
	    this.close();
	    return numbers;
	}
	
	public ArrayList<String> getNumbersGroupPrefix() {
		this.open();
		ArrayList<String> numbers = new ArrayList<String>();
		String query = "SELECT REPLACE("+Database.COLUMN_NUMBER+","+ "'"+ Firewall.PREFIX + "'" + ",'') AS number "+ 
				" FROM " + Database.TABLE_FIREWALL_GOLD_NUMBER +
				" GROUP BY number;";
		
		Cursor cursor = database.rawQuery(query, null);
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	String number = cursor.getString(cursor.getColumnIndex(Database.COLUMN_NUMBER));
	    	numbers.add(number);
	    	cursor.moveToNext();
	    }
	    cursor.close();
	    this.close();
	    return numbers;
	}
	
	
}
