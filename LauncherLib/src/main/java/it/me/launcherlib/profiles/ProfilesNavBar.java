package it.me.launcherlib.profiles;

import it.me.launcherlib.model.User;
import it.me.launcherlib.R;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

@SuppressLint("ResourceAsColor")
public class ProfilesNavBar {

	private static RelativeLayout itemUser1,itemUser2,itemUser3,itemUser4;
	public static User selectedUser;
	public static ArrayList<User> users;
	public Activity activity;
	
	
	public ProfilesNavBar(Activity activity,View view,ArrayList<User> users, Handler selectCallback){
		this.activity = activity;
		ProfilesNavBar.users = users;
		
		itemUser1 = (RelativeLayout) view.findViewById(R.id.itemUser1);
		itemUser2 = (RelativeLayout) view.findViewById(R.id.itemUser2);
		itemUser3 = (RelativeLayout) view.findViewById(R.id.itemUser3);
		itemUser4 = (RelativeLayout) view.findViewById(R.id.itemUser4);
		
		new ItemProfile(activity,itemUser1, users.get(0),selectCallback);
		new ItemProfile(activity,itemUser2, users.get(1),selectCallback);
		new ItemProfile(activity,itemUser3, users.get(2),selectCallback);
		new ItemProfile(activity,itemUser4, users.get(3),selectCallback);
		//setBackgrounds(view);

		
		
		setArrowOn(1);
		selectedUser =  users.get(0);
		
		
	}
	
	/*public void setBackgrounds(View view){
		itemUser1.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.background_profile_1));
		itemUser2.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.background_profile_2));
		itemUser3.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.background_profile_3));
		itemUser4.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.background_profile_4));
	}*/
	
	//SET SELECTED
	public static void setSelected(int id){
		setAllArrowsOff();
		setArrowOn(id);
		selectedUser =  users.get(id-1);
	}
	
	
	//SET ARROWS OFF
	private static void setAllArrowsOff(){
		itemUser1.findViewById(R.id.imageViewArrow).setVisibility(View.INVISIBLE);
		itemUser2.findViewById(R.id.imageViewArrow).setVisibility(View.INVISIBLE);
		itemUser3.findViewById(R.id.imageViewArrow).setVisibility(View.INVISIBLE);
		itemUser4.findViewById(R.id.imageViewArrow).setVisibility(View.INVISIBLE);
	}
	
	
	//SET ARROW ON
	private static void setArrowOn(int id){
		switch (id) {
		case 1:		itemUser1.findViewById(R.id.imageViewArrow).setVisibility(View.VISIBLE); 			break;
		case 2:		itemUser2.findViewById(R.id.imageViewArrow).setVisibility(View.VISIBLE); 			break;
		case 3:		itemUser3.findViewById(R.id.imageViewArrow).setVisibility(View.VISIBLE); 			break;
		case 4:		itemUser4.findViewById(R.id.imageViewArrow).setVisibility(View.VISIBLE); 			break;

		default:
			break;
		}
		
	}
	
}
