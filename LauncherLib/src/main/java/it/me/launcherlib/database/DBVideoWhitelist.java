package it.me.launcherlib.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBVideoWhitelist {

	private SQLiteDatabase database;
	private Database dbHelper;
	
	public DBVideoWhitelist(Context context) {
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
	public void setUnLocked(int id_user,int id_youtube_video){
		this.open();
		ContentValues values = new ContentValues();
		values.put(Database.COLUMN_ID_USER, id_user);
		values.put(Database.COLUMN_ID_YOUTUBE_VIDEO, id_youtube_video);
		
		database.insert(Database.TABLE_VIDEOS_WHITELIST, null,values);
		this.close();
	}
	
	//SET LOCKED
	public void setLocked(int id_user,int id_youtube_video){
		this.open();
		database.delete(Database.TABLE_VIDEOS_WHITELIST, "id_user=? AND id_youtube_video=?" , new String []{   String.valueOf(id_user),String.valueOf(id_youtube_video)   });
		this.close();
	}
	
	
	//LOCK ALL VIDEOS (BY USER)
	public void lockAllVideos(int id_user){
		this.open();
		database.delete(Database.TABLE_VIDEOS_WHITELIST, "id_user=?" , new String []{   String.valueOf(id_user)   });
		this.close();
	}
	
	
	
	
}
