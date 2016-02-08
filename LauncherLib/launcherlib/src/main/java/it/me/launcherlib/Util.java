package it.me.launcherlib;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings.Secure;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.me.launcherlib.widget.CircularImageView;

public class Util {


	public static String getHumanReadableDate(long timeInMillis){
		Date date = new Date(timeInMillis);
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
		//sdf.setTimeZone(TimeZone.getDefault());
		String formattedDate = sdf.format(date);
		return formattedDate;
	}


	public final static String asUpperCaseFirstChar(final String target) {

	    if ((target == null) || (target.length() == 0)) {
	        return target; // You could omit this check and simply live with an
	                       // exception if you like
	    }
	    return Character.toUpperCase(target.charAt(0))
	            + (target.length() > 1 ? target.substring(1) : "");
	}
	
	//DECODE SAMPLE BITMAP FROM FILE
	public static Bitmap decodeBitmapFromFile(Context context,String pathFile, int reqWidth, int reqHeight) {
		File file = new File(pathFile);
		if(file.exists()){
			final BitmapFactory.Options options = new BitmapFactory.Options();
		    options.inJustDecodeBounds = true;
		    BitmapFactory.decodeFile(pathFile, options);
		    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		    options.inJustDecodeBounds = false;
		    return BitmapFactory.decodeFile(pathFile, options);
		}else{
			
			final BitmapFactory.Options options = new BitmapFactory.Options();
		    options.inJustDecodeBounds = true;
		    //BitmapFactory.decodeFile(pathFile, options);
		    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		    options.inJustDecodeBounds = false;
			
			return BitmapFactory.decodeResource(context.getResources(), R.drawable.empty_button,options);
			
		}
	    
	}
	
	//CALCULATE SAMPLE SIZE
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth) {
	        final int heightRatio = Math.round((float) height / (float) reqHeight);
	        final int widthRatio = Math.round((float) width / (float) reqWidth);
	        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	    }
	
	    return inSampleSize;
	}
	
	public static void setGreyFilter(ImageView view, boolean apply){
		if(apply){
			ColorMatrix matrix = new ColorMatrix();
			matrix.setSaturation(0);
			ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
			view.setColorFilter(filter);
		}else{
			view.setColorFilter(null);
		}
	}
	public static void setColorFilter(View view, boolean apply){
		ColorMatrixColorFilter filter = null;
		if(apply){
			ColorMatrix matrix = new ColorMatrix();
			matrix.setSaturation(0.1f);
			filter = new ColorMatrixColorFilter(matrix);
		}else{
			filter = null;
		}
		if(view instanceof ImageView || view instanceof CircularImageView){
			((ImageView)view).setColorFilter(filter);
		}else if(view instanceof RelativeLayout){
			((RelativeLayout)view).getBackground().setColorFilter(filter);
		}
	}
	
	/*DEVICE ID*/
	public static String getDeviceID(Context context){
		return Secure.getString(context.getContentResolver(),Secure.ANDROID_ID); 
	}
	
	//HIDE KEYBOARD
	public static void hideKeyboard(Activity activity){
		//InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		//imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
		activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}
	
	//SHOW KEYBOARD
	public static void showKeyboard(Activity activity){
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
	}
	
	public static String fromUriImageToPath(Context context,Uri imageUri){
		String filePath = "";
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(imageUri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
		return filePath;
	}
}
