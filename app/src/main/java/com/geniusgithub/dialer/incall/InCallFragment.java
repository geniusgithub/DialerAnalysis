package com.geniusgithub.dialer.incall;


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
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.telecom.InCallService;
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
import com.geniusgithub.dialer.sim.LoadMethod;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@SuppressLint("NewApi")
public class InCallFragment extends Fragment implements View.OnClickListener{

    private final static String TAG = InCallFragment.class.getSimpleName();
    private Context mContext;

    private View mRootView;
    private Button mBtnInCallInfo;
    private TextView mTvAllInfo;
    private TextView mTvDialerInfo;
    private TextView mTvSystemInfo;

    private StringBuffer mStringBufferAll;
    private StringBuffer mStringBufferDialer;
    private StringBuffer mStringBufferInCall;


    private ComponentName mSystemInCallComponentName;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView  =  LayoutInflater.from(mContext).inflate(R.layout.activity_incall_layout, null);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBtnInCallInfo = (Button) view.findViewById(R.id.bt_listall);
        mBtnInCallInfo.setOnClickListener(this);
        mTvAllInfo = (TextView) view.findViewById(R.id.tv_all_info);
        mTvDialerInfo = (TextView) view.findViewById(R.id.tv_dialer_info);
        mTvSystemInfo = (TextView) view.findViewById(R.id.tv_system_incall_info);

