package com.geniusgithub.dialer.telecom;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telecom.InCallService;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.geniusgithub.dialer.R;
import com.geniusgithub.dialer.incall.InCallServiceInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@SuppressLint("NewApi")
public class TelecomFragment extends Fragment implements View.OnClickListener{

    private final static String TAG = TelecomFragment.class.getSimpleName();
    private Context mContext;

    private View mRootView;
    private Button mBtnGetInfo;
    private TextView mTvContent;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView  =  LayoutInflater.from(mContext).inflate(R.layout.activity_telecom_layout, null);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBtnGetInfo = (Button) view.findViewById(R.id.bt_getinfo);
        mBtnGetInfo.setOnClickListener(this);
        mTvContent = (TextView) view.findViewById(R.id.tv_content);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_getinfo:
                updateInfo();
                break;
        }
    }

    private void updateInfo(){
        StringBuffer stringBuffer = new StringBuffer();

        List<PhoneAccountHandle> phoneAccountHandles = getCallCapablePhoneAccounts();
        stringBuffer.append("getCallCapablePhoneAccounts = \n");
        stringBuffer.append(PhoneAccountHandleToString(phoneAccountHandles));
        stringBuffer.append("\n\n");


        PhoneAccountHandle phoneAccountHandles1 = getDefaultOutgoingPhoneAccount(PhoneAccount.SCHEME_TEL);
        stringBuffer.append("getDefaultOutgoingPhoneAccount = \n");
        stringBuffer.append(PhoneAccountHandleToString(phoneAccountHandles1));
        stringBuffer.append("\n\n");

        PhoneAccountHandle phoneAccountHandles2 = getUserSelectedOutgoingPhoneAccount();
        stringBuffer.append("getUserSelectedOutgoingPhoneAccount = \n");
        stringBuffer.append(PhoneAccountHandleToString(phoneAccountHandles2));
        stringBuffer.append("\n\n");

        PhoneAccountHandle phoneAccountHandles3 = getSimCallManager();
        stringBuffer.append("getSimCallManager = \n");
        stringBuffer.append(PhoneAccountHandleToString(phoneAccountHandles3));
        stringBuffer.append("\n\n");

        Log.d(TAG, stringBuffer.toString());
        mTvContent.setText(stringBuffer.toString());
    }

    private static TelecomManager getTelecomManager(Context context) {
        return (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
    }

    public PhoneAccountHandle getDefaultOutgoingPhoneAccount(String uriScheme){
        return getTelecomManager(mContext).getDefaultOutgoingPhoneAccount(uriScheme);
    }

    public PhoneAccountHandle getUserSelectedOutgoingPhoneAccount() {
        return getTelecomManager(mContext).getUserSelectedOutgoingPhoneAccount();
    }

    public List<PhoneAccountHandle> getCallCapablePhoneAccounts() {
        return getTelecomManager(mContext).getCallCapablePhoneAccounts();
    }

    public PhoneAccountHandle getSimCallManager() {
        return getTelecomManager(mContext).getSimCallManager();
    }

    public String PhoneAccountHandleToString(List<PhoneAccountHandle> phoneAccountHandles){
        StringBuffer stringBuffer = new StringBuffer();
        int index = 0;
        for(PhoneAccountHandle handle : phoneAccountHandles){
            stringBuffer.append("index = " + index + "\n" + PhoneAccountHandleToString(handle) + "\n");
            index++;
        }
        return stringBuffer.toString();
    }

    public String PhoneAccountHandleToString(PhoneAccountHandle phoneAccountHandle){
         StringBuffer stringBuffer = new StringBuffer();
         if (phoneAccountHandle != null){
             PhoneAccount phoneAccount = getTelecomManager(mContext).getPhoneAccount(phoneAccountHandle);
             if (phoneAccount != null){
                 stringBuffer.append("mID = " + phoneAccountHandle.getId());
                 stringBuffer.append("\nmAddress = " + phoneAccount.getAddress());
                 stringBuffer.append("\nmLabel = " + phoneAccount.getLabel());
                 stringBuffer.append("\nmShortDescription = " + phoneAccount.getShortDescription());
                 stringBuffer.append("\nphoneAccount = " + phoneAccount);
             }
         }
        return stringBuffer.toString();
    }

}
