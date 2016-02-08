package it.me.launcherlib;


import it.me.launcherlib.model.Account;
import it.me.launcherlib.model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import android.util.Log;

public class UserSession {

	
	public static User user;

	public static void updateDataForGames(int user_id,String game_code,int score) {
		if(String.valueOf(score).equals("null")){
			score = 0;
		}
		
		String data="function getIdUtente(){"
						+ "return '"+String.valueOf(user_id)+"';};"
				  + "function getGame(){"
				  		+ "return '"+String.valueOf(game_code)+"';};"
				  + "function getScore(){"
				  		+ "return '"+String.valueOf(score)+"';};";
		
		File folder = Path.checkFolder(Path.SCRIPTS_PATH);
		File file = Path.checkFile(folder.getAbsolutePath(), Path.READDB_FILENAME);
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			fos.write(data.getBytes());
			fos.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			Log.e("Controller", e.getMessage() + e.getLocalizedMessage() + e.getCause());
		}
	}
	
	public static void updateDataForCloudGames(Account account, String androidId, int idNickname, String nickname) {
		String data="function getApiKey(){"
						+ "return '"+String.valueOf(account.getAPIKey())+"';};"
				  + "function getArticleCode(){"
				  		+ "return '"+String.valueOf(account.getTabletInfo().getProductCode())+"';};"
				  + "function getAndroidId(){"
				  		+ "return '"+String.valueOf(androidId)+"';};"
		  		  + "function getCdLocale(){"
				  		+ "return '"+String.valueOf(account.getUserLanguage())+"';};"
		  		  + "function getIdNickname(){"
				  		+ "return '"+String.valueOf(idNickname)+"';};"
				  + "function getNickname(){"
				  		+ "return '"+String.valueOf(nickname)+"';};";
		
		File folder = Path.checkFolder(Path.SCRIPTS_PATH);
		File file = Path.checkFile(folder.getAbsolutePath(), Path.AUTH_FILENAME);
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			fos.write(data.getBytes());
			fos.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			Log.e("Controller", e.getMessage() + e.getLocalizedMessage() + e.getCause());
		}
	}
	
	
	public static void updateData(User user) {
		String data = "user_id="+ user.getId()
						+ "\nuser_nickname=" + user.getNickname()
						+ "\nuser_avatar=" + user.getAvatar() 
						+ "\nrunning_ma=1\n";
		
		File folder = Path.checkFolder(Path.DATA_LISCIANI_PATH);
		File file = Path.checkFile(folder.getAbsolutePath(), Path.DATA_SESSION_FILENAME);
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			fos.write(data.getBytes());
			fos.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			Log.e("Controller", e.getMessage() + e.getLocalizedMessage() + e.getCause());
		}
	}
	
	public static void updateRunningMA(int runningMaStatus, int childrenPlayingStatus){
		
		File file = new File(Path.DATA_LISCIANI_PATH, Path.DATA_SESSION_FILENAME); 
		String result = "";
		try{              
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line; 
            
			while ((line = br.readLine()) != null && !line.contains("running_ma")){ 
				result += line + "\n";
			} 
			result += "running_ma=" + runningMaStatus + "\n";
			result += "children_playing=" + childrenPlayingStatus + "\n";
			updateData(result);
			br.close();
		}catch (IOException e){
		}
		
	}
	
	
	
	private static void updateData(String data){
		File folder = Path.checkFolder(Path.DATA_LISCIANI_PATH);
		File file = Path.checkFile(folder.getAbsolutePath(), Path.DATA_SESSION_FILENAME);
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			fos.write(data.getBytes());
			fos.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			Log.e("Controller", e.getMessage() + e.getLocalizedMessage() + e.getCause());
		}
	}
	
}
