package sdk.meizu.traffic.util;

import android.util.Log;
import android.view.View;

public class UrlSpanHelper {
    private static final String TAG = UrlSpanHelper.class.getSimpleName();
    private static final Class<?> sClass = getMyClass();

    private static Class<?> getMyClass() {
        try {
            return Class.forName("android.text.util.UrlSpanHelper");
        } catch (Throwable e) {
            Log.w(TAG, "", e);
            return null;
        }
    }

    public static void showDialog(View view, String str, int i) {
        try {
            sClass.getMethod("showDialog", new Class[]{View.class, String.class, Integer.TYPE}).invoke(null, new Object[]{view, str, Integer.valueOf(i)});
        } catch (Throwable e) {
            Log.w(TAG, "", e);
        }
    }
}
