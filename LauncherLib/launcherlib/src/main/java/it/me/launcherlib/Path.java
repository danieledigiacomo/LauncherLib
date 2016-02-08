package it.me.launcherlib;


import android.annotation.SuppressLint;
import android.content.Context;

import java.io.File;
import java.io.IOException;

import it.me.launcherlib.model.Session;

public class Path {

	public static String DATA_LSC_BOOT = "/data/lsc_boot/";
	
	public static String DATA_LISCIANI_PATH = "/mnt/sdcard/udnt_secure/";
	public static String DATA_CONFIG_FILENAME = "config.txt";
	public static String DATA_SESSION_FILENAME = "session.txt";
	
	public static String SCRIPTS_PATH = "/mnt/sdcard/udnt_secure/tmp/";
	public static String READDB_FILENAME = "readDB.js";
	public static String AUTH_FILENAME = "auth.js";
	
	public static String CACHE_PACKAGES_PATH = "/mnt/sdcard/udnt_secure/cache/packages/";
	public static String CACHE_IMAGES_PATH = "/mnt/sdcard/udnt_secure/cache/images/";
	
	
	public static String IMAGES_PATH = "/mnt/sdcard/udnt_secure/images/";
	
	public static String ITEM_PATH = IMAGES_PATH + "items/";
	
	
	
	//CHECK FOLDER
	@SuppressLint("NewApi")
	public static File checkFolder(String folderPath){
		File folder = new File(folderPath);
		if(!folder.exists()){
			folder.mkdirs();
			
			folder.setReadable(true,false);
			folder.setWritable(true,false);
			folder.setExecutable(true, false);
			
			
		}
		return folder;
	}
	
	//CHECK FOLDER
	public static File checkFile(String filePath,String fileName){
		File file = new File(filePath, fileName);
		if(!file.exists()){
			try {
				file.createNewFile();
				
				file.setReadable(true,false);
				file.setWritable(true,false);
				file.setExecutable(true, false);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	
	
	public static String getAvatarPath(Context context){
		return IMAGES_PATH + "avatars/"+ Session.getAccount(context).getTabletInfo().getType()+"/";
	}
	
	public static String getBackgroundPath(Context context){
		return IMAGES_PATH + "backgrounds/"+ Session.getAccount(context).getTabletInfo().getType()+"/";
	}
	
	
	public static void deleteLSCBoot(){
		File directory = new File(DATA_LSC_BOOT);
		if(directory.exists()){
			File[] files = directory.listFiles();
			for (File file : files)	{
				if(file.exists()){
					file.delete();
				}
			}
		}
		
	}
	
	
	
	
	
}
