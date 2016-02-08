package it.me.launcherlib.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import it.me.launcherlib.Logger;

public class Nickname implements Serializable{

	public static String BUNDLE_NICKNAME= "NICKNAME";
	
	private int id;
	private String name;
	private String password;
	private boolean hasAvatar;
	
	public Nickname(){}
	
	public Nickname(JSONObject obj){
		try {
			this.setId(obj.getInt("id"));
			this.setName(obj.getString("nickname"));
			this.setPassword(obj.getString("password"));
			int flag1 = obj.getInt("flg_avatar_db");
			int flag2 = obj.getInt("flg_avatar_fs");
			boolean hasAvatar = (flag1 + flag2 == 2);
			this.setHasAvatar(hasAvatar);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}		
	}
	
	public static ArrayList<Nickname> createList( JSONArray jArray){
		ArrayList<Nickname> nicks = new ArrayList<Nickname>();
		int count = jArray.length();
		for(int i=0; i<count; i++){
			try {
				Nickname nick = new Nickname(jArray.getJSONObject(i));
				nicks.add(nick);
				
			} catch (JSONException e) {
				Logger.createErrorLog("NICKNAME CONSTRUCTOR (Nicks list)", "JSONException", e.getMessage());
			}
		}
		return nicks;
	}
	
	
	
	public int getId() {								return id;				}
	public String getName() {							return name;			}
	public String getPassword() {						return password;		}

	public boolean hasAvatar() {						return hasAvatar;		}
	
	public void setId(int id) {								this.id = id;					}
	public void setName(String name) {						this.name = name;				}
	public void setHasAvatar(boolean hasAvatar) {			this.hasAvatar = hasAvatar;		}
	public void setPassword(String password) {				this.password = password;		}
	
	
	
	
	/*
	 * 
	 * {
        "flg_avatar_db": 1,
        "avatar_ext": "jpg",
        "id": 1,
        "utc_creation": 1427878102,
        "flg_avatar_fs": 1,
        "nickname": "ccccccag"
      }
	 * 
	 */
}




