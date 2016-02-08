package it.me.launcherlib.database;

import it.me.launcherlib.model.HistoryItem;
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DbHistory {

	private SQLiteDatabase database;
	private Database dbHelper;
	
	public DbHistory(Context context) {
		dbHelper = new Database(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
		
		database.execSQL(Database.DATABASE_CREATE_HISTORY);
	}
	
	public void close() {
		dbHelper.close(); 
		database.close();
	}

	//INSERT SITE
	public void insertItem(HistoryItem item){
		this.open();
		ContentValues values = new ContentValues();
		
		values.put(DatabaseHistory.COLUMN_ID_USER, item.getIdUser());
		values.put(DatabaseHistory.COLUMN_PACKAGE_NAME, item.getPackageName());
		values.put(DatabaseHistory.COLUMN_LABEL, item.getLabel());
		values.put(DatabaseHistory.COLUMN_TIMESTAMP, item.getTime());
		
		database.insert(DatabaseHistory.TABLE_HISTORY, null,values);
		this.close();
	}
	
	
	
	
	
}
