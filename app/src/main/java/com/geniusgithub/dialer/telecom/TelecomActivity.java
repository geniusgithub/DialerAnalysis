package com.geniusgithub.dialer.telecom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.geniusgithub.dialer.R;
import com.geniusgithub.dialer.incall.InCallFragment;


public class TelecomActivity extends AppCompatActivity {

    private final static String TAG = TelecomActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }

    private void initView() {
        setContentView(R.layout.activity_telecom_unit);
            getFragmentManager().beginTransaction().add(R.id.dialtacts_container, new TelecomFragment(), "telecom_fragment").commit();

    }


}
