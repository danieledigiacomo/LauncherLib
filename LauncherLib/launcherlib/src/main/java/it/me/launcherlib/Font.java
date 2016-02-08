package it.me.launcherlib;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

public class Font {

	public static String FONT_ROBOTO = "fonts/RobotoCondensed-Bold.ttf";
	public static String FONT_JUNIOR_BOLD = "fonts/MyriadPro-BoldCond.otf";
	public static String FONT_JUNIOR = "fonts/MyriadPro.otf";
	
	public static void setFont(Context context, TextView textView){		
		Typeface type = Typeface.createFromAsset(context.getAssets(), Font.FONT_JUNIOR_BOLD); 
		textView.setTypeface(type);
	}
	
	public static void setFont(Context context, Button button){
		Typeface type = Typeface.createFromAsset(context.getAssets(), Font.FONT_JUNIOR_BOLD); 
		button.setTypeface(type);
	}
	
	public static void setFont(Context context, TextView textView, String font){		
		Typeface type = Typeface.createFromAsset(context.getAssets(), font); 
		textView.setTypeface(type);
	}
	
	public static void setFont(Context context, Button button, String font){
		Typeface type = Typeface.createFromAsset(context.getAssets(), font); 
		button.setTypeface(type);
	}
	
/*	public static void setFont(CustomActivity activity, TextView textView){
		Typeface type = Typeface.createFromAsset(activity.getAssets(), Font.FONT_JUNIOR); 
		textView.setTypeface(type);
	}
	public static void setFont(TextView textView){
		Typeface type = Typeface.createFromAsset(ActProfiles.activity.getAssets(), Font.FONT_JUNIOR); 
		textView.setTypeface(type);
	}
*/	
}
