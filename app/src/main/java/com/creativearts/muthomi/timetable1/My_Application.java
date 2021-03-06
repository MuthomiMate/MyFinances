package com.creativearts.muthomi.timetable1;

import android.app.Application;
import android.util.Log;

import java.io.File;

/**
 * Created by MUTHOMI on 3/19/2016.
 */
public class My_Application extends Application {
    private static My_Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static My_Application getInstance() {
        return instance;
    }

    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.i("TAG", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }
}
