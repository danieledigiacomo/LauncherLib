package it.me.launcherlib.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBSiteWhitelist {

	private SQLiteDatabase database;
	private Database dbHelper;
	
	public DBSiteWhitelist(Context context) {
		dbHelper = new Database(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close(); 
		database.close();
	}

	
	//SET UNLOCKED
	public void setUnLocked(int id_user,int id_site){
		this.open();
		ContentValues values = new ContentValues();
		values.put(Database.COLUMN_ID_USER, id_user);
		values.put(Database.COLUMN_ID_SITE, id_site);
		
		database.insert(Database.TABLE_SITES_WHITELIST, null,values);
		this.close();
	}
	
	//SET LOCKED
	public void setLocked(int id_user,int id_site){
		this.open();
		database.delete(Database.TABLE_SITES_WHITELIST, "id_user=? AND id_site=?" , new String []{   String.valueOf(id_user),String.valueOf(id_site)   });
		this.close();
	}
	
	
	//LOCK ALL SITES (BY USER)
	public void lockAllSites(int id_user){
		this.open();
		database.delete(Database.TABLE_SITES_WHITELIST, "id_user=?" , new String []{   String.valueOf(id_user)   });
		this.close();
	}
	
	
	
	
}
