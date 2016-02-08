package it.me.launcherlib;


import it.me.launcherlib.database.DBAccount;
import it.me.launcherlib.database.DBConfig;
import it.me.launcherlib.database.DBSession;
import it.me.launcherlib.model.Account;
import it.me.launcherlib.model.Item;
import it.me.launcherlib.widget.CloudToast;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class AppTool {
	
	private static Context context;
	public static boolean CONTROL_PACKAGE = true;
	public static boolean NOT_CONTROL_PACKAGE = false;
	
	/*******************
	 *   LAUNCH APP    *
	 *******************/
	static public void launchApp(Activity activity,String packageName,boolean controlPkg){
		KILLING_ENABLED = true;
		
		AppTool.context = activity;
		
		if(packageExists(context,packageName)){
			Intent i = new Intent(Intent.ACTION_MAIN);
			PackageManager manager = context.getPackageManager();
			i = manager.getLaunchIntentForPackage(packageName);
			//i.addCategory(Intent.CATEGORY_LAUNCHER);
			if(i!=null){
				context.startActivity(i);
				Navigation.animate(activity, Navigation.ACTIVITY_LEFT);
			}
			
		}else{
			/*if(controlPkg){
				DialogController.text(activity, activity.getString(R.string.pacchetto_non_presente));
				
				DBItem database = new DBItem(activity);
				int id = database.getIdByPackage(packageName);
				database.setStatusOff(id);
			}*/
		
		}
	}
	
	/*******************
	 *   LAUNCH CLOUD   *
	 *******************/
	static public void launchCloudGame(Activity activity,Item item){
		DBAccount dbAccount = new DBAccount(activity);
		Account account = dbAccount.getAccount();
		String androidId = Util.getDeviceID(activity);
		DBSession dbSession = new DBSession(activity);
		int idNickname = dbSession.getIdNickname();
		String nickname = dbSession.getNickname();
		
		if(idNickname!=-1){
			UserSession.updateDataForCloudGames(account, androidId, idNickname, nickname);
			DBSession database = new DBSession(activity);
			database.updateSession(1, item.getCode(),item.getPackage());
			
			AppTool.launchApp(activity, item.getPackage(),CONTROL_PACKAGE);
		}else{
			CloudToast.show(activity);
		}
	}
	
	/********************
	 *   LAUNCH STORE   *
	 ********************/
	static public void launchStore(Activity activity,Item item){
		DBSession dbSession = new DBSession(activity);
		dbSession.updateSession(item.getCode());
		
		DBConfig db = new DBConfig(activity);
		String packageName = db.getValue(DBConfig.KEY_STORE_PACKAGE);
		String detailAct = db.getValue(DBConfig.KEY_STORE_DETAIL_ACT);
		
		if(packageExists(activity, packageName)){
		    Intent intent = new Intent();
			intent.setComponent(new ComponentName(packageName, detailAct));
		    activity.startActivity(intent);
		   // Navigation.animate(activity, Navigation.ACTIVITY_FADE);	
		}
	}
	
	/**********************
	 *   PACKAGE EXISTS   *
	 **********************/
	public static boolean packageExists(Context context, String targetPackage){
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(targetPackage, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

	
	public static boolean KILLING_ENABLED;
	
	
	//KILL APP BY PACKAGENAME
	static public void killApp(Context context,String packageName){  
		Log.e("Killing app......",packageName);
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		manager.killBackgroundProcesses(packageName);
		
		Log.e("done",packageName);
	}

	
	
	

	
	
	//UNINSTALL APP
	public static void uninstallApp(Context context,String packageName){
		Uri packageUri = Uri.parse("package:" + packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri);
        uninstallIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(uninstallIntent);
	
	}
		
	
	
	/*******************
	 * LAUNCH ANDOID OS
	 *******************/
	public static void launchAndroid(Activity activity){
		
		//UPDATE RUNNING MAINAPP
		UserSession.updateRunningMA(0,0);
				
		Intent barIntent1 = new Intent("display_navigation_bar");
		activity.sendBroadcast(barIntent1);
		
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		ComponentName cn = new ComponentName("com.android.launcher3", "com.android.launcher3.Launcher");
		intent.setComponent(cn);
		activity.startActivity(intent);  
		
	}
	
	/**********************
	 * CALL BUTTON SERVICE
	 **********************/
	private static String CALL_BUTTON_PACKAGE = "it.udanet.US4444EU";
	private static String CALL_BUTTON_SERVICE = "it.udanet.US4444EU.ButtonService";
	public static void startCallButtonService(Context context){
		if(packageExists(context, CALL_BUTTON_PACKAGE)){
			Intent intent = new Intent();
	          intent.setComponent(new ComponentName(CALL_BUTTON_PACKAGE, CALL_BUTTON_SERVICE));
	          ComponentName c = context.startService(intent);
		}
	}
	
	
	public class ExternalApp{
		
		public static final String CONSIGLI_UTILI_PRESCHOOL = "it.udanet.L0055IT10";
		public static final String CATALOGO_LISCIANI_PRESCHOOL = "it.udanet.L0058IT10";
		
		public static final String CONSIGLI_UTILI_JUNIOR = "it.udanet.L0056IT10";
		public static final String CATALOGO_LISCIANI_JUNIOR= "it.udanet.L0059IT10";
		
		public static final String CONSIGLI_UTILI_PRESCHOOL_POLACCO = "it.udanet.L0055PL10";
		public static final String CATALOGO_LISCIANI_PRESCHOOL_POLACCO = "it.udanet.L0058PL10";
		
		
		public static final String ANDROID_SETTINGS = "com.android.settings";
		public static final String APTOIDE = "com.aptoide.partners";
	}
	
	
	
	
	
}
