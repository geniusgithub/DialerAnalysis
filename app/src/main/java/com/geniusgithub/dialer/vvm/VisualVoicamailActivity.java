package com.geniusgithub.dialer.vvm;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.geniusgithub.dialer.R;

public class VisualVoicamailActivity extends AppCompatActivity implements View.OnClickListener, CallLogQueryHandler.Listener{

    private final static String TAG = "VisualVoicamailActivity";

    private Button mBtnVVM;
    private CallLogQueryHandler mCallLogQueryHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }

    private void initView() {
        setContentView(R.layout.activity_vvm_unit);

        mBtnVVM = (Button) findViewById(R.id.bt_getvvm);
        mBtnVVM.setOnClickListener(this);

        mCallLogQueryHandler =
                new CallLogQueryHandler(this, getContentResolver(), this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_getvvm:
                getVVM();
                break;
        }
    }

    private void getVVM(){
        Log.i(TAG, "getVVM");
        mCallLogQueryHandler.fetchVoicemailStatus();
    }

    @Override
    public void onVoicemailStatusFetched(Cursor statusCursor) {
        Log.i(TAG, "onVoicemailStatusFetched statusCursor = " + statusCursor);
    }

    @Override
    public void onVoicemailUnreadCountFetched(Cursor cursor) {

    }

    @Override
    public void onMissedCallsUnreadCountFetched(Cursor cursor) {

    }

    @Override
    public boolean onCallsFetched(Cursor combinedCursor) {
        return false;
    }
}