package com.geniusgithub.dialer.incall;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.geniusgithub.dialer.R;
import com.geniusgithub.dialer.sim.SimInfoFragment;


public class InCallActivity extends AppCompatActivity {

    private final static String TAG = InCallActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }

    private void initView() {
        setContentView(R.layout.activity_incall_unit);
            getFragmentManager().beginTransaction().add(R.id.dialtacts_container, new InCallFragment(), "incall_fragment").commit();

    }


}
