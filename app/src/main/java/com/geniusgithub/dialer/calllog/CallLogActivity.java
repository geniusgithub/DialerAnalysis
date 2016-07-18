package com.geniusgithub.dialer.calllog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.geniusgithub.dialer.R;


public class CallLogActivity extends AppCompatActivity {

    private final static String TAG = CallLogActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }

    private void initView() {
        setContentView(R.layout.activity_calllog_unit);
            getFragmentManager().beginTransaction().add(R.id.dialtacts_container, new CallLogFragment(), "calllog_fragment").commit();

    }


}
