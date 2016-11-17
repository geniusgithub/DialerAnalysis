package com.geniusgithub.dialer.emergency;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.SystemProperties;
import com.geniusgithub.dialer.R;

public class EmergencyActivity extends Activity implements View.OnClickListener {

    private Button mBtnShow;
    private TextView mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_emergency);

        mBtnShow = (Button) findViewById(R.id.bt_show);
        mBtnShow.setOnClickListener(this);

        mContent = (TextView) findViewById(R.id.tv_content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_show:
                show();
                break;
        }
    }

    private void show(){
        StringBuilder stringBuilder = new StringBuilder();
        String RilEcclist = getRilEcclist();
        String RilEcclist1 = getRilEcclist1();
        String RoRilEcclist = getRoRilEcclist();
        stringBuilder.append("ril.ecclist = " + RilEcclist +
                              "\nril.ecclist1 = " + RilEcclist1 +
                               "\nro.ril.ecclist = " + RoRilEcclist +
                                "\ngetSLOT0 = " + getSLOT0() +
                                    "\ngetSLOT1 = " + getSLOT1());

        mContent.setText(stringBuilder.toString());
    }

    public String getSLOT0(){
        String value = "";
        value = getRilEcclist();
        if (TextUtils.isEmpty(value)){
            value = getRoRilEcclist();
        }

        if (TextUtils.isEmpty(value)){
            value ="112,911";
        }

        return value;
    }



    public String getSLOT1(){
        String value = "";
        value = getRilEcclist1();
        if (TextUtils.isEmpty(value)){
            value = getRoRilEcclist();
        }

        if (TextUtils.isEmpty(value)){
            value ="112,911";
        }

        return value;
    }

    public String getRilEcclist(){
        return SystemProperties.get("ril.ecclist", "");
    }

    public String getRilEcclist1(){
        return SystemProperties.get("ril.ecclist1", "");
    }

    public String getRoRilEcclist(){
        return  SystemProperties.get("ro.ril.ecclist");
    }

}
