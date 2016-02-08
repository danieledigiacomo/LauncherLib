package it.me.launcherlib.database;


import it.me.launcherlib.model.Site;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBSite{

	
	private SQLiteDatabase database;
	private Database dbHelper;
	
	public DBSite(Context context) {
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
	public ArrayList<Site> getSites(int idUser){
		
		this.open();
		ArrayList<Site> sites = new ArrayList<Site>();
		
		String query = "SELECT "+ 
				"sites.id as site_id, "+
				"sites.title as site_title, "+
				"sites.url as site_url, "+
				"sites.thumb_url as site_thumb_url, "+

				"CASE WHEN (sites_whitelist.id>0) THEN 0 "+
				"ELSE 1 "+
				"END AS locked "+

				"FROM sites "+

				"LEFT JOIN sites_whitelist  ON (sites.id =  sites_whitelist.id_site AND sites_whitelist.id_user =?); ";
		
		Cursor cursor = database.rawQuery(query, new String []{String.valueOf(idUser)});

		cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	Site site = cursorToSite(cursor);
	    	sites.add(site);
	    	cursor.moveToNext();
	    }
	    cursor.close();
	    this.close();
	    return sites;
	}

	//GET SITE WITH URL
	public ArrayList<Site> getSiteWithUrl(String url){
		this.open();
		ArrayList<Site> sites = new ArrayList<Site>();
		
		String query = "SELECT * FROM sites WHERE url=?;"; 
		
		Cursor cursor = database.rawQuery(query, new String []{String.valueOf(url)});

		cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	Site site = simpleCursorToSite(cursor);
	    	sites.add(site);
	    	cursor.moveToNext();
	    }
	    cursor.close();
	    this.close();
	    return sites;
	}
	
	//SITE JUST INSERTED
	public boolean siteJustInserted(String url){
		this.open();
		String query = "SELECT * FROM sites WHERE url=?;"; 
		Cursor cursor = database.rawQuery(query, new String []{String.valueOf(url)});
		boolean result = cursor.getCount()>0;
		this.close();
		return result;
	}
	
	//INSERT SITE
	public void insertSite(Site site){
		this.open();
		ContentValues values = new ContentValues();
		
		values.put(Database.COLUMN_TITLE, site.getTitle());
		values.put(Database.COLUMN_URL, site.getUrl());
		values.put(Database.COLUMN_THUMB_URL, site.getThumbUrl());
		
		database.insert(Database.TABLE_SITES, null,values);
		this.close();
	}
	
	//DELETE SITE
	public void deleteSite(int id){
		this.open();
		database.delete(Database.TABLE_SITES, "id=? " , new String []{   String.valueOf(id)   });
		this.close();
	}
	//DELETE SITE
	public Site deleteSite(String url){
		ArrayList<Site> sites = this.getSiteWithUrl(url);
		this.open();
		database.delete(Database.TABLE_SITES, "url=? " , new String []{  url   });
		this.close();
		
		return sites.get(0);
	}
	
	//UPDATE SITE 
	public void updateSite(int id, String title){
		this.open();
		ContentValues values = new ContentValues();
		values.put(Database.COLUMN_TITLE, title);
		database.update(Database.TABLE_SITES, values, "id=?" , new String []{String.valueOf(id)});
		this.close();
		
	}
	
	//CURSOR
	private Site cursorToSite(Cursor cursor) {
		int id = cursor.getInt(cursor.getColumnIndex("site_id"));
		String title = cursor.getString(cursor.getColumnIndex("site_title"));
		String url = cursor.getString(cursor.getColumnIndex("site_url"));
		String thumbUrl = cursor.getString(cursor.getColumnIndex("site_thumb_url"));
		int locked = cursor.getInt(cursor.getColumnIndex("locked"));
		
		Site site = new Site(title, url, thumbUrl);
		site.setId(id);
		site.setLocked(locked);
		
		return site;
	}

	//CURSOR
	private Site simpleCursorToSite(Cursor cursor) {
		int id = cursor.getInt(cursor.getColumnIndex("id"));
		String title = cursor.getString(cursor.getColumnIndex("title"));
		String url = cursor.getString(cursor.getColumnIndex("url"));
		String thumbUrl = cursor.getString(cursor.getColumnIndex("thumb_url"));
		
		Site site = new Site(title, url, thumbUrl);
		site.setId(id);
		
		return site;
	}
	
	

	
}