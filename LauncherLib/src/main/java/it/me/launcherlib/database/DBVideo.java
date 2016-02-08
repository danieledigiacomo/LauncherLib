package it.me.launcherlib.database;

import it.me.launcherlib.database.Database;
import it.me.launcherlib.model.Video;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class DBVideo{

	
	private SQLiteDatabase database;
	private Database dbHelper;
	
	public DBVideo(Context context) {
		dbHelper = new Database(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close(); 
		database.close();
	}


	
	//GET VIDEOS BY ID USER
	public ArrayList<Video> getVideos(int idUser){
		
		this.open();
		ArrayList<Video> videos = new ArrayList<Video>();
		
		String query = "SELECT "+ 
				"youtube_videos.id as video_id, "+
				"youtube_videos.title as video_title, "+
				"youtube_videos.url as video_url, "+
				"youtube_videos.thumb_url as video_thumb_url, "+

				"CASE WHEN (youtube_videos_whitelist.id>0) THEN 0 "+
				"ELSE 1 "+
				"END AS locked "+

				"FROM youtube_videos "+

				"LEFT JOIN youtube_videos_whitelist  ON (youtube_videos.id =  youtube_videos_whitelist.id_youtube_video AND youtube_videos_whitelist.id_user =?); ";
		
		Cursor cursor = database.rawQuery(query, new String []{String.valueOf(idUser)});

		cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	Video video = cursorToVideo(cursor);
	    	videos.add(video);
	    	cursor.moveToNext();
	    }
	    cursor.close();
	    this.close();
	    return videos;
	}

	//GET VIDEOS BY ID USER
	public ArrayList<Video> getUnlockVideos(int idUser){
		
		this.open();
		ArrayList<Video> videos = new ArrayList<Video>();
		
		String query = "SELECT "+ 
				"youtube_videos.id as video_id, "+
				"youtube_videos.title as video_title, "+
				"youtube_videos.url as video_url, "+
				"youtube_videos.thumb_url as video_thumb_url, "+

				"CASE WHEN (youtube_videos_whitelist.id>0) THEN 0 "+
				"ELSE 1 "+
				"END AS locked "+

				"FROM youtube_videos "+

				"LEFT JOIN youtube_videos_whitelist  ON (youtube_videos.id =  youtube_videos_whitelist.id_youtube_video AND youtube_videos_whitelist.id_user =?); ";
		
		Cursor cursor = database.rawQuery(query, new String []{String.valueOf(idUser)});

		cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	Video video = cursorToVideo(cursor);
	    	if(!video.isLocked()){
	    		videos.add(video);
	    	}
	    	cursor.moveToNext();
	    }
	    cursor.close();
	    this.close();
	    return videos;
	}
	
	//VIDEO JUST INSERTED
	public boolean videoJustInserted(String url){
		this.open();
		String query = "SELECT * FROM youtube_videos WHERE url=?;"; 
		Cursor cursor = database.rawQuery(query, new String []{String.valueOf(url)});
		boolean result = cursor.getCount()>0;
		this.close();
		return result;
	}
	
	//INSERT VIDEO
	public void insertVideo(Video video){
		this.open();
		ContentValues values = new ContentValues();
		
		values.put(Database.COLUMN_TITLE, video.getTitle());
		values.put(Database.COLUMN_URL, video.getUrl());
		values.put(Database.COLUMN_THUMB_URL, video.getThumbUrl());
		
		database.insert(Database.TABLE_VIDEOS, null,values);
		this.close();
	}
	
	//DELETE VIDEO
	public void deleteVideo(int id){
		this.open();
		database.delete(Database.TABLE_VIDEOS, "id=? " , new String []{   String.valueOf(id)   });
		this.close();
	}
	
	public void deleteVideo(String url){
		this.open();
		database.delete(Database.TABLE_VIDEOS, "url=? " , new String []{   url   });
		this.close();
	}
	
	
	//UPDATE VIDEO
	public void updateVideo(int id, String title){
		this.open();
		ContentValues values = new ContentValues();
		values.put(Database.COLUMN_TITLE, title);
		database.update(Database.TABLE_VIDEOS, values, "id=?" , new String []{String.valueOf(id)});
		this.close();
		
	}
	
	//CURSOR
	private Video cursorToVideo(Cursor cursor) {
		int id = cursor.getInt(cursor.getColumnIndex("video_id"));
		String title = cursor.getString(cursor.getColumnIndex("video_title"));
		String url = cursor.getString(cursor.getColumnIndex("video_url"));
		String thumbUrl = cursor.getString(cursor.getColumnIndex("video_thumb_url"));
		int locked = cursor.getInt(cursor.getColumnIndex("locked"));
		Video video = new Video(title, url, thumbUrl);
		video.setId(id);
		video.setLocked(locked);
		return video;
	}


	
	

	
}