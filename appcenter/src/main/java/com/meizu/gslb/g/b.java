package com.meizu.gslb.g;

import org.apache.http.conn.util.InetAddressUtils;

public class b {
    public static boolean a(String str) {
        return InetAddressUtils.isIPv4Address(str) || InetAddressUtils.isIPv6Address(str);
    }
}
