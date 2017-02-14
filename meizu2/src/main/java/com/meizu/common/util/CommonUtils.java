package com.meizu.common.util;

import android.os.Build;
import android.preference.PreferenceActivity;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import java.lang.reflect.Method;

public class CommonUtils {
    private static final String TAG = "CommonUtils";
    private static Boolean isFlymeOS = null;
    private static Boolean isFlymeOS4 = null;
    private static Method mMethodGetString;

    public static boolean isFlymeOS() {
        try {
            if (isFlymeOS != null) {
                return isFlymeOS.booleanValue();
            }
            if (isFlymeOS4()) {
                isFlymeOS = Boolean.TRUE;
            } else {
                isFlymeOS = Boolean.FALSE;
            }
            return isFlymeOS.booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isFlymeOS4() {
        try {
            if (isFlymeOS4 != null) {
                return isFlymeOS4.booleanValue();
            }
            String str = Build.DISPLAY;
            String systemProperties = getSystemProperties(CommonConstants.BUILD_DESCRIPTION);
            if (str.startsWith("Flyme OS 4") || (systemProperties != null && systemProperties.matches(CommonConstants.IS_FLYME_OS_4_MATCH))) {
                isFlymeOS4 = Boolean.TRUE;
            } else {
                isFlymeOS4 = Boolean.FALSE;
            }
            return isFlymeOS4.booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    public static String getSystemProperties(String str) {
        try {
            Class cls = Build.class;
            if (mMethodGetString == null) {
                mMethodGetString = cls.getDeclaredMethod("getString", new Class[]{String.class});
                mMethodGetString.setAccessible(true);
            }
            return (String) mMethodGetString.invoke(null, new Object[]{str});
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean setDarkStatusBarIcon(Window window, boolean z) {
        return false;
    }

    public static boolean PreferenceActivity_setActionBarToTop(PreferenceActivity preferenceActivity, boolean z) {
        return false;
    }

    public static boolean TextView_setEmojiAlphaEnabled(TextView textView, boolean z) {
        if (isFlymeOS()) {
            try {
                Class.forName("android.widget.TextView").getMethod("setEmojiAlphaEnabled", new Class[]{Boolean.TYPE}).invoke(textView, new Object[]{Boolean.valueOf(z)});
                return true;
            } catch (Exception e) {
                Log.e(TAG, "TextView.setEmojiAlphaEnabled fail to be invoked!");
            }
        }
        return false;
    }

    public static boolean TextView_startSelectionActionMode(TextView textView) {
        if (isFlymeOS()) {
            try {
                return ((Boolean) Class.forName("android.widget.TextView").getMethod("startSelectionActionMode", new Class[0]).invoke(textView, new Object[0])).booleanValue();
            } catch (Exception e) {
                Log.e(TAG, "TextView.startSelectionActionMode fail to be invoked!");
            }
        }
        return false;
    }

    public SpannableStringBuilder createSpannableStringBuilder(CharSequence charSequence, int i, int i2) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
        if (charSequence != null) {
            spannableStringBuilder.setSpan(new AbsoluteSizeSpan(i, true), 0, charSequence.length(), 34);
            spannableStringBuilder.setSpan(new ForegroundColorSpan(i2), 0, charSequence.length(), 34);
        }
        return spannableStringBuilder;
    }
}
