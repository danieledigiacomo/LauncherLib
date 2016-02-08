package it.me.launcherlib.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

import it.me.launcherlib.widget.DialogDBNotFound;

public class Database extends SQLiteOpenHelper {
	  
	private static final String DATABASE_NAME = "/mnt/sdcard/udnt_secure/lisciani.db";
	private static final int DATABASE_VERSION = 1;
	  
	public static final String TABLE_USERS = "users";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NICKNAME = "nickname";
	public static final String COLUMN_PASSWORD = "password";
	public static final String COLUMN_ID_BACKGROUND = "id_background";
	public static final String COLUMN_AVATAR_TYPE = "avatar_type";
	public static final String COLUMN_AVATAR = "avatar";
	public static final String COLUMN_HAS_PASSWORD = "has_pass";
	public static final String COLUMN_PLAY_TIME = "play_time";
	public static final String COLUMN_PLAY_TIME_REMAINS = "play_time_remains";
	  
	public static final String TABLE_ITEMS = "items";
	public static final String COLUMN_ID_PARENT = "id_parent";
	public static final String COLUMN_LABEL = "label";
	public static final String COLUMN_STATUS = "status";
	public static final String COLUMN_CODE = "cd";
	public static final String COLUMN_APK = "apk";
	public static final String COLUMN_PACKAGE = "package";
	
	public static final String TABLE_GAMES = "games";
	public static final String COLUMN_CD_ITEM = "cd_item";
	
	public static final String TABLE_GAMES_SCORES = "users_games_scores";
	public static final String COLUMN_ID_GAME = "id_game";
	public static final String COLUMN_ID_USER = "id_user";
	public static final String COLUMN_SCORE = "score";
	
	public static final String TABLE_SESSION = "session";
	public static final String COLUMN_KEY = "key";
	public static final String COLUMN_VALUE = "value";
	
	public static final String TABLE_MYAPPS_BLACKLIST = "myapps_blacklist";
	
	
	public static final String TABLE_ITEMS_BLACKLIST = "users_items_blacklist";
	public static final String TABLE_SITES_WHITELIST= "sites_whitelist";
	public static final String TABLE_YOUTUBE_VIDEOS_WHITELIST= "youtube_videos_whitelist";
	
	public static final String TABLE_ARTICLES= "articles";
	public static final String COLUMN_UR_TYPE = "ur_type";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_DESCRIPTION = "description";
	
	public static final String TABLE_ACCOUNT = "account";
	public static final String COLUMN_USERNAME = "username";
	public static final String COLUMN_API_KEY = "api_key";
	public static final String COLUMN_LASTNAME = "lastname";
	public static final String COLUMN_FIRSTNAME = "firstname";
	public static final String COLUMN_ARTICLE_CODE = "cd_article";
	public static final String COLUMN_CD_LOCALE = "cd_locale";
	
	public static final String TABLE_CONFIG = "config";
	public static final String KEY_DATA_LISCIANI_PATH = "lsc_data_path";
	public static final String KEY_DATA_IMAGES_PATH = "lsc_image_path";
	public static final String KEY_DATA_AVATARS_PATH = "lsc_avatars_path";
	public static final String KEY_DATA_BACKGROUNDS_PATH = "lsc_backgrounds_path";
	public static final String KEY_DATA_SESSION_PATH = "lsc_session_path";
	public static final String KEY_DATA_FLAGS_PATH = "lsc_flags_path";
	public static final String KEY_DATA_DBAPI_PATH = "lsc_db_api_path";
	public static final String KEY_DATA_DB_PATH = "lsc_db_path";
	public static final String KEY_INSTALLER_PATH = "lsc_installer_path";
	public static final String KEY_APPS_FOLDER_PATH = "apps_folder_path";
	public static final String KEY_APPS_IMAGES_PATH = "apps_image_path";
	public static final String KEY_APPS_AUDIO_PATH = "apps_audio_path";
	public static final String KEY_APPS_VIDEO_PATH = "apps_video_path";
	public static final String KEY_SYS_PREINSTALL_PATH = "sys_preinstall_path";
	public static final String KEY_SYS_PACKAGE_LIST_PATH = "sys_pkg_list_file_path";
	public static final String KEY_SYS_PACKAGE_XML_PATH = "sys_pkg_xml_file_path";
	public static final String KEY_SYS_KEYEVENT_DONE = "sys_signal_keyevent_done";
	public static final String KEY_CLOUD_URL = "lsc_cloud_url";
	public static final String KEY_NOTIFY_STATUS_GENERIC = "notify_status_generic";
	public static final String KEY_NOTIFY_STATUS_APP = "notify_status_app_upgrades";
	public static final String KEY_NOTIFY_STATUS_ECO = "notify_status_eco_upgrades";
	public static final String KEY_NOTIFY_STATUS_PROMOS = "notify_status_promos";
	
	public static final String KEY_RINGTONE = "ringtone";
	public static final String KEY_BACKGROUND = "background";
	
	public static final String TABLE_PROMOS = "promos";
	public static final String COLUMN_URL = "url";
	public static final String COLUMN_TIMESTAMP = "timestamp";
	public static final String COLUMN_VIEWED = "viewed";
	public static final String COLUMN_DELETED = "deleted";
		