        mSystemInCallComponentName = getSystemInCallComponent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_listall:
                updateInfo();
                break;
        }
    }

    private void updateInfo(){
        ListAllInCallComponent();
        ListDefaultDialerComponent();
        ListSystemInCallComponent();
    }


    private void ListAllInCallComponent() {
        mStringBufferAll = new StringBuffer("all incall\n");
        getInCallServiceComponent(null, IN_CALL_SERVICE_TYPE_INVALID, mStringBufferAll);
        mTvAllInfo.setText(mStringBufferAll.toString());
    }

    private InCallServiceInfo ListDefaultDialerComponent() {
        String packageName = getDefaultDialerApplication();
        String value = "Default Dialer package: " + packageName;
        Log.d(TAG, value);
        mStringBufferDialer = new StringBuffer(value + "\n");

        getInCallServiceComponent(packageName, IN_CALL_SERVICE_TYPE_INVALID, mStringBufferDialer);
        mTvDialerInfo.setText(mStringBufferDialer.toString());
        return null;
    }

    private InCallServiceInfo ListSystemInCallComponent() {
        String value = "mSystemInCallComponentName: " + mSystemInCallComponentName;
        Log.d(TAG, value);
        mStringBufferInCall =  new StringBuffer(value + "\n");
        getInCallServiceComponentEx(mSystemInCallComponentName, IN_CALL_SERVICE_TYPE_INVALID, mStringBufferInCall);
        mTvSystemInfo.setText(mStringBufferInCall.toString());
        return null;
    }



    public String getDefaultDialerApplication(){
        return getTelecomManager(mContext).getDefaultDialerPackage();
    }

    private static final String incall_default_class = "com.android.incallui.InCallServiceImpl";
    public ComponentName getSystemInCallComponent(){
        return new ComponentName(getTelecomManager(mContext).getSystemDialerPackage(),incall_default_class);
    }

    private static final int IN_CALL_SERVICE_TYPE_INVALID = 0;
    private static final int IN_CALL_SERVICE_TYPE_DIALER_UI = 1;
    private static final int IN_CALL_SERVICE_TYPE_SYSTEM_UI = 2;
    private static final int IN_CALL_SERVICE_TYPE_CAR_MODE_UI = 3;
    private static final int IN_CALL_SERVICE_TYPE_NON_UI = 4;

    private InCallServiceInfo getInCallServiceComponentEx(ComponentName componentName, int type,  StringBuffer stringBuilder) {
        List<InCallServiceInfo> list = getInCallServiceComponentsEx(componentName, type, stringBuilder);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        } else {
            // Last Resort: Try to bind to the ComponentName given directly.
            Log.e(TAG, "Package Manager could not find ComponentName: "
                    + componentName +". Trying to bind anyway.");
            return new InCallServiceInfo(componentName, false, false, type);
        }
    }

    private InCallServiceInfo getInCallServiceComponent(String packageName, int type, StringBuffer stringBuilder) {
        List<InCallServiceInfo> list = getInCallServiceComponents(packageName, type, stringBuilder);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }


    private List<InCallServiceInfo> getInCallServiceComponentsEx(ComponentName componentName,
                                                               int type,  StringBuffer stringBuilder) {
        return getInCallServiceComponents(null, componentName, type, stringBuilder);
    }


    private List<InCallServiceInfo> getInCallServiceComponents(String packageName, int type,  StringBuffer stringBuilder) {
        return getInCallServiceComponents(packageName, null, type, stringBuilder);
    }






    private List<InCallServiceInfo> getInCallServiceComponents(
            String packageName, ComponentName componentName, int requestedType, StringBuffer stringBuilder) {

        List<InCallServiceInfo> retval = new LinkedList<>();

        Intent serviceIntent = new Intent(InCallService.SERVICE_INTERFACE);
        if (packageName != null) {
            serviceIntent.setPackage(packageName);
        }
        if (componentName != null) {
            serviceIntent.setComponent(componentName);
        }

        PackageManager packageManager = mContext.getPackageManager();
        List<ResolveInfo> resolveInfoList =  packageManager.queryIntentServices(
                serviceIntent, PackageManager.GET_META_DATA);
        String value = "resolveInfoList.size = " + resolveInfoList.size();
        Log.d(TAG, value);
        stringBuilder.append(value + "\n");

        for (ResolveInfo entry : resolveInfoList) {
            ServiceInfo serviceInfo = entry.serviceInfo;

            if (serviceInfo != null) {
                boolean isExternalCallsSupported = serviceInfo.metaData != null &&
                        serviceInfo.metaData.getBoolean(
                                TelecomManager.METADATA_INCLUDE_EXTERNAL_CALLS, false);
                boolean isSelfManageCallsSupported = serviceInfo.metaData != null &&
                        serviceInfo.metaData.getBoolean(
                                TelecomManager.METADATA_INCLUDE_SELF_MANAGED_CALLS, false);

                int currentType = getInCallServiceType(entry.serviceInfo, packageManager);
                String value1 = "pn = " + serviceInfo.packageName + "\nname = " + serviceInfo.name + "\ngetInCallServiceType = " + currentType;
                Log.d(TAG, value1);
                stringBuilder.append(value1 + "\n");
                if (requestedType == 0 || requestedType == currentType) {
                    if (requestedType == IN_CALL_SERVICE_TYPE_NON_UI) {
                        // We enforce the rule that self-managed calls are not supported by non-ui
                        // InCallServices.
                        isSelfManageCallsSupported = false;
                    }
                    retval.add(new InCallServiceInfo(
                            new ComponentName(serviceInfo.packageName, serviceInfo.name),
                            isExternalCallsSupported, isSelfManageCallsSupported, requestedType));
                }
            }
        }

        return retval;
    }

    private int getInCallServiceType(ServiceInfo serviceInfo, PackageManager packageManager) {
        // Verify that the InCallService requires the BIND_INCALL_SERVICE permission which
        // enforces that only Telecom can bind to it.
        boolean hasServiceBindPermission = serviceInfo.permission != null &&
                serviceInfo.permission.equals(
                        Manifest.permission.BIND_INCALL_SERVICE);
        if (!hasServiceBindPermission) {
            Log.e(TAG, "InCallService does not require BIND_INCALL_SERVICE permission: " +
                    serviceInfo.packageName);
            return IN_CALL_SERVICE_TYPE_INVALID;
        }

        if (mSystemInCallComponentName.getPackageName().equals(serviceInfo.packageName) &&
                mSystemInCallComponentName.getClassName().equals(serviceInfo.name)) {
            return IN_CALL_SERVICE_TYPE_SYSTEM_UI;
        }

        // Check to see if the service is a car-mode UI type by checking that it has the
        // CONTROL_INCALL_EXPERIENCE (to verify it is a system app) and that it has the
        // car-mode UI metadata.
        boolean hasControlInCallPermission = packageManager.checkPermission(
                Manifest.permission.CONTROL_INCALL_EXPERIENCE,
                serviceInfo.packageName) == PackageManager.PERMISSION_GRANTED;
        boolean isCarModeUIService = serviceInfo.metaData != null &&
                serviceInfo.metaData.getBoolean(
                        TelecomManager.METADATA_IN_CALL_SERVICE_CAR_MODE_UI, false) &&
                hasControlInCallPermission;
        if (isCarModeUIService) {
            return IN_CALL_SERVICE_TYPE_CAR_MODE_UI;
        }

        // Check to see that it is the default dialer package
        boolean isDefaultDialerPackage = Objects.equals(serviceInfo.packageName,
                getDefaultDialerApplication());
        boolean isUIService = serviceInfo.metaData != null &&
                serviceInfo.metaData.getBoolean(
                        TelecomManager.METADATA_IN_CALL_SERVICE_UI, false);
        if (isDefaultDialerPackage && isUIService) {
            return IN_CALL_SERVICE_TYPE_DIALER_UI;
        }

        // Also allow any in-call service that has the control-experience permission (to ensure
        // that it is a system app) and doesn't claim to show any UI.
        if (hasControlInCallPermission && !isUIService) {
            return IN_CALL_SERVICE_TYPE_NON_UI;
        }

        // Anything else that remains, we will not bind to.
        Log.e(TAG, "skipping binding");
/*        Log.i(TAG, "Skipping binding to %s:%s, control: %b, car-mode: %b, ui: %b",
                serviceInfo.packageName, serviceInfo.name, hasControlInCallPermission,
                isCarModeUIService, isUIService);*/
        return IN_CALL_SERVICE_TYPE_INVALID;
    }

    private static TelecomManager getTelecomManager(Context context) {
        return (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
    }
}
