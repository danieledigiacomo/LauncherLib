package it.me.launcherlib.widget;

import it.me.launcherlib.Font;
import it.me.launcherlib.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CloudToast { 

	
	public static void show(Activity activity){
		LayoutInflater inflater = activity.getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_need_nickname,(ViewGroup) activity.findViewById(R.id.toast_layout_root));

		TextView tv = (TextView) layout.findViewById(R.id.text);
		tv.setText(activity.getResources().getString(R.string.seleziona_nome_cloud));
		Font.setFont(activity, tv, Font.FONT_JUNIOR);
		
		Toast toast = new Toast(activity);
		//toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
		
	}
	
}
