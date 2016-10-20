package com.geniusgithub.dialer.sim;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.geniusgithub.dialer.R;


public class SimActivity extends AppCompatActivity {

    private final static String TAG = SimActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }

    private void initView() {
        setContentView(R.layout.activity_sim_unit);
            getFragmentManager().beginTransaction().add(R.id.dialtacts_container, new SimInfoFragment(), "sim_fragment").commit();

    }


}
