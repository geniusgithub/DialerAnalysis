package com.geniusgithub.dialer.contact;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.geniusgithub.dialer.R;
import com.geniusgithub.dialer.util.DisplayUtil;


public class ContactUnitActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = ContactUnitActivity.class.getSimpleName();

    private Button mBtnQuery;
    private Button mBtnSearch;
    private Button mBtnUpdate;

    private ContentResolver mContentReslover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initView();

    }

    private void initView() {
        setContentView(R.layout.activity_contact_unit);

        mBtnQuery = (Button)findViewById(R.id.bt_query);
        mBtnSearch = (Button)findViewById(R.id.bt_search);
        mBtnUpdate = (Button)findViewById(R.id.bt_update);

        mBtnQuery.setOnClickListener(this);
        mBtnSearch.setOnClickListener(this);
        mBtnUpdate.setOnClickListener(this);

        mContentReslover = getContentResolver();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_query:
                onQuery();
                break;
            case R.id.bt_search:
                search();
                break;
            case R.id.bt_update:
                onUpdate();
                break;
        }
    }

    private void onQuery(){
        Cursor phoneCursor = mContentReslover.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        if (phoneCursor != null) {
            String colNames[] = phoneCursor.getColumnNames();
        //    DisplayUtil.display(colNames);
            phoneCursor.close();
        }

        Cursor contactsCursor = mContentReslover.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (contactsCursor != null){
            String colNames[] = contactsCursor.getColumnNames();
            DisplayUtil.display(colNames);
            contactsCursor.close();
        }

        Cursor rawcontactsCursor = mContentReslover.query(ContactsContract.RawContacts.CONTENT_URI, null, null, null, null);
        if (rawcontactsCursor != null){
            String colNames[] = rawcontactsCursor.getColumnNames();
       //     DisplayUtil.display(colNames);
            rawcontactsCursor.close();
        }



        Cursor dataCursor = mContentReslover.query(ContactsContract.Data.CONTENT_URI, null, null, null, null);
        if (dataCursor != null){
            String colNames[] = dataCursor.getColumnNames();
            DisplayUtil.display(colNames);
            dataCursor.close();
        }

    }

    private void search(){

    }

    private void onUpdate(){

    }

}
