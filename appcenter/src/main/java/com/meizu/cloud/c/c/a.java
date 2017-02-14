package com.meizu.cloud.c.c;

import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.security.MessageDigest;

public class a {
    private static final String[] a = new String[]{"0", PushConstants.CLICK_TYPE_ACTIVITY, PushConstants.CLICK_TYPE_WEB, "3", "4", "5", "6", "7", "8", "9", "a", "cancel", "c", "d", "e", "f"};

    public static String a(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (byte a : b) {
            resultSb.append(a(a, true));
        }
        return resultSb.toString();
    }

    public static String a(byte[] b, int beginPos, int length) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = beginPos; i < length + beginPos; i++) {
            resultSb.append(a(b[i], true));
        }
        return resultSb.toString();
    }

    private static String a(byte b, boolean bigEnding) {
        int n = b;
        if (n < (byte) 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return bigEnding ? a[d1] + a[d2] : a[d2] + a[d1];
    }

    public static byte[] b(byte[] origin) {
        try {
            return MessageDigest.getInstance("MD5").digest(origin);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
