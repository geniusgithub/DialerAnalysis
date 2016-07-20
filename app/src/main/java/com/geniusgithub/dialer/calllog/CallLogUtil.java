package com.geniusgithub.dialer.calllog;

import android.provider.CallLog;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CallLogUtil {

    public static String getStringDate(long date) {
        Date currentTime = new Date(date);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 	HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    public static String formatteDuration(long duration){
        return String.valueOf(duration);
    }

    public static String getCalltype(int  calltype){
        String value = "unknown";
        switch( calltype){
            case CallLog.Calls.INCOMING_TYPE:
                value = "INCOMING_TYPE";
                break;
            case CallLog.Calls.OUTGOING_TYPE:
                value =  "OUTGOING_TYPE";
                break;
            case CallLog.Calls.MISSED_TYPE:
                value =  "MISSED_TYPE";
                break;
            case CallLog.Calls.VOICEMAIL_TYPE:
                value = "VOICEMAIL_TYPE";
                break;
        }

        return value;
    }
}
