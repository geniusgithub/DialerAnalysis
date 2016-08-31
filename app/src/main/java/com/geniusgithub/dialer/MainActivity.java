package com.geniusgithub.dialer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.geniusgithub.dialer.calllog.CallLogActivity;
import com.geniusgithub.dialer.contact.ContactUnitActivity;
import com.geniusgithub.dialer.dialpad.DialerActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mBtnContact;
    private Button mBtnCalllog;
    private Button mBtnDialpad;

    private KeyboardTone mKeyboardTome;
    private Button mBtnTest1;
    private Button mBtnTest2;
    private Button mBtnTest3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean hasStartPermissionActivity =RequestPermissionsActivity.startPermissionActivity(this);

        initView();

        mKeyboardTome = new KeyboardTone(this);
        mKeyboardTome.initSoundPool();
    }

    @Override
    protected void onDestroy() {
        mKeyboardTome.releasePool();
        super.onDestroy();
    }

    private void initView() {
        setContentView(R.layout.activity_main);

        mBtnContact = (Button)findViewById(R.id.bt_contact);
        mBtnCalllog = (Button)findViewById(R.id.bt_calllog);
        mBtnDialpad = (Button)findViewById(R.id.bt_dialer);
        mBtnContact.setOnClickListener(this);
        mBtnCalllog.setOnClickListener(this);
        mBtnDialpad.setOnClickListener(this);

        mBtnTest1 = (Button) findViewById(R.id.bt_test1);
        mBtnTest2 = (Button) findViewById(R.id.bt_test2);
        mBtnTest3 = (Button) findViewById(R.id.bt_test3);
        mBtnTest1.setOnClickListener(this);
        mBtnTest2.setOnClickListener(this);
        mBtnTest3.setOnClickListener(this);
        mBtnTest1.setVisibility(View.GONE);
        mBtnTest2.setVisibility(View.GONE);
        mBtnTest3.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_contact:
               onContact();
                break;
            case R.id.bt_calllog:
                onCalllog();
                break;
            case R.id.bt_dialer:
                onDialpad();
                break;
            case R.id.bt_test1:
                mKeyboardTome.playSoundPool(6, 1, false);
                break;
            case R.id.bt_test2:
             //   playTone(2);
                mKeyboardTome.playSoundPool(6, 1, true);
                break;
            case R.id.bt_test3:
                mKeyboardTome.playSoundPool(5, 1, false);
                break;
        }
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
}
