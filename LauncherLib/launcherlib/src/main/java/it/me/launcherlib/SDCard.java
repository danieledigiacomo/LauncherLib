package it.me.launcherlib;




import android.content.Context;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;

import it.me.launcherlib.model.StorageItem;
import it.me.launcherlib.model.User;

public class SDCard {
	
	public static String SECONDARY_SDCARD_PATH = "/mnt/extsd/";
	
	public static String DATA_LISCIANI_IMAGES = "/data/lisciani/images/";
	
	
	public static String PHOTO_PATH;
	public static String AUDIO_PATH;
	public static String VIDEO_PATH;
	
	public static String USER_PATH;// = "utente1/";
	public static String APP_PATH = "CAMERA/";
	
	public static String USER_PHOTO_PATH = PHOTO_PATH + USER_PATH;
	public static String USER_AUDIO_PATH = AUDIO_PATH + USER_PATH;
	public static String USER_VIDEO_PATH = VIDEO_PATH + USER_PATH;
	
	public static String APP_PHOTO_PATH = USER_PHOTO_PATH + APP_PATH;
	public static String APP_AUDIO_PATH = USER_AUDIO_PATH;// + APP_PATH;
	public static String APP_VIDEO_PATH = USER_VIDEO_PATH + APP_PATH;
	
	//GET ITEMS
	public static ArrayList<StorageItem> getItems(String mainPath){			
		ArrayList<StorageItem> items = new ArrayList<StorageItem>();
		File folder = new File(mainPath);
		if(!folder.exists()){
			folder.mkdir();
		}
	    String []files_ = folder.list();
	    if(files_!=null){
	    	
	    	Arrays.sort(files_, Collections.reverseOrder());
	    	ArrayList<String> files = new ArrayList<String>(Arrays.asList(files_));
		    
		    for (String path : files) {
				File file = new File(mainPath + "/" + path);
				
				if(file.isDirectory()){
					
					
					ArrayList<StorageItem> subItems = getItems(file.getAbsolutePath());
					Logger.createInfoLog("SDCARD", "Getting items", "DIRECTORY: " + file.getAbsolutePath() + "\nSIZE: " + subItems.size());
					
					for (StorageItem subItem : subItems) {
						
						Logger.createInfoLog("SDCARD", "Getting items", subItem.getPath());
							
						items.add(subItem);
						
					}
					
					
				}else{
					StorageItem item = new StorageItem(itemType(file.isDirectory()), file.getAbsolutePath());
					items.add(item);	
				}
				
				
			}
	    	
		    if(files_!=null){
		    	files_ = null;
		    }
		    Runtime.getRuntime().gc();

	    }
	    
	    return items;
			
	}
	
	//ITEM TYPE
	private static int itemType(boolean isFolder){
		if(isFolder){
			return StorageItem.FOLDER_TYPE;
		}else{
			return StorageItem.PHOTO_TYPE;
		}
	}
	
	//GET PHOTOS
	public static ArrayList<String> getPhotos(){
    	File thumbFolder = new File(USER_PHOTO_PATH);
		if(!thumbFolder.exists()){
			thumbFolder.mkdir();
		}
	    String []files_ = thumbFolder.list();
	    
	    for (String path : files_) {
			File file = new File(path);
			if(file.isDirectory()){}
		}
	    
	    Arrays.sort(files_, Collections.reverseOrder());
	    ArrayList<String> files = new ArrayList<String>(Arrays.asList(files_));
    	
	    if(files_!=null){
	    	files_ = null;
	    }
	    Runtime.getRuntime().gc();
	    return files;
	}
	
	
	//SETUP
	public static void setup(String appFolder,Context context){
		 if(!Config.INITIALIZED){
	        	Config config = Config.create(context);
	            SDCard.setConfigPaths(config);
	            Config.INITIALIZED = true;
	        }
        	int userId = User.getCurrentUserId(context);
            SDCard.setUserPath(userId);

            if(appFolder!=null){
	        	SDCard.setAppPath(appFolder);
	        }
	}
	//SETUP
	public static void setup(int userId,String appFolder,Context context){
		 if(!Config.INITIALIZED){
	        	Config config = Config.create(context);
	            SDCard.setConfigPaths(config);
	            Config.INITIALIZED = true;
	        }
            SDCard.setUserPath(userId);

            if(appFolder!=null){
	        	SDCard.setAppPath(appFolder);
	        }
	}
	
	
	public static void setConfigPaths(Config config){		
		SDCard.PHOTO_PATH = config.getAppImagesPath() + "/";
		SDCard.AUDIO_PATH = config.getAppAudioPath() + "/";
		SDCard.VIDEO_PATH = config.getAppVideoPath() + "/";
		
		checkFolder(SDCard.PHOTO_PATH);
		checkFolder(SDCard.AUDIO_PATH);
		checkFolder(SDCard.VIDEO_PATH);
		
	}
	
	public static void setUserPath(int userId){		
		SDCard.USER_PATH = userId + "/";
		checkFolder(SDCard.USER_PATH);
		
		USER_PHOTO_PATH = PHOTO_PATH + USER_PATH;
		USER_AUDIO_PATH = AUDIO_PATH + USER_PATH;
		USER_VIDEO_PATH = VIDEO_PATH + USER_PATH;
		
		
		checkFolder(SDCard.USER_PHOTO_PATH);
		checkFolder(SDCard.USER_AUDIO_PATH);
		checkFolder(SDCard.USER_VIDEO_PATH);
	}
	
	
	public static void setAppPath(String path){
		APP_PHOTO_PATH = USER_PHOTO_PATH + path + "/";
		APP_VIDEO_PATH = USER_VIDEO_PATH + path + "/";
		checkFolder(SDCard.APP_PHOTO_PATH);
		checkFolder(SDCard.APP_VIDEO_PATH);
		
	}
	
	private static void checkFolder(String folderPath){
		File folder = new File(folderPath);
		if(!folder.exists()){
			folder.mkdirs();
		}
	}
	
	 public static HashSet<String> getExternalMounts() {
	    final HashSet<String> out = new HashSet<String>();
	    String reg = "(?i).*vold.*(vfat|ntfs|exfat|fat32|ext3|ext4).*rw.*";
	    String s = "";
	    try {
	        final Process process = new ProcessBuilder().command("mount")
	                .redirectErrorStream(true).start();
	        process.waitFor();
	        final InputStream is = process.getInputStream();
	        final byte[] buffer = new byte[1024];
	        while (is.read(buffer) != -1) {
	            s = s + new String(buffer);
	        }
	        is.close();
	    } catch (final Exception e) {
	        e.printStackTrace();
	    }

	    // parse output
	    final String[] lines = s.split("\n");
	    for (String line : lines) {
	        if (!line.toLowerCase(Locale.US).contains("asec")) {
	            if (line.matches(reg)) {
	                String[] parts = line.split(" ");
	                for (String part : parts) {
	                    if (part.startsWith("/"))
	                        if (!part.toLowerCase(Locale.US).contains("vold"))
	                            out.add(part);
	                }
	            }
	        }
	    }
	    return out;
	}
	 
	
	
	
	
}
