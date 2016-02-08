package it.me.launcherlib;

import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;

public class Shell {
	
	
	public static String CHMOD_700 = "700";
	public static int CHMOD_777 = 0777;
	public static int CHMOD_555 = 0555;
	
	public static String TAG = " SHELL ";
	/*public static boolean chmod(String mode, String folderPath){
		boolean done = false;
		Process process;
		try {
			String command_ = "ln -s /data/lisciani/libs/xwalk/v1 /data/app-lib/sticazzi";
			String command = "su -c chmod -R "+ mode + " "+ folderPath;
			Logger.createExcpetionLog("CHECK PACKAGE", "CHMOD COMMAND", String.valueOf(command));
			process = Runtime.getRuntime().exec(command);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			done = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("","ERROR");
			e.printStackTrace();
			done = false;
		}
		return done;
	}
	
	
	
	public static boolean prova(){
		boolean done = false;
		Process process;
		try {
			String command_ = "ln -s /data/lisciani/libs/xwalk/v1 /data/app-lib/sticazzi";
			String command = "./data/lisciani/prova.sh";
			Logger.createExcpetionLog("CHECK PACKAGE", "CHMOD COMMAND", String.valueOf(command));
			process = Runtime.getRuntime().exec(command);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			done = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("","ERROR");
			e.printStackTrace();
			done = false;
		}
		return done;
	}
	
	public static boolean prova2(){
		boolean done = false;
		Process process = null;
		DataOutputStream dataOutputStream = null;

		try {
		    process = Runtime.getRuntime().exec("su - c");
		    dataOutputStream = new DataOutputStream(process.getOutputStream());
		    dataOutputStream.writeBytes("chmod -R 777 /data/lisciani/cache/ \n");
		    dataOutputStream.writeBytes("exit\n");
		    dataOutputStream.flush();
		    process.waitFor();
		    done = true;
		} catch (Exception e) {
		    return false;
		} finally {
		    try {
		        if (dataOutputStream != null) {
		            dataOutputStream.close();
		        }
		        process.destroy();
		    } catch (Exception e) {
		    }
		}
		
		
		return done;
	}*/
	
	public static int chmod(File path, int mode) throws Exception {
	    Class fileUtils = Class.forName("android.os.FileUtils");
	    Method setPermissions = fileUtils.getMethod("setPermissions", String.class, int.class, int.class, int.class);
	    Log.e("SHELL","CHMOD: " + path.getAbsolutePath());
	    return (Integer) setPermissions.invoke(null, path.getAbsolutePath(), mode, -1, -1);
	}
	
	
	public static int chmodFolder(File file) throws Exception {
	    Class fileUtils = Class.forName("com.android.tradefed.util.FileUtil");
	    Method setPermissions = fileUtils.getMethod("chmodGroupRWX", File.class);
	    return (Integer) setPermissions.invoke(null, file);
	}
	

	
}
