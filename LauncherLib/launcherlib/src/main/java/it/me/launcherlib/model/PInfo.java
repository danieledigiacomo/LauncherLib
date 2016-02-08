package it.me.launcherlib.model;


import java.util.ArrayList;
import java.util.List;

import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class PInfo {
	
    public  String appname = "";
    public String pname = "";
    public String versionName = "";
    private int versionCode = 0;
    public Drawable icon;
    public boolean locked;
    
    
    private void prettyPrint() {
        Log.d("",appname + "\t" + pname + "\t" + versionName + "\t" + versionCode);
    }
}

