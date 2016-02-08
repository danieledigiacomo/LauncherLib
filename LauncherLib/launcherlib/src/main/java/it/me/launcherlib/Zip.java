package it.me.launcherlib;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by daniele on 03/02/16.
 */
public class Zip {

    private static int BUFFER_SIZE = 8192;
    public static void unzip(String zipFile, String location) throws Exception {
        int size;
        byte[] buffer = new byte[BUFFER_SIZE];
        if ( !location.endsWith("/") ) {
            location += "/";
        }
        File f = new File(location);
        if(!f.isDirectory()) {
            f.mkdirs();
            f.setWritable(true);
            f.setReadable(true);
            f.setExecutable(true);

        }
        Log.e("SHELL", "-----UNZIP------");
        Shell.chmod(f, Shell.CHMOD_777);
        ZipInputStream zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile), BUFFER_SIZE));
        try {
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                String path = location + ze.getName();
                File unzipFile = new File(path);

                if (ze.isDirectory()) {
                    if(!unzipFile.isDirectory()) {
                        unzipFile.mkdirs();
                        unzipFile.setWritable(true);
                        unzipFile.setReadable(true);
                        unzipFile.setExecutable(true);

                        Shell.chmod(unzipFile, Shell.CHMOD_777);
                    }
                } else {
                    // check for and create parent directories if they don't exist
                    File parentDir = unzipFile.getParentFile();
                    if ( null != parentDir ) {
                        if ( !parentDir.isDirectory() ) {
                            parentDir.mkdirs();
                            unzipFile.setWritable(true);
                            unzipFile.setReadable(true);
                            unzipFile.setExecutable(true);

                        }
                    }

                    // unzip the file
                    FileOutputStream out = new FileOutputStream(unzipFile, false);
                    BufferedOutputStream fout = new BufferedOutputStream(out, BUFFER_SIZE);
                    try {
                        while ( (size = zin.read(buffer, 0, BUFFER_SIZE)) != -1 ) {
                            fout.write(buffer, 0, size);
                        }

                        zin.closeEntry();
                    }
                    finally {
                        fout.flush();
                        fout.close();
                        Shell.chmod(unzipFile, Shell.CHMOD_777);
                    }
                }
            }
        }
        finally {
            zin.close();
        }

    }

}
