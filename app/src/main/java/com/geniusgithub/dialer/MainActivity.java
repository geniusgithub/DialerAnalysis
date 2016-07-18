package com.geniusgithub.dialer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.geniusgithub.dialer.calllog.CallLogActivity;
import com.geniusgithub.dialer.contact.ContactUnitActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mBtnContact;
    private Button mBtnCalllog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean hasStartPermissionActivity =RequestPermissionsActivity.startPermissionActivity(this);

        initView();
    }


    private void initView() {
        setContentView(R.layout.activity_main);

        mBtnContact = (Button)findViewById(R.id.bt_contact);
        mBtnCalllog = (Button)findViewById(R.id.bt_calllog);
        mBtnContact.setOnClickListener(this);
        mBtnCalllog.setOnClickListener(this);
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
}
