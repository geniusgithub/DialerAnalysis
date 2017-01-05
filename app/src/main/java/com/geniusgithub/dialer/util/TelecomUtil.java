package com.geniusgithub.dialer.util;

import android.Manifest;
import android.content.Context;
import android.net.Uri;
import android.provider.CallLog;

import static com.android.contacts.common.util.PermissionsUtil.hasPermission;

public class TelecomUtil {

    public static boolean hasReadWriteVoicemailPermissions(Context context) {
        return isDefaultDialer(context)
                || (hasPermission(context, Manifest.permission.READ_VOICEMAIL)
                && hasPermission(context, Manifest.permission.WRITE_VOICEMAIL));
    }

    public static boolean isDefaultDialer(Context context) {
        return false;
    }

    public static Uri getCallLogUri(Context context) {
        return hasReadWriteVoicemailPermissions(context) ? CallLog.Calls.CONTENT_URI_WITH_VOICEMAIL
                : CallLog.Calls.CONTENT_URI;
    }
}
