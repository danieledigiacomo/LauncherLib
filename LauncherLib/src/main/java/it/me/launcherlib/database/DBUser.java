package it.me.launcherlib.database;


import it.me.launcherlib.model.User;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBUser{

	private static int PARENT_ID = 0;
	
	private SQLiteDatabase database;
	private Database dbHelper;
	
	public DBUser(Context context) {
		dbHelper = new Database(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close(); 
		database.close();
	}

	//GET USER
	public User getUser(int id) {
		this.open();
		User user = null;
		Cursor cursor = database.rawQuery("SELECT * FROM users WHERE id = "+ id,null);
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	user = cursorToUser(cursor);
	    	cursor.moveToNext();
	    }
	    cursor.close();
	    this.close();
	    return user;
	}
	
	
	//GET USERS
	public ArrayList<User> getUserbyId(int id) {
		this.open();
		ArrayList<User> users = new ArrayList<User>();
		Cursor cursor = database.rawQuery("SELECT * FROM users WHERE id = "+ id,null);
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	User user = cursorToUser(cursor);
	    	users.add(user);
	    	cursor.moveToNext();
	    }
	    cursor.close();
	    this.close();
	    return users;
	}
	
	//GET USERS
	public ArrayList<User> getUsersExceptParent() {
		this.open();
		ArrayList<User> users = new ArrayList<User>();
		Cursor cursor = database.rawQuery("SELECT * FROM users WHERE id <>"+ DBUser.PARENT_ID,null);
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	User user = cursorToUser(cursor);
	    	users.add(user);
	    	cursor.moveToNext();
	    }
	    cursor.close();
	    this.close();
	    return users;
	}

	//GET PARENT
	public User getParent() {
		this.open();
		ArrayList<User> users = new ArrayList<User>();
		Cursor cursor = database.rawQuery("SELECT * FROM users WHERE id = "+ DBUser.PARENT_ID,null);
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	User user = cursorToUser(cursor);
	    	users.add(user);
	    	cursor.moveToNext();
	    }
	    cursor.close();
	    this.close();
	    return users.get(0);
	}
	
	//UPDATE USER
	public void updateUser(User user){
		this.open();
		ContentValues values = new ContentValues();

		String nickname = user.getNickname();
		int id_background = user.getIdBackground();
		String avatar_type= user.getAvatarType();
		String avatar = user.getAvatar();
		    
	    values.put(Database.COLUMN_NICKNAME, nickname);
	    values.put(Database.COLUMN_ID_BACKGROUND, id_background);
	    values.put(Database.COLUMN_AVATAR_TYPE, avatar_type);
	    values.put(Database.COLUMN_AVATAR, avatar);
		    
	    database.update(Database.TABLE_USERS, values, "id=?" , new String []{String.valueOf(user.getId())});
	   
	    this.close();
	}
	public void updateUser(int id, String dbColumn, String value){
		this.open();
		ContentValues values = new ContentValues();
		values.put(dbColumn, value);
		database.update(Database.TABLE_USERS, values, "id=?" , new String []{String.valueOf(id)});
	    this.close();
	}
	
	//UPDATE PARENT PASSWORD
	public void updateParentKey(String password){
		this.open();
		ContentValues values = new ContentValues();
		values.put(Database.COLUMN_PASSWORD, password);

		database.update(Database.TABLE_USERS, values, "id=0" , null);
	    this.close();
	}
	
	//UPDATE USER PASSWORD
	public void updateUserKey(User user, int hasPassword, String password){
		this.open();
		ContentValues values = new ContentValues();
	    
	    values.put(Database.COLUMN_HAS_PASSWORD, hasPassword);
	    if(hasPassword==1){
	    	values.put(Database.COLUMN_PASSWORD, password);
	    }else{
	    	values.put(Database.COLUMN_PASSWORD, "");
	    }
	    database.update(Database.TABLE_USERS, values, "id=?" , new String []{String.valueOf(user.getId())});
	    this.close();
	}
	
	//UPDATE USER PLAY TIME
	public void updateUserPlayTime(User user, int playTime){
		this.open();
		ContentValues values = new ContentValues();
	    values.put(Database.COLUMN_PLAY_TIME, playTime);
	    values.put(Database.COLUMN_PLAY_TIME_REMAINS, playTime);
	    database.update(Database.TABLE_USERS, values, "id=?" , new String []{String.valueOf(user.getId())});
	    this.close();
	}
	
	//UPDATE USER PLAY TIME REMAINED
	public void updateUserPlayTimeRemains(User user, long playTime){
		this.open();
		ContentValues values = new ContentValues();
	    values.put(Database.COLUMN_PLAY_TIME_REMAINS, playTime);

	    database.update(Database.TABLE_USERS, values, "id=?" , new String []{String.valueOf(user.getId())});
	    this.close();
	}
	
	
	//CURSOR
	private User cursorToUser(Cursor cursor) {
		User user = new User();
		user.setId(cursor.getInt(cursor.getColumnIndex(Database.COLUMN_ID)));
		user.setNickname(cursor.getString(cursor.getColumnIndex(Database.COLUMN_NICKNAME)));
		user.setPassword(cursor.getString(cursor.getColumnIndex(Database.COLUMN_PASSWORD)));
		user.setIdBackground(cursor.getInt(cursor.getColumnIndex(Database.COLUMN_ID_BACKGROUND)));
		user.setAvatarType(cursor.getString(cursor.getColumnIndex(Database.COLUMN_AVATAR_TYPE)));
		user.setAvatar(cursor.getString(cursor.getColumnIndex(Database.COLUMN_AVATAR)));
		user.setHasPassword(cursor.getInt(cursor.getColumnIndex(Database.COLUMN_HAS_PASSWORD)));
		user.setPlayTime(cursor.getInt(cursor.getColumnIndex(Database.COLUMN_PLAY_TIME)));
		user.setPlayTimeRemained(cursor.getInt(cursor.getColumnIndex(Database.COLUMN_PLAY_TIME_REMAINS)));
		return user;
	}
	  
}