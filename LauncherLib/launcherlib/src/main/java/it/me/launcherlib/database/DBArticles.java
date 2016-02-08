package it.me.launcherlib.database;

import it.me.launcherlib.model.Article;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBArticles {

	
	private SQLiteDatabase database;
	private Database dbHelper;
	
	public DBArticles(Context context) {
		dbHelper = new Database(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close(); 
		database.close();
	}

	
	//GET ITEM BY ID
	public Article getArticle(String code){
		this.open();
		Article article = null;
		
		String query="SELECT * FROM articles WHERE code=?";
		Cursor cursor = database.rawQuery(query, new String []{String.valueOf(code)});

		if(cursor.getCount()>0){
			cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		    	article = cursorToArticle(cursor);
		    	cursor.moveToNext();
		    }
		}
		else{
			return null;
		}
		
		
	    cursor.close();
	    this.close();
	    return article;
	}
	
	
	//CURSOR 
	private Article cursorToArticle(Cursor cursor) {
		
		Article article = new Article();
		article.setId(cursor.getInt(cursor.getColumnIndex(Database.COLUMN_ID)));
		article.setCode(cursor.getString(cursor.getColumnIndex(Database.COLUMN_CODE)));
		//article.setUrType(cursor.getString(cursor.getColumnIndex(Database.COLUMN_UR_TYPE)));
		article.setTitle(cursor.getString(cursor.getColumnIndex(Database.COLUMN_TITLE)));
		article.setDescription(cursor.getString(cursor.getColumnIndex(Database.COLUMN_DESCRIPTION)));
			
		return article;
	}

	
	
}