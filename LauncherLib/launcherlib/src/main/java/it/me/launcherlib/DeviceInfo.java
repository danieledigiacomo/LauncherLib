package it.me.launcherlib;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by daniele on 03/02/16.
 */
public class DeviceInfo {


    public static JSONObject getInfo(Context context) {
        JSONObject response = new JSONObject();
        try {
            response.put("BRAND", Build.BRAND);
            response.put("DEVICE", Build.DEVICE);
            response.put("DISPLAY", Build.DISPLAY);
            response.put("HARDWARE", Build.HARDWARE);
            response.put("MANUFACTURER", Build.MANUFACTURER);
            response.put("MODEL", Build.MODEL);
            response.put("PRODUCT", Build.PRODUCT);
            response.put("VERSION_CODENAME", Build.VERSION.CODENAME);
            response.put("TYPE", Build.TYPE);
            response.put("BOARD", Build.BOARD);
            response.put("SERIAL", Build.SERIAL);

            final Point screenSize = getScreenSize(context);
            response.put("SCREEN_RESOLUTION", screenSize.x + "x" + screenSize.y);
            Log.e("SCREEN_RESOLUTION", screenSize.x + "x" + screenSize.y);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.e("BRAND", Build.BRAND);
        Log.e("DEVICE", Build.DEVICE);
        Log.e("DISPLAY", Build.DISPLAY);
        Log.e("HARDWARE", Build.HARDWARE);
        Log.e("MANUFACTURER", Build.MANUFACTURER);
        Log.e("MODEL", Build.MODEL);
        Log.e("PRODUCT", Build.PRODUCT);
        Log.e("VERSION_CODENAME", Build.VERSION.CODENAME);
        Log.e("TYPE", Build.TYPE);
        Log.e("BOARD", Build.BOARD);
        Log.e("SERIAL", Build.SERIAL);

        return response;
    }

    private static Point getScreenSize(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

}
