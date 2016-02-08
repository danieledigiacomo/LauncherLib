package it.me.launcherlib.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBFavourites{

	
	private SQLiteDatabase database;
	private Database dbHelper;
	
	public DBFavourites(Context context) {
		dbHelper = new Database(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close(); 
		database.close();
	}
	
	public int insert(int position, String cdItem){
		this.open();
		ContentValues values = new ContentValues();
		values.put(Database.COLUMN_POSITION, position);
		values.put(Database.COLUMN_CD_ITEM, cdItem);
		long rowId = database.insert(Database.TABLE_FAVOURITES, null,values);
		this.close();
		return (int) rowId;
	}
	
	public void delete(int position,String cdItem){
		this.open();
		database.delete(Database.TABLE_FAVOURITES, Database.COLUMN_POSITION+"=? AND "+Database.COLUMN_CD_ITEM+"=?" , new String []{   String.valueOf(position),String.valueOf(cdItem)   });
		this.close();
	}
	
	public void delete(String cdItem){
		this.open();
		database.delete(Database.TABLE_FAVOURITES, Database.COLUMN_CD_ITEM+"=?" , new String []{   String.valueOf(cdItem)   });
		this.close();
	}
	
	public boolean has(String cdItem){
		this.open();
		String query = "SELECT * FROM " + Database.TABLE_FAVOURITES + " WHERE "  +Database.COLUMN_CD_ITEM+"=?"; 
		Cursor cursor = database.rawQuery(query, new String []{String.valueOf(cdItem)});
		boolean result = cursor.getCount()>0;
		this.close();
		return result;
	}
	
	public boolean has(int position){
		this.open();
		String query = "SELECT * FROM " + Database.TABLE_FAVOURITES + " WHERE "  +Database.COLUMN_POSITION+"=?"; 
		Cursor cursor = database.rawQuery(query, new String []{String.valueOf(position)});
		boolean result = cursor.getCount()>0;
		this.close();
		return result;
	}

	public void update(int position,String cdItem){
		this.open();
		ContentValues values = new ContentValues();
		values.put(Database.COLUMN_POSITION, position);
		values.put(Database.COLUMN_CD_ITEM, cdItem);
		database.update(Database.TABLE_FAVOURITES, values, Database.COLUMN_CD_ITEM+"=?" , new String []{   String.valueOf(cdItem)   });
		this.close();
	}
	
	  
}