package com.geniusgithub.dialer.notify;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.geniusgithub.dialer.R;

public class NotifyActivity extends Activity implements View.OnClickListener {

    public static final String ACTION_NOTIFY_ACTIVITY = "com.geniusgithub.dialer.notify";

    private Button mBtnSendNotify;
    private TextView mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_notify);

        mBtnSendNotify = (Button) findViewById(R.id.bt_sendNotify);
        mBtnSendNotify.setOnClickListener(this);

        mContent = (TextView) findViewById(R.id.tv_content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_sendNotify:
                sendNotify();
                break;
        }
    }

    private void sendNotify(){
        mContent.setText("sendNotify");

        Notification.Builder publicBuilder = new Notification.Builder(this);
        PendIntentUtil.setMainPendingIntent(publicBuilder, this);
        PendIntentUtil.setCustomPendingIntent(publicBuilder, this);

        Notification notification = publicBuilder.build();
        getNotificationMgr().notify(NOTIFICATION_TAG, NOTIFICATION_ID, notification);
    }

    public static final String NOTIFICATION_TAG = "MyNotifier";
    public static final int NOTIFICATION_ID = 1;


    private NotificationManager getNotificationMgr() {
        return (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
