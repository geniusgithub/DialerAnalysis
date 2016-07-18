package com.geniusgithub.dialer.util;

public class DisplayUtil {

    private final static String TAG = DisplayUtil.class.getSimpleName();

    public static void display(String buf[]){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[\n");
        int size = buf.length;
        for (String value: buf ) {
            stringBuffer.append(value + "\n");
        }
        stringBuffer.append("]");
        AlwaysLog.i(TAG, stringBuffer.toString());
    }
}
