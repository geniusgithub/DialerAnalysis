package com.geniusgithub.dialer.sim;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SubscriptionManageReflect {
    private static final String CLASS_NAME = "android.telephony.SubscriptionManager";

    static Class mCallClass = null;

    static{
        try {
            mCallClass = Class.forName(CLASS_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int static_getSubId(int slotID) {
        if (mCallClass == null){
            return -1;
        }

        Method getSubId = null;
        Object result = null;

        try {
            getSubId = mCallClass.getDeclaredMethod(
                    "getSubId", new Class[] {Integer.TYPE});

            if ((getSubId != null)) {
                try {
                    getSubId.setAccessible(true);
                    result = getSubId.invoke(mCallClass, new Object[] {slotID});
                } catch (IllegalArgumentException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if(result != null)
        {
            int []subIDs = (int[]) result;
            return subIDs[0];
        }else {
            return -1;
        }
    }
}
