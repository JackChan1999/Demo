package sdk.meizu.traffic.util;

import android.content.Context;
import android.location.Geocoder;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.util.Locale;

public class CountryDetector {
    private static final long DISTANCE_BETWEEN_UPDATES_METERS = 5000;
    public static final String KEY_PREFERENCE_CURRENT_COUNTRY = "preference_current_country";
    public static final String KEY_PREFERENCE_TIME_UPDATED = "preference_time_updated";
    private static final String TAG = "CountryDetector";
    private static final long TIME_BETWEEN_UPDATES_MS = 43200000;
    private static CountryDetector sInstance;
    private final String DEFAULT_COUNTRY_ISO;
    private final Context mContext;
    private final LocaleProvider mLocaleProvider;
    private final LocationManager mLocationManager;
    private final TelephonyManager mTelephonyManager;

    public static class LocaleProvider {
        public Locale getDefaultLocale() {
            return Locale.getDefault();
        }
    }

    private CountryDetector(Context context) {
        this(context, (TelephonyManager) context.getSystemService("phone"), (LocationManager) context.getSystemService("location"), new LocaleProvider());
    }

    private CountryDetector(Context context, TelephonyManager telephonyManager, LocationManager locationManager, LocaleProvider localeProvider) {
        this.DEFAULT_COUNTRY_ISO = "US";
        this.mTelephonyManager = telephonyManager;
        this.mLocationManager = locationManager;
        this.mLocaleProvider = localeProvider;
        this.mContext = context;
    }

    public static synchronized CountryDetector getInstance(Context context) {
        CountryDetector countryDetector;
        synchronized (CountryDetector.class) {
            if (sInstance == null) {
                sInstance = new CountryDetector(context.getApplicationContext());
            }
            countryDetector = sInstance;
        }
        return countryDetector;
    }

    public String getCurrentCountryIso() {
        String str = null;
        if (isNetworkCountryCodeAvailable()) {
            str = getNetworkBasedCountryIso();
        }
        if (TextUtils.isEmpty(str)) {
            str = getLocationBasedCountryIso();
        }
        if (TextUtils.isEmpty(str)) {
            str = getSimBasedCountryIso();
        }
        if (TextUtils.isEmpty(str)) {
            str = getLocaleBasedCountryIso();
        }
        if (TextUtils.isEmpty(str)) {
            str = "US";
        }
        return str.toUpperCase(Locale.US);
    }

    private String getNetworkBasedCountryIso() {
        return this.mTelephonyManager.getNetworkCountryIso();
    }

    private String getLocationBasedCountryIso() {
        if (Geocoder.isPresent()) {
            return PreferenceManager.getDefaultSharedPreferences(this.mContext).getString(KEY_PREFERENCE_CURRENT_COUNTRY, null);
        }
        return null;
    }

    private String getSimBasedCountryIso() {
        return this.mTelephonyManager.getSimCountryIso();
    }

    private String getLocaleBasedCountryIso() {
        Locale defaultLocale = this.mLocaleProvider.getDefaultLocale();
        if (defaultLocale != null) {
            return defaultLocale.getCountry();
        }
        return null;
    }

    private boolean isNetworkCountryCodeAvailable() {
        return this.mTelephonyManager.getPhoneType() == 1;
    }
}
