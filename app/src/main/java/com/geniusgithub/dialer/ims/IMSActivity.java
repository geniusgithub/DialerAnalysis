package com.geniusgithub.dialer.ims;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.geniusgithub.dialer.R;

public class IMSActivity extends Activity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private Button mBtnMenu;
    private TextView mContent;
    private PopupMenu mOverflowMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_ims_layout);

        mBtnMenu = (Button) findViewById(R.id.bt_menu);
        mBtnMenu.setOnClickListener(this);
        mContent = (TextView) findViewById(R.id.tv_content);
        mOverflowMenu = buildOptionsMenu(mBtnMenu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        int resId = item.getItemId();
        switch (resId){
            case R.id.menu_conference_call:
                dialConferenceCall();
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_menu:
                mOverflowMenu.show();
                break;
        }
    }

    private void dialConferenceCall(){
        mContent.setText("dialConferenceCall isSuppportConferenceCall = " + VolteCallUtil.isSuppportConferenceCall(this));
        String []numberList = new String[]{"18150396111", "18150396333", "18150396666"};
        VolteCallUtil.startConferenceCall(this, numberList);
    }

    protected class OptionsPopupMenu extends PopupMenu {
        public OptionsPopupMenu(Context context, View anchor) {
            super(context, anchor, Gravity.END);
        }

        @Override
        public void show() {
            final Menu menu = getMenu();
            final MenuItem conferenceCall = menu.findItem(R.id.menu_conference_call);
         //   conferenceCall.setVisible(VolteCallUtil.isSuppportConferenceCall(IMSActivity.this));
            super.show();
        }
    }

    protected OptionsPopupMenu buildOptionsMenu(View invoker) {
        final OptionsPopupMenu popupMenu = new OptionsPopupMenu(this, invoker);
        popupMenu.inflate(R.menu.dialtacts_options);
        popupMenu.setOnMenuItemClickListener(this);
        return popupMenu;
    }
}
