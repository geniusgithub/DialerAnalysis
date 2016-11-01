package com.geniusgithub.dialer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.contacts.common.util.PermissionsUtil;
import com.geniusgithub.dialer.blockednumber.BlockedNumbersActivity;
import com.geniusgithub.dialer.calllog.CallLogActivity;
import com.geniusgithub.dialer.contact.ContactUnitActivity;
import com.geniusgithub.dialer.dialpad.DialerActivity;
import com.geniusgithub.dialer.sim.SimActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mBtnSim;
    private Button mBtnContact;
    private Button mBtnCalllog;
    private Button mBtnDialpad;
    private Button mBtnBlockNumber;

    private Button mBtnTest1;
    private Button mBtnTest2;
    private Button mBtnTest3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean hasStartPermissionActivity =RequestPermissionsActivity.startPermissionActivity(this);

        initView();

    }

    @Override
    protected void onStart() {
        super.onStart();



    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();


    }


    @Override
    protected void onStop() {
        super.onStop();
;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private void initView() {
        setContentView(R.layout.activity_main);

        mBtnSim = (Button)findViewById(R.id.bt_sim);
        mBtnContact = (Button)findViewById(R.id.bt_contact);
        mBtnCalllog = (Button)findViewById(R.id.bt_calllog);
        mBtnDialpad = (Button)findViewById(R.id.bt_dialer);
        mBtnBlockNumber = (Button)findViewById(R.id.bt_blocknumber);
        mBtnSim.setOnClickListener(this);
        mBtnContact.setOnClickListener(this);
        mBtnCalllog.setOnClickListener(this);
        mBtnDialpad.setOnClickListener(this);
        mBtnBlockNumber.setOnClickListener(this);

        mBtnTest1 = (Button) findViewById(R.id.bt_test1);
        mBtnTest2 = (Button) findViewById(R.id.bt_test2);
        mBtnTest3 = (Button) findViewById(R.id.bt_test3);
        mBtnTest1.setOnClickListener(this);
        mBtnTest2.setOnClickListener(this);
        mBtnTest3.setOnClickListener(this);
        mBtnTest1.setVisibility(View.GONE);
        mBtnTest2.setVisibility(View.GONE);
        mBtnTest3.setVisibility(View.GONE);

        if (PermissionsUtil.getApiVersion() < Build.VERSION_CODES.N){
            mBtnBlockNumber.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_sim:
                onSim();
                break;
            case R.id.bt_contact:
               onContact();
                break;
            case R.id.bt_calllog:
                onCalllog();
                break;
            case R.id.bt_dialer:
                onDialpad();
                break;
            case R.id.bt_blocknumber:
                onBlockNumber();
                break;
        }
    }

    private void onSim(){
        Intent intent = new Intent();
        intent.setClass(this, SimActivity.class);
        startActivity(intent);
    }

    private void onContact(){
        Intent intent = new Intent();
        intent.setClass(this, ContactUnitActivity.class);
        startActivity(intent);
    }

    private void onCalllog(){
        Intent intent = new Intent();
        intent.setClass(this, CallLogActivity.class);
        startActivity(intent);
    }

    private void onDialpad(){
        Intent intent = new Intent();
        intent.setClass(this, DialerActivity.class);
        startActivity(intent);
    }

    private void onBlockNumber(){
        Intent intent = new Intent();
        intent.setClass(this, BlockedNumbersActivity.class);
        startActivity(intent);
    }
}
