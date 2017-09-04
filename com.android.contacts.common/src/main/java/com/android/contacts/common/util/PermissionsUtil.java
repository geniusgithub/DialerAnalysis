/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.contacts.common.util;

import android.Manifest;
import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Process;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;

import com.android.contacts.common.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to help with runtime permissions.
 */
public class PermissionsUtil {
    // Each permission in this list is a cherry-picked permission from a particular permission
    // group. Granting a permission group enables access to all permissions in that group so we
    // only need to check a single permission in each group.
    // Note: This assumes that the app has correctly requested for all the relevant permissions
    // in its Manifest file.
    public static final String PHONE[];
    public static final String CONTACTS[];
    public static final String SMS[];
    public static final String LOCATION[];
    public static final String STORAGE[];
    public static final String CALENDAR[];
    public static final String MICROPHONE[];
    public static final String SENSORS[];
    public static final String CAMERA[];

    public static final String[] sRequiredPermissions;

    private static boolean sInitialized = false;
    public static boolean sIsAtLeastM = getApiVersion() >= android.os.Build.VERSION_CODES.M;
    public static final String PACKAGE_URI_PREFIX = "package:";
    public static final String SECURITY_INTENT = "com.android.SETTINGS";

    static {
        CALENDAR = new String[]{
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR};

        CAMERA = new String[]{
                Manifest.permission.CAMERA};

        CONTACTS = new String[]{
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.GET_ACCOUNTS};

        LOCATION = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        MICROPHONE = new String[]{
                Manifest.permission.RECORD_AUDIO};

        PHONE = new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.WRITE_CALL_LOG,
                Manifest.permission.USE_SIP,
                Manifest.permission.PROCESS_OUTGOING_CALLS};

        SENSORS = new String[]{
                Manifest.permission.BODY_SENSORS};

        SMS = new String[]{
                Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_WAP_PUSH,
                Manifest.permission.RECEIVE_MMS};

        STORAGE = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        ArrayList<String> requestPermissons = new ArrayList<>();
        addRequestPermission(requestPermissons, PHONE);
        addRequestPermission(requestPermissons, CONTACTS);
        addRequestPermission(requestPermissons, LOCATION);
        addRequestPermission(requestPermissons, STORAGE);

        sRequiredPermissions = (String [])requestPermissons.toArray(new String[0]);;
    }

    private static void addRequestPermission(List<String> requestPermissons, String[] permission){
        for(String value: permission){
            requestPermissons.add(value);
        }
    }

    public static int getApiVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }




    public static boolean hasPhonePermissions(Context context) {
        return hasPermissions(context, PHONE);
    }

    public static boolean hasContactsPermissions(Context context) {
        return hasPermissions(context, CONTACTS);
    }

    public static boolean hasLocationPermissions(Context context) {
        return hasPermissions(context, LOCATION);
    }

    public static boolean hasSmsPermissions(Context context) {
        return hasPermissions(context, SMS);
    }

    public static boolean hasStoragePermissions(Context context) {
        return hasPermissions(context, STORAGE);
    }

    public static boolean hasCalendarPermissions(Context context) {
        return hasPermissions(context, CALENDAR);
    }

    public static boolean hasMicrophonePermissions(Context context) {
        return hasPermissions(context, MICROPHONE);
    }

    public static boolean hasSensorsPermissions(Context context) {
        return hasPermissions(context, SENSORS);
    }

    public static boolean hasCameraPermissions(Context context) {
        return hasPermissions(context, CAMERA);
    }


    public static boolean hasNecessaryRequiredPermissions(Context context) {
        return hasPermissions(context, sRequiredPermissions);
    }

    @SuppressLint("NewApi")
    public static boolean hasPermission(Context context, String permission) {

        if (!sIsAtLeastM)
            return true;
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;

    }

    public static boolean hasPermissions(Context context,
                                         final String... permissions) {
        for (final String permission : permissions) {
            if (!hasPermission(context, permission)) {
                return false;
            }
        }
        return true;

    }

    public static boolean hasAppOp(Context context, String appOp) {
        final AppOpsManager appOpsManager =
                (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        final int mode = appOpsManager.checkOpNoThrow(appOp, Process.myUid(),
                context.getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    public static Dialog createPermissionSettingDialog(final Activity activity,
                                                       String forbiddenPermissions) {
        if (forbiddenPermissions.length() > 1) {
            forbiddenPermissions = forbiddenPermissions.substring(0,
                    forbiddenPermissions.length() - 1);
        }
        Dialog dialog = new AlertDialog.Builder(activity)
                .setTitle(R.string.permission_title)
                .setMessage(
                        activity.getString(
                                R.string.permission_setting_guidedialog,
                                forbiddenPermissions))
                .setPositiveButton(R.string.permission_gosetting,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                final Intent intentnew = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.parse(PermissionsUtil.PACKAGE_URI_PREFIX + activity.getPackageName()));
                                activity.startActivity(intentnew);
                            }
                        })
                .setNegativeButton(R.string.permission_know,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).create();
        dialog.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                // TODO Auto-generated method stub
                activity.finish();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
    /**
     * Rudimentary methods wrapping the use of a LocalBroadcastManager to simplify the process
     * of notifying other classes when a particular fragment is notified that a permission is
     * granted.
     *
     * To be notified when a permission has been granted, create a new broadcast receiver
     * and register it using {@link #registerPermissionReceiver(Context, BroadcastReceiver, String)}
     *
     * E.g.
     *
     * final BroadcastReceiver receiver = new BroadcastReceiver() {
     *     @Override
     *     public void onReceive(Context context, Intent intent) {
     *         refreshContactsView();
     *     }
     * }
     *
     * PermissionsUtil.registerPermissionReceiver(getActivity(), receiver, READ_CONTACTS);
     *
     * If you register to listen for multiple permissions, you can identify which permission was
     * granted by inspecting {@link Intent#getAction()}.
     *
     * In the fragment that requests for the permission, be sure to call
     * {@link #notifyPermissionGranted(Context, String)} when the permission is granted so that
     * any interested listeners are notified of the change.
     */
    public static void registerPermissionReceiver(Context context, BroadcastReceiver receiver,
            String permission) {
        final IntentFilter filter = new IntentFilter(permission);
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, filter);
    }

    public static void unregisterPermissionReceiver(Context context, BroadcastReceiver receiver) {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
    }

    public static void notifyPermissionGranted(Context context, String permission) {
        final Intent intent = new Intent(permission);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
