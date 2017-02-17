package com.geniusgithub.dialer.sim;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.geniusgithub.dialer.R;

@SuppressLint("NewApi")
public class SimInfoFragment extends Fragment implements View.OnClickListener{

    private final static String TAG = SimInfoFragment.class.getSimpleName();
    private Context mContext;
    private View mRootView;
    private TextView mTvSub;
    private Button mBtnSub;
    private TextView mTvMainCard;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView  =  LayoutInflater.from(mContext).inflate(R.layout.subinfo_layout_fragment, null);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTvSub = (TextView) view.findViewById(R.id.tv_sub);
        mBtnSub = (Button) view.findViewById(R.id.btn_sub);
        mBtnSub.setOnClickListener(this);
        mTvMainCard = (TextView) view.findViewById(R.id.tv_mainCard);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sub:
                updateMainCard();
                updateSub();
                break;
        }
    }

    private void updateMainCard(){
        TelecomManager telecomManager = getTelecomManager(mContext);
        PhoneAccountHandle phoneAccountHandle = telecomManager.getUserSelectedOutgoingPhoneAccount();
        int mainSubID = -1;
        if (phoneAccountHandle != null){
            PhoneAccount phoneAccount =  telecomManager.getPhoneAccount(phoneAccountHandle);
            TelephonyManager telephonyManager = getTelephonyManager(mContext);
            mainSubID = telephonyManager.getSubIdForPhoneAccount(phoneAccount);
            Log.d(TAG, "telephonyManager.getSubIdForPhoneAccount  = " + mainSubID);
        }else{
            Log.e(TAG, "getUserSelectedOutgoingPhoneAccount = null");
        }

        final SubscriptionInfo subInfo = SubscriptionManager.from(mContext).getActiveSubscriptionInfo(mainSubID);
        String mainSubInfo = "null";
        if (subInfo != null){
            mainSubInfo = getSimpleSubInfo(subInfo);
        }else{
            Log.e(TAG, "getActiveSubscriptionInfo = null");
        }

        int globalSubID = Settings.Global.getInt(mContext.getContentResolver(),
                Settings.Global.MULTI_SIM_VOICE_CALL_SUBSCRIPTION,
                SubscriptionManager.INVALID_SUBSCRIPTION_ID);

        int globalPrompt = Settings.Global.getInt(mContext.getContentResolver(),
                Settings.Global.MULTI_SIM_VOICE_PROMPT, -1);

        String value = "globalSubID = " + globalSubID + "\nglobalPrompt = " + globalPrompt
                + "\nmainSubID = " + mainSubID + "\nmainSubInfo -->\n" + mainSubInfo + "\n======================\n";
        mTvMainCard.setText(value);
    }

    private void updateSub(){
        SubscriptionManager subscriptionManager = SubscriptionManager.from(mContext);

        int sub1[] = SubscriptionManager.getSubId(0);
        int sub2[] =  SubscriptionManager.getSubId(1);
        int inSub1 = -1;
        if (sub1 != null){
            inSub1 = sub1[0];
        }
        int inSub2 = -1;
        if (sub2 != null){
            inSub2 = sub2[0];
        }

        SubscriptionInfo info1 = subscriptionManager.getActiveSubscriptionInfo(inSub1);
        SubscriptionInfo info2 = subscriptionManager.getActiveSubscriptionInfo(inSub2);

        String value = "slotid = 0\n" + getSubInfo(info1) + "\n======================\nslotid = 1\n" + getSubInfo(info2);
        mTvSub.setText(value);

    }

    public String getSubInfo(SubscriptionInfo info){
        if (info == null){
            return "null";
        }

        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("getCountryIso = " + info.getCountryIso() +
                        "\ngetCarrierName = " + info.getCarrierName() +
                        "\ngetDisplayName = " + info.getDisplayName() +
                        "\ngetIccId = " + info.getIccId() +
                        "\ngetIconTint() = " + info.getIconTint() +
                        "\ngetMcc = " + info.getMcc() +
                        "\ngetMnc = " + info.getMnc() +
                        "\ngetNumber = " + info.getNumber() +
                        "\ngetSimSlotIndex = " + info.getSimSlotIndex() +
                        "\ngetSubscriptionId = " + info.getSubscriptionId());

        boolean isVolteEnable = LoadMethod.TelephonyManager_isVolteAvailableUsingSubId(getTelephonyManager(mContext), info.getSubscriptionId());
        sBuffer.append("\nisVolteAvailableUsingSubId = " + isVolteEnable);


        return sBuffer.toString();

    }

    public String getSimpleSubInfo(SubscriptionInfo info) {
        if (info == null){
            return "null";
        }

        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("getDisplayName = " + info.getDisplayName() +
                "\ngetIccId = " + info.getIccId() +
                "\ngetNumber = " + info.getNumber() +
                "\ngetSimSlotIndex = " + info.getSimSlotIndex() +
                "\ngetSubscriptionId = " + info.getSubscriptionId());

        boolean isVolteEnable = LoadMethod.TelephonyManager_isVolteAvailableUsingSubId(getTelephonyManager(mContext), info.getSubscriptionId());
        sBuffer.append("\nisVolteAvailableUsingSubId = " + isVolteEnable);


        return sBuffer.toString();
    }

    private static TelephonyManager getTelephonyManager(Context context) {
        return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    private static TelecomManager getTelecomManager(Context context) {
        return (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
    }
}
