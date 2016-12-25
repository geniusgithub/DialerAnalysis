package com.geniusgithub.dialer.ims;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.geniusgithub.dialer.util.AlwaysLog;

public class VolteCallUtil {

    private final static String TAG  = "VolteCallUtil";

    public static boolean startConferenceCall(Context context, String []numberList){

        if (numberList == null){
            return false;
        }

        AlwaysLog.d(TAG, "startConferenceCall numberList = " + numberList.toString());
        StringBuffer numbers = new StringBuffer();
        for(String number : numberList) {
            numbers.append("tel:").append(number).append(";");
        }

        AlwaysLog.d(TAG, " numbers.toString = " + numbers.toString());
        final Intent conferenceIntent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", numbers.toString(), null));
        conferenceIntent.putExtra(TelecomManager.EXTRA_START_CALL_WITH_VIDEO_STATE, 0);
        conferenceIntent.putExtra("org.codeaurora.extra.DIAL_CONFERENCE_URI", true);
        conferenceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(conferenceIntent);

        AlwaysLog.d(TAG, "startConferenceCall --> conferenceIntent = " + conferenceIntent.getExtras());
        return true;

    }

    public static boolean isSuppportConferenceCall(Context context){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.isVolteAvailable();
    }

}
