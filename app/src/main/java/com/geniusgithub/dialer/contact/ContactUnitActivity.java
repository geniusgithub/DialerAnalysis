package com.geniusgithub.dialer.contact;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.geniusgithub.dialer.R;


public class ContactUnitActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = ContactUnitActivity.class.getSimpleName();

    private Button mBtnQuery;
    private Button mBtnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initView();

    }

    private void initView() {
        setContentView(R.layout.activity_contact_unit);

        mBtnQuery = (Button)findViewById(R.id.bt_query);
        mBtnUpdate = (Button)findViewById(R.id.bt_update);

        mBtnQuery.setOnClickListener(this);
        mBtnUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_query:
                onQuery();
                break;
            case R.id.bt_update:
                onUpdate();
                break;
        }
    }

    private void onQuery(){

    }

    private void onUpdate(){

    }

}
