package com.geniusgithub.dialer.dialpad;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.geniusgithub.dialer.R;
import com.geniusgithub.dialer.util.AlwaysLog;


public class DialerActivity extends AppCompatActivity {

    private final static String TAG = DialerActivity.class.getSimpleName();

    private DialpadControlCenter mControlCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlwaysLog.i(TAG, "onCreate");
        setContentView(R.layout.activity_dialer_unit);
        getFragmentManager().beginTransaction().add(R.id.dialtacts_container, new DialpadFragment(), "dialer_fragment").commit();

        mControlCenter = new DialpadControlCenter(this, R.id.dialtacts_container);
        mControlCenter.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

        mControlCenter.updateDialpadViewShowStatusSelf();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        AlwaysLog.i(TAG, "onConfigurationChanged");
        mControlCenter.onConfigurationChanged(newConfig);
        mControlCenter.updateDialpadViewShowStatusSelf();
    }

    public boolean isOrientationPortatit(){
        boolean flag = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
        return flag;
    }


}
