package com.geniusgithub.dialer.calllog;

import android.database.Cursor;

public class CallDetail {
    public String number;
    public long date;
    public long duration;
    public int calltype;

    public static CallDetail from(Cursor cursor){
        CallDetail object = new CallDetail();
        object.number = cursor.getString(CallLogQuery.NUMBER);
        object.date = cursor.getLong(CallLogQuery.DATE);
        object.duration = cursor.getLong(CallLogQuery.DURATION);
        object.calltype = cursor.getInt(CallLogQuery.CALL_TYPE);
        return object;
    }
}
