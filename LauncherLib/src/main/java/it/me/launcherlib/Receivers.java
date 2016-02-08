package it.me.launcherlib;

import android.content.Context;
import android.content.Intent;

/**
 * Created by daniele on 05/02/16.
 */
public class Receivers {

    public static String ACTION_DOWNLOAD_UDNT_SECURE = "it.udanet.udnt_secure";

    public static void broadcast(Context context,String action){
        Intent i = new Intent();
        i.setAction(action);
        //i.putExtra("bundle_action",1);
        context.sendBroadcast(i);

    }

}
