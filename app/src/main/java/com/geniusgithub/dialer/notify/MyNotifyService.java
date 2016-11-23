package com.geniusgithub.dialer.notify;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyNotifyService extends IntentService {

    public static final String ACTION_SEND_MAIN_NOTIFY =
            "com.geniusgithub.dialer.notify.ACTION_SEND_MAIN_NOTIFY";

    public static final String ACTION_SEND_CUSTOM_NOTIFY =
            "com.geniusgithub.dialer.notify.ACTION_SEND_CUSTOM_NOTIFY";

    public static final String ACTION_CLEAR_NOTIFY =
            "com.geniusgithub.dialer.notify.ACTION_CLEAR_NOTIFY";

    public MyNotifyService() {
        super("MyNotifyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            return;
        }

        String action = intent.getAction();
        Log.i("wyj", "onHandleIntent action = " + action);
        switch (action) {
            case ACTION_SEND_MAIN_NOTIFY:
                openMainNotify(this);
                break;
            case ACTION_SEND_CUSTOM_NOTIFY:
                openCustomNotify(this);
                break;
            case ACTION_CLEAR_NOTIFY:
                clearNotify(this);
                break;
        }
    }

    public static void openMainNotify(Context context){
        Log.i("wyj", "openMainNotify");
        Intent contentIntent = new Intent();
        contentIntent.setAction(NotifyActivity.ACTION_NOTIFY_ACTIVITY);

        context.startActivity(contentIntent);
    }

    public static void openCustomNotify(Context context){
        Log.i("wyj", "openCustomNotify");
        Intent contentIntent = new Intent();
        contentIntent.setAction(NotifyActivity.ACTION_NOTIFY_ACTIVITY);
        context.startActivity(contentIntent);
    }

    public static void clearNotify(Context context){
        Log.i("wyj", "clearNotify");
        getNotificationMgr(context).cancel(NotifyActivity.NOTIFICATION_ID);
    }


    private static NotificationManager getNotificationMgr(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
