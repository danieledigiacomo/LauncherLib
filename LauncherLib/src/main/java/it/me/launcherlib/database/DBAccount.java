package it.me.launcherlib.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import it.me.launcherlib.model.Account;

public class DBAccount{

	
	private SQLiteDatabase database;
	private Database dbHelper;
	
	public DBAccount(Context context) {

		dbHelper = new Database(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close(); 
		database.close();
	}

	//INSERT EMPTY RECORD
	public void insertEmtpyRecord(){
		this.open();
		ContentValues values = new ContentValues();
		values.put(Database.COLUMN_USERNAME, "");
		values.put(Database.COLUMN_PASSWORD, "");
		values.put(Database.COLUMN_API_KEY, "");
		values.put(Database.COLUMN_LASTNAME, "");
		values.put(Database.COLUMN_FIRSTNAME, "");
		values.put(Database.COLUMN_ARTICLE_CODE, "");
		values.put(Database.COLUMN_CD_LOCALE, "");
		
		database.insert(Database.TABLE_ACCOUNT, null,values);
		this.close();
	}
	
	//GET ALL ACCOUNTS
	public ArrayList<Account> getAccounts(){
		this.open();
		ArrayList<Account> accounts = new ArrayList<Account>();

		String query= "SELECT * FROM " + Database.TABLE_ACCOUNT;
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	
	    	Account account = cursorToAccount(cursor);
	    	accounts.add(account);
	    	cursor.moveToNext();
	    }
	    cursor.close();
	    this.close();
	    
	    return accounts;
	}
	
	//GET ALL ACCOUNTS
	public Account getAccount(){
		this.open();
		ArrayList<Account> accounts = new ArrayList<Account>();

		String query= "SELECT * FROM " + Database.TABLE_ACCOUNT;
		Cursor cursor = database.rawQuery(query, null);	
		if(cursor.getCount()>0){
			cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		    	Account account = cursorToAccount(cursor);
		    	accounts.add(account);
		    	cursor.moveToNext();
		    }
		    cursor.close();
		    this.close();
		    
		    return accounts.get(0);
	
		}else{
			return null;
		}

	}

	public String getValueOf(String column){
		this.open();

		String result = null;
		String query= "SELECT "+ column +" FROM " + Database.TABLE_ACCOUNT;
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	result = cursor.getString(cursor.getColumnIndex(column));
	    	cursor.moveToNext();
	    }
	    cursor.close();
	    this.close();
	    
		return result;
	}
	
	
	//UPDATE 
	public void update(String column,String value){
		this.open();
		ContentValues values = new ContentValues();
	    values.put(column, value);
	    database.update(Database.TABLE_ACCOUNT, values, null, null);
	    this.close();
	    
	}
	
	//LOGOUT
	public void logout(){
		this.open();
		ContentValues values = new ContentValues();
	    values.put(Database.COLUMN_API_KEY, "");
	    values.put(Database.COLUMN_PASSWORD, "");
	    database.update(Database.TABLE_ACCOUNT, values, null, null);
	    this.close();
	    
	}
	
	//UPDATE ACCOUNT
	public void updateAccount(Account account){
		this.open();
		ContentValues values = new ContentValues();
		
		Log.e("", account.getUsername());
	    Log.e("", account.getPassword());
	    Log.e("", account.getAPIKey());
	    Log.e("",account.getLastname());
	    Log.e("",account.getFirstname());
	    Log.e("", account.getTabletInfo().getProductCode());
	    Log.e("", account.getUserLanguage());
		
	    values.put(Database.COLUMN_USERNAME, account.getUsername());
	    values.put(Database.COLUMN_PASSWORD, account.getPassword());
	    values.put(Database.COLUMN_API_KEY, account.getAPIKey());
	    values.put(Database.COLUMN_LASTNAME, account.getLastname());
	    values.put(Database.COLUMN_FIRSTNAME, account.getFirstname());
	    values.put(Database.COLUMN_ARTICLE_CODE, account.getTabletInfo().getProductCode());
	    values.put(Database.COLUMN_CD_LOCALE, account.getUserLanguage());
	    database.update(Database.TABLE_ACCOUNT, values, null, null);
	    this.close();
	}
	
	//CURSOR
	private Account cursorToAccount(Cursor cursor) {
		Account account = new Account();
		account.setUsername(cursor.getString(cursor.getColumnIndex(Database.COLUMN_USERNAME)));
		account.setPassword(cursor.getString(cursor.getColumnIndex(Database.COLUMN_PASSWORD)));
		account.setAPIKey(cursor.getString(cursor.getColumnIndex(Database.COLUMN_API_KEY)));
		account.setLastName(cursor.getString(cursor.getColumnIndex(Database.COLUMN_LASTNAME)));
		account.setFirstname(cursor.getString(cursor.getColumnIndex(Database.COLUMN_FIRSTNAME)));
		account.setTabletInfo(cursor.getString(cursor.getColumnIndex(Database.COLUMN_ARTICLE_CODE)));
		account.setUserLanguage(cursor.getString(cursor.getColumnIndex(Database.COLUMN_CD_LOCALE)));
		
		return account;
	}


	  
}