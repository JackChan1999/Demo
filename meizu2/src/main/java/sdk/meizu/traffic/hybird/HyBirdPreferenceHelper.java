package sdk.meizu.traffic.hybird;

import android.content.Context;

public class HyBirdPreferenceHelper {
    private static final String CONFIG_FILE_MODIFY = "Last-Modified";
    private static final String H5_RESOURCE_MODIFY = "h5_resource_modify";
    private static final String HYBIRD_PREFERENCE = "hybird.prefs";
    private static final String NATIVE_SUPPORT_PREFERENCE_kEY = "mzaccount.SUPPORT_NATIVE_DEBUG";

    public static final void writeH5ResourceModify(Context context, long j) {
        context.getSharedPreferences(HYBIRD_PREFERENCE, 0).edit().putLong(H5_RESOURCE_MODIFY, j).apply();
    }

    public static final long readH5ResourceModify(Context context) {
        return context.getSharedPreferences(HYBIRD_PREFERENCE, 0).getLong(H5_RESOURCE_MODIFY, 0);
    }

    public static final void writeConfigFileModify(Context context, String str) {
        context.getSharedPreferences(HYBIRD_PREFERENCE, 0).edit().putString(CONFIG_FILE_MODIFY, str).apply();
    }

    public static final String readConfigFileModify(Context context) {
        return context.getSharedPreferences(HYBIRD_PREFERENCE, 0).getString(CONFIG_FILE_MODIFY, null);
    }

    public static final void writeNativeSupportPreferenceKey(Context context, boolean z) {
        context.getSharedPreferences(HYBIRD_PREFERENCE, 0).edit().putBoolean(NATIVE_SUPPORT_PREFERENCE_kEY, z).apply();
    }

    public static final boolean readNativeSupportPreferenceKey(Context context) {
        return context.getSharedPreferences(HYBIRD_PREFERENCE, 0).getBoolean(NATIVE_SUPPORT_PREFERENCE_kEY, false);
    }
}
