package it.me.launcherlib;


import it.me.launcherlib.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class Navigation {

	public static final int FRAGMENT_RIGHT = 0;
	public static final int FRAGMENT_LEFT = 1;
	public static final int FRAGMENT_NO_ANIMATION = 2;
	public static final int FRAGMENT_TOP = 3;
	public static final int FRAGMENT_BOTTOM = 4;
	public static final int FRAGMENT_FADE = 5;
	
	public static final int ACTIVITY_FADE = 20;
	public static final int ACTIVITY_LEFT = 21;
	public static final int ACTIVITY_RIGHT = 22;
	public static final int ACTIVITY_TOP = 23;
	public static final int ACTIVITY_BOTTOM = 24;
	public static final int ACTIVITY_SPLASH= 25;
	
	
	public static final int BACK_TO_PREVIOUS_FOLDER = 10;
	public static final int BACK_TO_HOME = 11;
	public static final int BACK_FROM_PROFILE = 12;
	public static final int BACK_TO_PARENTS_HOME = 13;
	public static final int BACK_TO_PARENTAL_PROFILES_MENU = 14;
	
	//GO TO FRAGMENT
	@SuppressLint("NewApi")
	public static void goToFragment(Activity activity, Fragment fragment, int containerResId, int direction){	
		
		  FragmentManager fragmentManager = activity.getFragmentManager();
		  FragmentTransaction transaction = fragmentManager.beginTransaction();
		  
		  switch (direction) {
			
		  		case FRAGMENT_LEFT:
		  			transaction.setCustomAnimations(R.anim.frg_left_a, R.anim.frg_left_b);
		  			break;
	
		  		case FRAGMENT_RIGHT:
		  			 transaction.setCustomAnimations(R.anim.frg_right_a, R.anim.frg_right_b);
		  			 break;
		  		
		  		case FRAGMENT_TOP:
		  			 transaction.setCustomAnimations(R.anim.frg_top_a, R.anim.frg_top_b);
		  			 break;
		  			
		  		case FRAGMENT_BOTTOM:
		  			 transaction.setCustomAnimations(R.anim.frg_bottom_a, R.anim.frg_bottom_b);
		  			break;
		  			
		  		case FRAGMENT_FADE:
		  			 transaction.setCustomAnimations(R.anim.frg_fade_a, R.anim.frg_fade_b);
		  			 break;
		  			
		  		case FRAGMENT_NO_ANIMATION:
		  			 transaction.setCustomAnimations(R.anim.frg_no_anim, R.anim.frg_no_anim);
		  			 break;
				
		  		default:
				break;
			}
		  transaction.replace(containerResId, fragment );
		  transaction.addToBackStack(null);
		  transaction.commit();
		  
	}

	public static void goToFragment(FragmentActivity activity, android.support.v4.app.Fragment fragment, int containerResId, int direction){	
		
		  android.support.v4.app.FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
		  
		  /*switch (direction) {
			
		  		case FRAGMENT_LEFT:
		  			transaction.setCustomAnimations(R.anim.frg_left_a, R.anim.frg_left_b);
		  			break;
	
		  		case FRAGMENT_RIGHT:
		  			 transaction.setCustomAnimations(R.anim.frg_right_a, R.anim.frg_right_b);
		  			 break;
		  		
		  		case FRAGMENT_TOP:
		  			 transaction.setCustomAnimations(R.anim.frg_top_a, R.anim.frg_top_b);
		  			 break;
		  			
		  		case FRAGMENT_BOTTOM:
		  			 transaction.setCustomAnimations(R.anim.frg_bottom_a, R.anim.frg_bottom_b);
		  			break;
		  			
		  		case FRAGMENT_NO_ANIMATION:
		  			 transaction.setCustomAnimations(R.anim.frg_no_anim, R.anim.frg_no_anim);
		  			 break;
				
		  		default:
				break;
			}*/
		  transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		  transaction.replace(containerResId, fragment );
		  transaction.addToBackStack(null);
		  transaction.commit();
		  
	}
	
	//OPEN ACTIVITY
	public static <T> void goToActivity (Activity activity,Class<T> activityClass, int animation){
		Intent mainIntent = new Intent(activity, activityClass);
		activity.startActivity(mainIntent);
		animate(activity, animation);
	}

	//OPEN ACTIVITY WITH BUNDLE
	public static <T> void goToActivityWithBundle(Activity activity,Class<T> activityClass, Bundle bundle,int animation){
		
		Intent intent = new Intent(activity,activityClass);  
        intent.putExtras(bundle);  
        activity.startActivity(intent);  
        animate(activity, animation);
	}
	
	//FINISH ACTIVITY
	public static void finishActivity(Activity activity,int animation){
		activity.finish();
		animate(activity, animation);
	}
	
	
	public static void animate(Activity activity,int animation){
		switch (animation) {
		case ACTIVITY_FADE:
			activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			break;
		case ACTIVITY_TOP:
			activity.overridePendingTransition(R.anim.act_top_a, R.anim.act_top_b);
			break;
		case ACTIVITY_BOTTOM:
			activity.overridePendingTransition(R.anim.act_bottom_a, R.anim.act_bottom_b);
			break;
			
		case ACTIVITY_LEFT:
			activity.overridePendingTransition(R.anim.act_left_a, R.anim.act_left_b);
			break;
			
		case ACTIVITY_RIGHT:
			activity.overridePendingTransition(R.anim.act_right_a, R.anim.act_right_b);
			break;
			
		case ACTIVITY_SPLASH:
			activity.overridePendingTransition(R.anim.act_splash_a, R.anim.act_splash_b);
			break;
			
		default:
			break;
		}
	}
}
