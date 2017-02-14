package com.meizu.cloud.app.utils;

import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.security.MessageDigest;

public class l {
    private static final String[] a = new String[]{"0", PushConstants.CLICK_TYPE_ACTIVITY, PushConstants.CLICK_TYPE_WEB, "3", "4", "5", "6", "7", "8", "9", "a", "cancel", "c", "d", "e", "f"};

    public static String a(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (byte a : b) {
            resultSb.append(a(a, true));
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

    public static String a(String origin) {
        return a(origin, null);
    }

    public static String a(String origin, String encoding) {
        Exception e;
        try {
            String resultString = new String(origin);
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                if (encoding == null) {
                    return a(md.digest(resultString.getBytes()));
                }
                return a(md.digest(resultString.getBytes(encoding)));
            } catch (Exception e2) {
                e = e2;
                String str = resultString;
                throw new RuntimeException(e);
            }
        } catch (Exception e3) {
            e = e3;
            throw new RuntimeException(e);
        }
    }
}
