package com.geniusgithub.dialer.sim;

import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LoadMethod {
	private final static String TAG = LoadMethod.class.getSimpleName();

	  public static boolean TelephonyManager_isVolteAvailable(TelephonyManager telephonyManager) {
	    	Class callClass = null;
			Method isVolteAvailable = null;
			Object result = null;

			try {
				try {
					callClass = Class.forName("android.telephony.TelephonyManager");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				if(callClass != null)
				{
					isVolteAvailable = callClass.getMethod("isVolteAvailable", new Class[] {});
				}
			} catch (NoSuchMethodException e) {
				 e.printStackTrace();
			}
			
			if ((isVolteAvailable != null)) {
				try {
					isVolteAvailable.setAccessible(true);
					result = isVolteAvailable.invoke(telephonyManager, new Object[] {});
					Log.i(TAG, "invoke isVolteAvailable result = " + result);
				} catch (IllegalArgumentException e1) {
					 e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					 e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					 e1.printStackTrace();
				}
			}
			
			if(result != null)
			{
				return (Boolean)result;
			}
			else {
				return false;
			}
	    }



	public static boolean TelephonyManager_isVolteAvailableUsingSubId(TelephonyManager telephonyManager, int subID) {
		Class callClass = null;
		Method isVolteAvailableUsingSubId = null;
		Object result = null;

		try {
			try {
				callClass = Class.forName("android.telephony.TelephonyManager");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			if(callClass != null)
			{
				isVolteAvailableUsingSubId = callClass.getMethod("isVolteAvailableUsingSubId", new Class[] {Integer.TYPE});
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			Log.e(TAG, "no such method for isVolteAvailableUsingSubId");
		}

		if ((isVolteAvailableUsingSubId != null)) {
			try {
				isVolteAvailableUsingSubId.setAccessible(true);
				result = isVolteAvailableUsingSubId.invoke(telephonyManager, new Object[] {subID});
				Log.i(TAG, "invoke isVolteAvailableUsingSubId subID = " + subID + ", result = " + result);
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				e1.printStackTrace();
			}
		}else{
			Log.e(TAG, "can't find method:isVolteAvailableUsingSubId from android.telephony.TelephonyManager!!!");
		}

		if(result != null)
		{
			return (Boolean)result;
		}
		else {
			return false;
		}
	}

	public static Method[] TelephonyManager_MethodList(){
		Class callClass = null;
		Method isVolteAvailableUsingSubId = null;
		Object result = null;


		try {
			callClass = Class.forName("android.telephony.TelephonyManager");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		if(callClass != null)
		{
			return callClass.getMethods();
		}

		return null;
	}


}
