package com.geniusgithub.dialer.emergency;

import android.content.Context;
import android.location.Country;
import android.location.CountryDetector;
import android.os.SystemProperties;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.i18n.phonenumbers.ShortNumberUtil;

import java.util.Locale;

public class EmergencyPhoneNumberUtil {

    private final static String LOG_TAG = EmergencyPhoneNumberUtil.class.getSimpleName();

    private static Country sCountryDetector = null;

    /*
 * Special characters
 *
 * (See "What is a phone number?" doc)
 * 'p' --- GSM pause character, same as comma
 * 'n' --- GSM wild character
 * 'w' --- GSM wait character
 */
    public static final char PAUSE = ',';
    public static final char WAIT = ';';
    public static final char WILD = 'N';


    /** True if c is ISO-LATIN characters 0-9, *, # , +, WILD  */
    public final static boolean
    isDialable(char c) {
        return (c >= '0' && c <= '9') || c == '*' || c == '#' || c == '+' || c == WILD;
    }


    /** This any anything to the right of this char is part of the
     *  post-dial string (eg this is PAUSE or WAIT)
     */
    public final static boolean
    isStartsPostDial (char c) {
        return c == PAUSE || c == WAIT;
    }


    public static boolean isLocalEmergencyNumber(Context context, int subId, String number) {
        return isLocalEmergencyNumberInternal(subId, number, context, true);
    }


    public static boolean isPotentialLocalEmergencyNumber(Context context, int subId, String number) {
        return isLocalEmergencyNumberInternal(subId, number,context, false);
    }




    private static boolean isLocalEmergencyNumberInternal(int subId, String number,
                                                          Context context,
                                                          boolean useExactMatch) {
        String countryIso = getCountryIso(context);
        Log.w(LOG_TAG, "isLocalEmergencyNumberInternal" + countryIso);
        if (countryIso == null) {
            Locale locale = context.getResources().getConfiguration().locale;
            if(locale != null) {
                countryIso = locale.getCountry();
                Log.w(LOG_TAG, "No CountryDetector; falling back to countryIso based on locale: "
                        + countryIso);
            } else {
                countryIso = "US"; //default value is "US"
            }
        }
        return isEmergencyNumberInternal(subId, number, countryIso, useExactMatch);
    }


    private static boolean isEmergencyNumberInternal(int subId, String number,
                                                     String defaultCountryIso,
                                                     boolean useExactMatch) {
        // If the number passed in is null, just return false:
        if (number == null) return false;

        // If the number passed in is a SIP address, return false, since the
        // concept of "emergency numbers" is only meaningful for calls placed
        // over the cell network.
        // (Be sure to do this check *before* calling extractNetworkPortionAlt(),
        // since the whole point of extractNetworkPortionAlt() is to filter out
        // any non-dialable characters (which would turn 'abc911def@example.com'
        // into '911', for example.))
        if (isUriNumber(number)) {
            return false;
        }

        // Strip the separators from the number before comparing it
        // to the list.
        number = extractNetworkPortionAlt(number);

        String emergencyNumbers = "";

        int phoneId  = SubscriptionManager.getPhoneId(subId);
        if (!SubscriptionManager.isValidPhoneId(phoneId)) {
            phoneId = 0;
        }
        int slotId = phoneId;//SubscriptionManager.getSlotId(subId);


        // retrieve the list of emergency numbers
        // check read-write ecclist property first
        String ecclist = (slotId <= 0) ? "ril.ecclist" : ("ril.ecclist" + slotId);

        emergencyNumbers = SystemProperties.get(ecclist, "");

        Log.d(LOG_TAG, "slotId:" + slotId + " subId:" + subId + " country:"
                + defaultCountryIso + " emergencyNumbers: " +  emergencyNumbers);

        if (TextUtils.isEmpty(emergencyNumbers)) {
            // then read-only ecclist property since old RIL only uses this
            emergencyNumbers = SystemProperties.get("ro.ril.ecclist");
        }

        if (!TextUtils.isEmpty(emergencyNumbers)) {
            // searches through the comma-separated list for a match,
            // return true if one is found.
            for (String emergencyNum : emergencyNumbers.split(",")) {
                // It is not possible to append additional digits to an emergency number to dial
                // the number in Brazil - it won't connect.
                if (useExactMatch || "BR".equalsIgnoreCase(defaultCountryIso)) {
                    if (number.equals(emergencyNum)) {
                        return true;
                    }
                } else {
                    if (number.startsWith(emergencyNum)) {
                        return true;
                    }
                }
            }
            // no matches found against the list!
            return false;
        }

        Log.d(LOG_TAG, "System property doesn't provide any emergency numbers."
                + " Use embedded logic for determining ones.");

        // If slot id is invalid, means that there is no sim card.
        // According spec 3GPP TS22.101, the following numbers should be
        // ECC numbers when SIM/USIM is not present.
        emergencyNumbers = ((slotId < 0) ? "112,911,000,08,110,118,119,999" : "112,911");

        for (String emergencyNum : emergencyNumbers.split(",")) {
            if (useExactMatch) {
                if (number.equals(emergencyNum)) {
                    return true;
                }
            } else {
                if (number.startsWith(emergencyNum)) {
                    return true;
                }
            }
        }

        // No ecclist system property, so use our own list.
        if (defaultCountryIso != null) {
            ShortNumberUtil util = new ShortNumberUtil();
            if (useExactMatch) {
                return util.isEmergencyNumber(number, defaultCountryIso);
            } else {
                return util.connectsToEmergencyNumber(number, defaultCountryIso);
            }
        }

        return false;
    }








    public static String extractNetworkPortionAlt(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }

        int len = phoneNumber.length();
        StringBuilder ret = new StringBuilder(len);
        boolean haveSeenPlus = false;

        for (int i = 0; i < len; i++) {
            char c = phoneNumber.charAt(i);
            if (c == '+') {
                if (haveSeenPlus) {
                    continue;
                }
                haveSeenPlus = true;
            }
            if (isDialable(c)) {
                ret.append(c);
            } else if (isStartsPostDial (c)) {
                break;
            }
        }

        return ret.toString();
    }

    public static boolean isUriNumber(String number) {
        // Note we allow either "@" or "%40" to indicate a URI, in case
        // the passed-in string is URI-escaped.  (Neither "@" nor "%40"
        // will ever be found in a legal PSTN number.)
        return number != null && (number.contains("@") || number.contains("%40"));
    }


    private static int getDefaultVoiceSubId() {
        return SubscriptionManager.getDefaultVoiceSubscriptionId();
    }


    private static String getCountryIso(Context context) {
        Log.w(LOG_TAG, "getCountryIso " + sCountryDetector);
        if (sCountryDetector == null) {
            CountryDetector detector = (CountryDetector) context.getSystemService(
                    Context.COUNTRY_DETECTOR);
            if (detector != null) {
                sCountryDetector = detector.detectCountry();
            }
        }

        if (sCountryDetector == null) {
            return null;
        } else {
            return sCountryDetector.getCountryIso();
        }
    }


}
