package it.me.launcherlib.widget;

import it.me.launcherlib.Font;
import it.me.launcherlib.R;
import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CToast {

	
	public static void show(Activity activity, String text, int duration){
		LayoutInflater inflater = activity.getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast,(ViewGroup) activity.findViewById(R.id.toast_layout_root));

		TextView tv = (TextView) layout.findViewById(R.id.text);
		tv.setText(text);
		Font.setFont(activity, tv, Font.FONT_JUNIOR);
		
		Toast toast = new Toast(activity);
		//toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.setDuration(duration);
		toast.setView(layout);
		toast.show();
		
	}
	
}