	public static final String TABLE_ECO_UPGRADES = "eco_upgrades";
	public static final String COLUMN_MANDATORY = "mandatory";
	public static final String COLUMN_REL_TIMESTAMP = "rel_timestamp";
	public static final String COLUMN_INSTALLED = "installed";
	public static final String COLUMN_DOWNLOADED = "downloaded";
	
	public static final String TABLE_APP_UPGRADES = "app_upgrades";
	public static final String COLUMN_SIZE = "size";
	public static final String COLUMN_MD5SUM = "md5sum";
	public static final String COLUMN_APP_CODE = "app_serial";
	public static final String COLUMN_APP_TITLE = "app_title";
	public static final String COLUMN_APP_VERSION= "app_version";
	
	public static final String TABLE_APP_INSTALLATIONS= "app_install";
	
	public static final String TABLE_PACKAGES_SPOOL= "packages_spool";
	public static final String COLUMN_ERROR_MESSAGE= "error_message";
	
	public static final String TABLE_PACKAGES_SPOOL_LOG= "packages_spool_log";
	public static final String COLUMN_VERSION = "version";
	public static final String COLUMN_READED = "readed";
	public static final String COLUMN_REPORT_CODE = "report_code";
	public static final String COLUMN_REPORT_DESC = "report_desc";

	public static final String COLUMN_PARENT_ID = "parent_id";
	public static final String COLUMN_PARENT_CODE = "parent_code";
	public static final String COLUMN_PARENT_LABEL = "parent_label";
	public static final String COLUMN_ITEM_ID = "item_id";
	public static final String COLUMN_ITEM_CODE = "item_code";
	public static final String COLUMN_ITEM_LABEL = "item_label";
	public static final String COLUMN_ITEM_STATUS = "item_status";
	public static final String COLUMN_METHOD_ID = "method_id";
	public static final String COLUMN_GAME_ID = "game_id";
	public static final String COLUMN_TYPE = "type";
	
	public static final String TABLE_VIEW_PACKAGES = "view_packages";
	public static final String COLUMN_TODO = "todo";
	
	
	public static final String TABLE_VIDEOS = "youtube_videos";
	public static final String COLUMN_THUMB_URL = "thumb_url";
	
	public static final String TABLE_VIDEOS_WHITELIST = "youtube_videos_whitelist";
	public static final String COLUMN_ID_YOUTUBE_VIDEO = "id_youtube_video";
	
	public static final String TABLE_SITES = "sites";
	public static final String COLUMN_ID_SITE = "id_site";

	public static final String TABLE_AUDIOS = "audios";
	public static final String COLUMN_PATH = "path";
	
	public static final String TABLE_AWARDS= "awards";
	public static final String TABLE_USERS_AWARDS = "awards_whitelist";
	public static final String COLUMN_TAKEN = "taken";
	public static final String COLUMN_PAGE = "page";
	public static final String COLUMN_X = "x";
	public static final String COLUMN_Y = "y";
	public static final String COLUMN_SCALE = "scale";
	public static final String COLUMN_ID_AWARD = "id_award";
	
	
	public static final String TABLE_DIARY_ENCLOSURES = "diary_enclosures";
	public static final String COLUMN_DIARY_PHOTO = "photo";
	public static final String COLUMN_DIARY_AUDIO = "audio";
	public static final String COLUMN_DIARY_OPINION = "opinion";
	public static final String TABLE_DIARY_NOTES = "diary_notes";
	
	public static final String TABLE_LOCALES = "locales";
	public static final String COLUMN_CD = "cd";
	
	public static final String TABLE_PACKAGES_BLACKLIST = "packages_blacklist";
	public static final String TABLE_PACKAGES_WHITELIST = "packages_users_whitelist";
	public static final String TABLE_USERS_NICKNAMES = "users_nicknames";
	public static final String COLUMN_ID_NICKNAME = "id_nickname";
	
	public static final String TABLE_FAVOURITES = "users_items_favourites";
	public static final String COLUMN_POSITION = "position";
	public static final String TABLE_FIREWALL = "firewall";
	public static final String TABLE_FIREWALL_CALL = "firewall_contacts_call";
	public static final String TABLE_FIREWALL_SMS = "firewall_contacts_sms";
	public static final String TABLE_FIREWALL_GOLD_NUMBER = "firewall_gold_numbers";
	public static final String COLUMN_NUMBER = "number";
	
	public static final String TABLE_READED_SMS = "readed_sms";

	
	public static final String TABLE_HISTORY = "history";
	public static final String COLUMN_PACKAGE_NAME = "package";
	
	
	public static final String DATABASE_CREATE_HISTORY = "create table IF NOT EXISTS "
			+ TABLE_HISTORY + "(" 
				+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ COLUMN_ID_USER + " INTEGER, "
				+ COLUMN_PACKAGE_NAME + " TEXT, "
				+ COLUMN_LABEL + " TEXT, "
				+ COLUMN_TIMESTAMP + " TEXT);";  

	public Database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		//database.execSQL(DATABASE_CREATE_HISTORY);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(Database.class.getName(),"Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
	}


	public static boolean check(Context context){
		boolean exists = new File(DATABASE_NAME).exists();
		if(!exists){
			//Toast.makeText(context,"OPS..IL DB NON ESISTE :(", Toast.LENGTH_SHORT).show();
			new DialogDBNotFound(context).show();
		}
		return exists;
	}


} 