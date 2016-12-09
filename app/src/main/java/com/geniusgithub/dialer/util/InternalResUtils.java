package com.geniusgithub.dialer.util;

import android.content.Context;
import android.text.TextUtils;


public class InternalResUtils {

    public static int getInternalResDrawableId(Context context, String resName) {
        if (context == null || TextUtils.isEmpty(resName)) {
            return -1;
        }

        return context.getResources().getIdentifier(resName,
                "drawable", "android");
    }


    public static boolean getInternalResBoolean(Context context, String resName) {
        if (context == null || TextUtils.isEmpty(resName)) {
            return false;
        }

        int resId = context.getResources().getIdentifier(resName,
                "bool", "android");

        boolean result = false;
        if (resId > 0) {
            result = context.getResources().getBoolean(resId);
        }

        return result;
    }

    public static String getInternalResString(Context context, String resName) {
        if (context == null || TextUtils.isEmpty(resName)) {
            return null;
        }

        int resId = context.getResources().getIdentifier(resName,
                "string", "android");

        String result = null;
        if (resId > 0) {
            result = context.getResources().getString(resId);
        }

        return result;
    }


    public static String[] getInternalResStringArray(Context context, String resName) {
        if (context == null || TextUtils.isEmpty(resName)) {
            return null;
        }

        int resId = context.getResources().getIdentifier(resName,
                "array", "android");

        String[] result = null;
        if (resId > 0) {
            result = context.getResources().getStringArray(resId);
        }

        return result;
    }
}
