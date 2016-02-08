package it.me.launcherlib.tool;

import android.app.Activity;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

public class KeyboardTool {

	//HIDE KEYBOARD
	public static void hide(Activity activity){
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		//imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
	
		    imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
	}
	
	//HIDE KEYBOARD
	public static void hide(Activity activity, IBinder ibinder){
		InputMethodManager imm = (InputMethodManager)activity.getSystemService(activity.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(ibinder, 0);
	}
	
	//SHOW KEYBOARD
	public static void show(Activity activity){
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
	}
	
}
