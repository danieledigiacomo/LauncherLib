package it.me.launcherlib.model;

import it.me.launcherlib.Path;
import it.me.launcherlib.Util;
import it.me.launcherlib.database.DBSession;
import it.me.launcherlib.database.DBUser;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class User{
	
	public static final int PASSWORD_ON = 1;
	public static final int PASSWORD_OFF = 0;
	
	
	private int id;
	private String nickname;
	private String password;
	private int id_background;
	private String avatar_type;
	private String avatar;
	private int has_password;
	private int play_time;
	private int play_time_remained;
	
	
	/*****COSTRUTTORI*****/
	public User(){}
	
	public User(int id,String nickname,String password,int id_background,String avatar_type,String avatar){
		this.setId(id);
		this.setNickname(nickname);
		this.setPassword(password);
		this.setIdBackground(id_background);
		this.setAvatarType(avatar_type);
		this.setAvatar(avatar);
		
	}
	
	/*****METODI GET*****/
	public int getId(){							return this.id;					}
	public String getNickname(){				return this.nickname;			}
	public String getPassword(){				return this.password;			}
	public int getIdBackground(){				return this.id_background;		}
	public String getAvatarType(){				return this.avatar_type;		}
	public String getAvatar(){					return this.avatar;				}
	public int getPlayTime(){					return this.play_time;			}
	
	public int getPlayTimeRemained(){			
		if(play_time_remained <10*1000){
			return 0;
		}else{
			return this.play_time_remained;	
		}
	}
	
	/*****METODI SET*****/
	public void setId(int id){							this.id=id;								}
	public void setNickname(String nickname){			this.nickname=nickname;					}
	public void setPassword(String password){			this.password = password;				}
	public void setIdBackground(int id_background){		this.id_background=id_background;		}
	public void setAvatarType(String avatar_type){		this.avatar_type=avatar_type;			}
	public void setAvatar(String avatar){				this.avatar=avatar;						}
	public void setHasPassword(int has_password){		this.has_password = has_password;		}
	public void setPlayTime(int play_time){  				this.play_time = play_time;				}
	public void setPlayTimeRemained(int play_time){  			this.play_time_remained = play_time;				}
	
	
	//HAS PASSWORD?
	public boolean hasPassword(){
		return this.has_password == PASSWORD_ON;
	}
	
	//HAS PLAY TIME?
	public boolean hasPlayTime(){
		return (this.play_time !=-1);
	}
	
	public static User getCurrentUser(Context context){
		DBUser database = new DBUser(context);
		User user = database.getUserbyId(getCurrentUserId(context)).get(0);
		return user;
	}
	
	public static int getCurrentUserId(Context context){
		DBSession dbSession = new DBSession(context);
		int currentUserId = dbSession.getUserId();
		return currentUserId;
	}

	public Bitmap getAvatarBitmap(Activity activity){
		Bitmap bitmap ;
		if(this.getAvatarType().equals(AvatarType.BUNDLE)){
			bitmap = BitmapFactory.decodeFile(Path.getAvatarPath(activity) + "avatar_"+this.getAvatar()+".png");
		}else{
			bitmap = Util.decodeBitmapFromFile(activity,this.getAvatar(), 120, 120);
		}
		return bitmap;
		
	}
	

	public String getAvatarPath(Activity activity){
		String path ="";
		if(this.getAvatarType().equals(AvatarType.BUNDLE)){
			path = Path.getAvatarPath(activity) + "avatar_"+this.getAvatar()+".png";
		}else{
			path = getAvatar();
		}
		return path;
		
	}
	
}