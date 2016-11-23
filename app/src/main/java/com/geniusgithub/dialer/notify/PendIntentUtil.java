package com.geniusgithub.dialer.notify;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.geniusgithub.dialer.R;

public class PendIntentUtil {

    public static  void setMainPendingIntent(Notification.Builder publicBuilder, Context context){
        publicBuilder.setSmallIcon(R.drawable.fab_ic_call)
                .setColor(context.getResources().getColor(R.color.dialer_theme_color))
                // Show "Phone" for notification title.
                .setContentTitle("phoneTitle")
                // Notification details shows that there are missed call(s), but does not reveal
                // the missed caller information.
                .setContentText("phoneContent")
                .setContentIntent(PendIntentUtil.createMainPendingIntent(context))
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setDeleteIntent(createClearPendingIntent(context));
    }

    public static void setCustomPendingIntent(Notification.Builder publicBuilder,  Context context){
        publicBuilder.addAction(R.drawable.ic_message_24dp, "customTitle", PendIntentUtil.createCustomPendingIntent(context));
    }


    private static PendingIntent createMainPendingIntent(Context context){
/*        Intent contentIntent = new Intent(context, NotifyActivity.class);
        return PendingIntent.getActivity(
                context, 0, contentIntent,PendingIntent.FLAG_UPDATE_CURRENT);*/
        Intent intent = new Intent(context, MyNotifyService.class);
        intent.setAction(MyNotifyService.ACTION_SEND_MAIN_NOTIFY);


        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static PendingIntent createCustomPendingIntent(Context context){

        Intent contentIntent = new Intent();
        contentIntent.setAction(NotifyActivity.ACTION_NOTIFY_ACTIVITY);
        contentIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(
                context, 0, contentIntent,PendingIntent.FLAG_UPDATE_CURRENT);

  /*      Intent intent = new Intent(context, MyNotifyService.class);
        intent.setAction(MyNotifyService.ACTION_SEND_CUSTOM_NOTIFY);
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);*/
    }

    private static PendingIntent createClearPendingIntent(Context context) {
        Intent intent = new Intent(context, MyNotifyService.class);
        intent.setAction(MyNotifyService.ACTION_CLEAR_NOTIFY);
        return PendingIntent.getService(context, 0, intent, 0);
    }

}
