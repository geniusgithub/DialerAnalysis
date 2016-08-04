package com.geniusgithub.dialer.calllog;

import android.database.Cursor;

public class CallDetail {
    public String number;
    public long date;
    public long duration;
    public int calltype;


    public String name;
    public String formattedNumber;
    public int numbertype;
    public String numberLabel;
    public String matchnumber;
    public String lookupUri;

    public static CallDetail from(Cursor cursor){
        CallDetail object = new CallDetail();
        object.number = cursor.getString(CallLogQuery.NUMBER);
        object.date = cursor.getLong(CallLogQuery.DATE);
        object.duration = cursor.getLong(CallLogQuery.DURATION);
        object.calltype = cursor.getInt(CallLogQuery.CALL_TYPE);

        object.name = cursor.getString(CallLogQuery.CACHED_NAME);
        object.formattedNumber = cursor.getString(CallLogQuery.CACHED_FORMATTED_NUMBER);
        object.numbertype = cursor.getInt(CallLogQuery.CACHED_NUMBER_TYPE);
        object.numberLabel = cursor.getString(CallLogQuery.CACHED_NUMBER_LABEL);
        object.matchnumber = cursor.getString(CallLogQuery.CACHED_MATCHED_NUMBER);
        object.lookupUri = cursor.getString(CallLogQuery.CACHED_LOOKUP_URI);
        return object;
    }

    public String extraDetail(){
        String value = "name = " + name + "\nformattedNumber = " + formattedNumber +
                        "\nnumbertype = " + numbertype + "\nnumberLabel = " + numberLabel +
                        "\nmatchnumber = " + matchnumber + "\nlookupUri = " + lookupUri;

        return value;
    }
}

