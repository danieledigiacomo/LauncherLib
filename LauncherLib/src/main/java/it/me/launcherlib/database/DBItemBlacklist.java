package it.me.launcherlib.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBItemBlacklist {

	private SQLiteDatabase database;
	private Database dbHelper;
	
	public DBItemBlacklist(Context context) {
		dbHelper = new Database(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close(); 
		database.close();
	}

	
	//SET LOCKED
	public void setLocked(int id_user,String cdItem){
		this.open();
		ContentValues values = new ContentValues();
		values.put(Database.COLUMN_ID_USER, id_user);
		values.put(Database.COLUMN_CD_ITEM, cdItem);
		database.replace(Database.TABLE_ITEMS_BLACKLIST, null, values);
		this.close();
	}
	
	//SET UNLOCKED
	public void setUnlocked(int id_user,String cdItem){
		this.open();
		database.delete(Database.TABLE_ITEMS_BLACKLIST, Database.COLUMN_ID_USER+"=? AND "+Database.COLUMN_CD_ITEM+"=?" , new String []{   String.valueOf(id_user),String.valueOf(cdItem)   });
		this.close();
	}
	
	
	//UNLOCK ALL ITEMS (BY USER)
	public void unlockAllItems(int id_user){
		this.open();
		database.delete(Database.TABLE_ITEMS_BLACKLIST, Database.COLUMN_ID_USER+"=?" , new String []{   String.valueOf(id_user)   });
		this.close();
	}
	
	
	
	
}
