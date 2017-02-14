package com.meizu.update.d.d;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import org.apache.http.conn.util.InetAddressUtils;

public class b {
    public static String a() {
        try {
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {
                Enumeration<InetAddress> enumIPAddr = ((NetworkInterface) enumeration.nextElement()).getInetAddresses();
                while (enumIPAddr.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) enumIPAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(inetAddress.getHostAddress())) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static String a(String host) {
        String address = null;
        try {
            if (InetAddressUtils.isIPv4Address(host)) {
                address = host;
            } else {
                InetAddress inetAddress = InetAddress.getByName(host);
                if (inetAddress != null) {
                    address = inetAddress.getHostAddress();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address == null ? "" : address;
    }
}
