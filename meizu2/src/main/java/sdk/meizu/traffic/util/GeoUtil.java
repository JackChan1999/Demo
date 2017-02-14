package sdk.meizu.traffic.util;

import android.content.Context;
import com.meizu.i18n.phonenumbers.NumberParseException;
import com.meizu.i18n.phonenumbers.PhoneNumberUtil;
import com.meizu.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;

public class GeoUtil {
    public static String getCurrentCountryIso(Context context) {
        return CountryDetector.getInstance(context).getCurrentCountryIso();
    }

    public static String getGeocodedLocationFor(Context context, String str) {
        try {
            return PhoneNumberOfflineGeocoder.getInstance().getDescriptionForNumber(PhoneNumberUtil.getInstance().parse(str, getCurrentCountryIso(context)), context.getResources().getConfiguration().locale);
        } catch (NumberParseException e) {
            return null;
        }
    }
}
