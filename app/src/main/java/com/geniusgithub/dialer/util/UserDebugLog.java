package com.geniusgithub.dialer.util;

import android.os.Build;
import android.util.Log;

public class UserDebugLog {
    private static final String TAG = "UserDebugLog";

    public static boolean isEngPlatform = Build.TYPE.equals("eng");
    public static boolean isUserPlatform = Build.TYPE.equals("user");
    public static boolean isUserDebugPlatform = Build.TYPE.equals("userdebug");

    public static boolean isLoggable = isUserDebugMode();

    public static boolean isUserDebugMode(){
        if (isUserDebugPlatform || isEngPlatform){
            return true;
        }
        return false;
    }

    public static void d(String TAG, String log) {
        if (isLoggable) {
            Log.d(TAG, log);
        }
    }

    public static void d(String TAG, String log, Throwable tr) {
        if (isLoggable) {
            Log.d(TAG, log, tr);
        }
    }

    public static void i(String TAG, String log) {
        if (isLoggable) {
            Log.i(TAG, log);
        }
    }

    public static void i(String TAG, String log, Throwable tr) {
        if (isLoggable) {
            Log.i(TAG, log, tr);
        }
    }

    public static void w(String TAG, String log) {
        if (isLoggable) {
            Log.w(TAG, log);
        }
    }

    public static void w(String TAG, String log, Throwable tr) {
        if (isLoggable) {
            Log.w(TAG, log, tr);
        }
    }

    public static void e(String TAG, String log) {
        Log.e(TAG, log);
    }

    public static void e(String TAG, String log, Throwable tr) {
        Log.e(TAG, log, tr);
    }
}
