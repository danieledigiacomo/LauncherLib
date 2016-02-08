package it.me.launcherlib.database;



import it.me.launcherlib.Config;
import it.me.launcherlib.model.Item;

import java.util.ArrayList;
import java.util.HashMap;

import android.accounts.Account;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBItem{

	
	private SQLiteDatabase database;
	private Database dbHelper;
	private Context context;
	
	public DBItem(Context context) {
		dbHelper = new Database(context);
		this.context = context;
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close(); 
		database.close();
	}

	
	//GET ITEM BY ID
	public Item getItem(String code){
		this.open();
		Item item = null;
		
		String query="SELECT items.* , translations.text as item_label FROM " + Database.TABLE_ITEMS 
				+ " LEFT JOIN translations ON items.id_i18n_label=translations.id_i18n "
				+ " WHERE " + Database.COLUMN_CODE+ "=?";
		Cursor cursor = database.rawQuery(query, new String []{String.valueOf(code)});

		cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	item = cursorToSimpleItem(cursor);
	    	cursor.moveToNext();
	    }
	    cursor.close();
	    this.close();
	    return item;
	}
	
	//GET ALL ITEMS
	public ArrayList<Item> getItems( int id_user,String parentCode){ 
		DBAccount dbAccount = new DBAccount(context);
		String cdLocale = dbAccount.getValueOf(Database.COLUMN_CD_LOCALE);
		this.open();
		ArrayList<Item> items = new ArrayList<Item>();

		String query = "SELECT  "
				 + "     items.cd                                                                    item_cd, "
				 + "     items.cd_parent                                                             item_cd_parent, "
				 + "     items.sp_domain                                                             item_sp_domain, "
				 + "     items.sp_type                                                               item_sp_type, "
				 + "     items.sp_serial                                                             item_sp_serial, "
				 + "     items.sp_lang                                                               item_sp_lang, "
				 + "     items.id_i18n_label                                                         item_i18n_label, "
				 + "     items.z_index                                                               item_z_index, "
				 + "     items.flg_status                                                            item_flg_status, "
				 + "     tr_item_label.text                                                          item_label,	 "
				 + "     apks.file                                                                   apk_file, "
				 + "     apks.package                                                                apk_package, "
				 + "     apks.version_code                                                           apk_version_code, "
				 + "     apks.version_name                                                           apk_version_name, "
				 + "     apks.compiler_type                                                          apk_compiler_type, "
				 + "     apks.library_version                                                        apk_library_version, "
				 + "  CASE WHEN (users_items_blacklist.cd_item<>'') THEN 1 "
				 + "  ELSE 0 "
				 + "  END AS item_locked "
				 + "     FROM "
				 + "         items "
				 + "             LEFT JOIN apks ON ( apks.cd_item=items.cd ) "
				 + "             LEFT JOIN translations tr_item_label ON ( items.id_i18n_label = tr_item_label.id_i18n)  "
				 + "		     LEFT JOIN users_items_blacklist ON (items.cd=users_items_blacklist.cd_item AND users_items_blacklist.id_user=?) " 

				 + "      WHERE 	 "
				 + "             items.cd_parent = ? "
				 + "             AND "
				 + "                 ( "
				 + "                     (	 "
				 + "                          "
				 + "                          (items.sp_lang<>'EU' AND ( "
				 + "                                                     "
				 + "                                                     (CASE WHEN (SELECT count(id) as cid FROM translations WHERE translations.cd_locale=LOWER(items.sp_lang) and id_i18n=items.id_i18n_label) > 0  "
				 + "                                                             THEN (tr_item_label.cd_locale=LOWER(items.sp_lang))  "
				 + "                                                             ELSE ( tr_item_label.cd_locale = ? OR tr_item_label.cd_locale IS NULL ) "
				 + "                                                             END "
				 + "                                                             ) "
				 + "                                                 )  "
				 + "                         ) "
				 + "                         OR "
				 + "                        "
				 + "                         (items.sp_lang='EU' AND (	 "
				 + "                                                     CASE WHEN (SELECT count(id) as cid FROM translations WHERE cd_locale = ? and id_i18n=items.id_i18n_label) > 0 "
				 + "                                                             THEN (tr_item_label.cd_locale = ?) "
				 + "                                                              ELSE ( tr_item_label.cd_locale = ? OR tr_item_label.cd_locale IS NULL ) "
				 + "                                                              END "
				 + "                                                 )  "
				 + "                         )  "
				 + "                     ) "
				 + "                 ) "
				 + "     ORDER BY    items.z_index, "
				 + "                 (CASE   WHEN items.sp_type = 'F' THEN 0 "
				 + "                      WHEN items.sp_type = 'S'  THEN 1  "
				 + "                      WHEN items.sp_type = 'A'  THEN 2  "
				 + "                      END),  "
				 + "                 items.cd ;";
		
		//Cursor cursor = database.rawQuery(query, new String []{String.valueOf(parentCode)});
		Cursor cursor = database.rawQuery(query, new String []{String.valueOf(id_user),String.valueOf(parentCode), Config.DEFAULT_CD_LOCALE, cdLocale, cdLocale, Config.DEFAULT_CD_LOCALE});
		cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	//Item item = cursorToItem(cursor);
	    	Item item = cursorToApp(cursor);
	    	item.setParentCode(parentCode);
	    	items.add(item);
	    	cursor.moveToNext();
	    }
	    cursor.close();
	    this.close();
	    return items;
	}

	public HashMap<Integer, Item> getFavourites(){ 
		DBAccount dbAccount = new DBAccount(context);
		String cdLocale = dbAccount.getValueOf(Database.COLUMN_CD_LOCALE);
		this.open();
		HashMap<Integer, Item> items = new HashMap<Integer, Item>();

		String query = "SELECT  "
				 + "     items.cd                                                                    item_cd, "
				 + "     items.cd_parent                                                             item_cd_parent, "
				 + "     items.sp_domain                                                             item_sp_domain, "
				 + "     items.sp_type                                                               item_sp_type, "
				 + "     items.sp_serial                                                             item_sp_serial, "
				 + "     items.sp_lang                                                               item_sp_lang, "
				 + "     items.id_i18n_label                                                         item_i18n_label, "
				 + "     items.z_index                                                               item_z_index, "
				 + "     items.flg_status                                                            item_flg_status, "
				 + "     tr_item_label.text                                                          item_label,	 "
				 + "     apks.file                                                                   apk_file, "
				 + "     apks.package                                                                apk_package, "
				 + "     apks.version_code                                                           apk_version_code, "
				 + "     apks.version_name                                                           apk_version_name, "
				 + "     apks.compiler_type                                                          apk_compiler_type, "
				 + "     apks.library_version                                                        apk_library_version, "
				 + "     favourites.position                                                         item_position, "
				 + "  CASE WHEN (users_items_blacklist.cd_item<>'') THEN 1 "
				 + "  ELSE 0 "
				 + "  END AS item_locked "
				 + "     FROM "
				 + "         items "
				 + "             LEFT JOIN apks ON ( apks.cd_item=items.cd ) "
				 + "             LEFT JOIN translations tr_item_label ON ( items.id_i18n_label = tr_item_label.id_i18n)  "
				 + "             INNER JOIN users_items_favourites favourites ON ( items.cd = favourites.cd_item)  "
				 + "		     LEFT JOIN users_items_blacklist ON (items.cd=users_items_blacklist.cd_item AND users_items_blacklist.id_user=?) " 

				 + "      WHERE 	 "
				 + "                 ( "
				 + "                     (	 "
				 + "                          "
				 + "                          (items.sp_lang<>'EU' AND ( "
				 + "                                                     "
				 + "                                                     (CASE WHEN (SELECT count(id) as cid FROM translations WHERE translations.cd_locale=LOWER(items.sp_lang) and id_i18n=items.id_i18n_label) > 0  "
				 + "                                                             THEN (tr_item_label.cd_locale=LOWER(items.sp_lang))  "
				 + "                                                             ELSE ( tr_item_label.cd_locale = ? OR tr_item_label.cd_locale IS NULL ) "
				 + "                                                             END "
				 + "                                                             ) "
				 + "                                                 )  "
				 + "                         ) "
				 + "                         OR "
				 + "                        "
				 + "                         (items.sp_lang='EU' AND (	 "
				 + "                                                     CASE WHEN (SELECT count(id) as cid FROM translations WHERE cd_locale = ? and id_i18n=items.id_i18n_label) > 0 "
				 + "                                                             THEN (tr_item_label.cd_locale = ?) "
				 + "                                                              ELSE ( tr_item_label.cd_locale = ? OR tr_item_label.cd_locale IS NULL ) "
				 + "                                                              END "
				 + "                                                 )  "
				 + "                         )  "
				 + "                     ) "
				 + "                 ) "
				 + "     ORDER BY    item_position ;";
		
		//Cursor cursor = database.rawQuery(query, new String []{String.valueOf(parentCode)});
		Cursor cursor = database.rawQuery(query, new String []{String.valueOf(1), Config.DEFAULT_CD_LOCALE, cdLocale, cdLocale, Config.DEFAULT_CD_LOCALE});
		cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	Item item = cursorToItem(cursor);
	    	items.put(item.getPosition(), item);
	    	//items.add(item);
	    	cursor.moveToNext();
	    }
	    cursor.close();
	    this.close();
	    return items;
	}
	
	
	
	//GET ONLY APPS
	public ArrayList<Item> getAllApps( int id_user){
		this.open();
		ArrayList<Item> items = new ArrayList<Item>();
		String query = "SELECT  "
				 + "     items.cd                                                                    item_cd, "
				 + "     items.cd_parent                                                             item_cd_parent, "
				 + "     items.sp_domain                                                             item_sp_domain, "
				 + "     items.sp_type                                                               item_sp_type, "
				 + "     items.sp_serial                                                             item_sp_serial, "
				 + "     items.sp_lang                                                               item_sp_lang, "
				 + "     items.id_i18n_label                                                         item_i18n_label, "
				 + "     items.z_index                                                               item_z_index, "
				 + "     items.flg_status                                                            item_flg_status, "
				 + "     tr_item_label.text                                                          item_label,	 "
				 + "     apks.file                                                                   apk_file, "
				 + "     apks.package                                                                apk_package, "
				 + "     apks.version_code                                                           apk_version_code, "
				 + "     apks.version_name                                                           apk_version_name, "
				 + "     apks.compiler_type                                                          apk_compiler_type, "
				 + "     apks.library_version                                                        apk_library_version, "
	
				 +	" CASE WHEN (users_items_blacklist.cd_item<>'') THEN 1 "
				 + " ELSE 0 "
				 + " END AS item_locked "

 			    + "     FROM "
				 + "         items "
				 + "             LEFT JOIN apks ON ( apks.cd_item=items.cd ) "
				 + "             LEFT JOIN translations tr_item_label ON ( items.id_i18n_label = tr_item_label.id_i18n)  "
	//			 + "             LEFT JOIN users_items_favourites favourites ON ( items.cd = favourites.cd_item)  "
	 			 + "					LEFT JOIN users_items_blacklist ON (items.cd=users_items_blacklist.cd_item AND users_items_blacklist.id_user=?) " 

				 + "      WHERE 	 "
	//			 + "             favourites.id_user = ? "
	//			 + "             AND "
				 + "                 ( "
				 + "                     (	 "
				 + "                          "
				 + "                          (items.sp_lang<>'EU' AND ( "
				 + "                                                     "
				 + "                                                     (CASE WHEN (SELECT count(id) as cid FROM translations WHERE translations.cd_locale=LOWER(items.sp_lang) and id_i18n=items.id_i18n_label) > 0  "
				 + "                                                             THEN (tr_item_label.cd_locale=LOWER(items.sp_lang))  "
				 + "                                                             ELSE ( tr_item_label.cd_locale = ? OR tr_item_label.cd_locale IS NULL ) "
				 + "                                                             END "
				 + "                                                             ) "
				 + "                                                 )  "
				 + "                         ) "
				 + "                         OR "
				 + "                        "
				 + "                         (items.sp_lang='EU' AND (	 "
				 + "                                                     CASE WHEN (SELECT count(id) as cid FROM translations WHERE cd_locale = ? and id_i18n=items.id_i18n_label) > 0 "
				 + "                                                             THEN (tr_item_label.cd_locale = ?) "
				 + "                                                              ELSE ( tr_item_label.cd_locale = ? OR tr_item_label.cd_locale IS NULL ) "
				 + "                                                              END "
				 + "                                                 )  "
				 + "                         )  "
				 + "                     ) "
				 + "                 ) "
				 + "     ORDER BY    items.z_index, "
				 + "                 (CASE   WHEN items.sp_type = 'F' THEN 0 "
				 + "                      WHEN items.sp_type = 'S'  THEN 1  "
				 + "                      WHEN items.sp_type = 'A'  THEN 2  "
				 + "                      END),  "
				 + "                 items.cd ;";
		
		/*String query = "SELECT "+
						"items.id as item_id, "+
						"items.id_parent as item_id_parent, "+
						
						"CASE WHEN (items.status=='1') THEN 1 "+
						"ELSE 0 "+
						"END AS installed, "+
						
						"items.label as item_label, "+
						"items.code as item_code,"+
						
						"apks.apk as item_apk, "+
						"apks.package as item_package, "+
						
						"CASE WHEN (items_blacklist.id_item>0) THEN 1 "+
						"ELSE 0 "+
						"END AS locked "+
						
						"FROM items "+
						
						
						"LEFT JOIN apks ON items.id=apks.id_item "+
		                "LEFT JOIN items_blacklist ON (items.id=items_blacklist.id_item AND items_blacklist.id_user=?) "+
						
						"WHERE item_package NOT NULL AND item_id_parent NOT IN (-1);";
		*/
		/*String query2 = "SELECT "+
				"items.id as item_id, "+
				
				"CASE WHEN (items.status==1) THEN 1 "+
				"ELSE 0 "+
				"END AS installed, "+
				
				"items.label as item_label, "+
				"items.code as item_code,"+
				"items.apk as item_apk,"+
				"items.package as item_package, "+
				
				
				"CASE WHEN (items_blacklist.id>0) THEN 1 "+
				"ELSE 0 "+
				"END AS locked, "+
				
				"games.id as game_id, "+
				
				"games_scores.score as game_score "+
				
				"FROM items left join games ON items.id=games.id_item "+
								"LEFT JOIN games_scores ON ( games_scores.id_game=games.id AND  games_scores.id_user=?) "+
				                "LEFT JOIN items_blacklist ON (items.id=items_blacklist.id_item AND items_blacklist.id_user=?) ;"+
				
				
				"WHERE item_package NOT NULL;";
		*/
		
		Log.e("","QUERY:" + query);
		Cursor cursor = database.rawQuery(query, new String []{String.valueOf(id_user)});

		cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	Item item = cursorToApp(cursor);
	    	items.add(item);
	    	cursor.moveToNext();
	    }
	    cursor.close();
	    this.close();
	    return items;
	}
	
	/*public int getIdByPackage(String packageName){
		this.open();
		String query = "SELECT id_item FROM apks WHERE " + Database.COLUMN_PACKAGE + "=?";
		Cursor cursor = database.rawQuery(query, new String []{ packageName});
		cursor.moveToFirst();
		int id = cursor.getInt(cursor.getColumnIndex(Database.COLUMN_ID_ITEM));
	    cursor.close();
	    this.close();
	    return id;
	}*/
	
	public void setStatusOff(int id){
		this.open();
		ContentValues values = new ContentValues();
	    values.put(Database.COLUMN_STATUS, 0);
	    database.update(Database.TABLE_ITEMS, values, Database.COLUMN_ID + "=?", new String []{String.valueOf(id)});
	    this.close();
	}
	
	//CURSOR
	private Item cursorToItem(Cursor cursor) {
		Item item = new Item();
		item.setLabel(cursor.getString(cursor.getColumnIndex("item_label")));
		item.setCode(cursor.getString(cursor.getColumnIndex("item_cd")));
		item.setApk(cursor.getString(cursor.getColumnIndex("apk_file")));
		item.setType(cursor.getString(cursor.getColumnIndex("item_sp_type")));
		item.setPackage(cursor.getString(cursor.getColumnIndex("apk_package")));
		item.setLocked(cursor.getInt(cursor.getColumnIndex("item_locked")));
		item.setPosition(cursor.getInt(cursor.getColumnIndex("item_position")));
		item.setInstalled(1);
		
		/*item.setGameId(cursor.getString(cursor.getColumnIndex("game_id")));
		int score = cursor.getInt(cursor.getColumnIndex("game_score"));
		if(score>5){	score = 5;		}
	  	item.setGameScore(score);*/
	  	
	  	return item;
	}
	
	//CURSOR
	private HashMap<Integer, Item> cursorToHash(Cursor cursor) {
		HashMap<Integer, Item> hash = new HashMap<Integer, Item>();
		Item item = new Item();
		//item.setId(cursor.getInt(cursor.getColumnIndex("item_id")));
		item.setLabel(cursor.getString(cursor.getColumnIndex("item_label")));
		item.setCode(cursor.getString(cursor.getColumnIndex("item_cd")));
		item.setApk(cursor.getString(cursor.getColumnIndex("apk_file")));
		item.setPackage(cursor.getString(cursor.getColumnIndex("apk_package")));
		int position = cursor.getInt(cursor.getColumnIndex("item_position"));
		item.setPosition(position);
		item.setInstalled(1);
		hash.put(position, item);
	  	
	  	return hash;
	}
	

	//SIMPLE CURSOR (used by getItem((int id)))
	private Item cursorToSimpleItem(Cursor cursor) {
		Item item = new Item();
		item.setParentCode(cursor.getString(cursor.getColumnIndex("cd_parent")));
		item.setLabel(cursor.getString(cursor.getColumnIndex("item_label")));
		item.setCode(cursor.getString(cursor.getColumnIndex("cd")));

		return item;
	}
		
	//SIMPLE CURSOR (used by getItem((int id)))
	private Item cursorToApp(Cursor cursor) {
		Item item = new Item();
		
		item.setType(cursor.getString(cursor.getColumnIndex("item_sp_type")));
		item.setLabel(cursor.getString(cursor.getColumnIndex("item_label")));
		item.setCode(cursor.getString(cursor.getColumnIndex("item_cd")));
		item.setLocked(cursor.getInt(cursor.getColumnIndex("item_locked")));
		item.setPackage(cursor.getString(cursor.getColumnIndex("apk_package")));
		return item;
	}
	  
}