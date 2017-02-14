package com.meizu.gslb.g;

import android.util.Base64;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

public class e {
    public static boolean a(String str, String str2, String str3) {
        try {
            PublicKey generatePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(str3, 2)));
            Signature instance = Signature.getInstance("MD5withRSA");
            instance.initVerify(generatePublic);
            instance.update(str.getBytes("UTF-8"));
            return instance.verify(Base64.decode(str2, 2));
        } catch (Exception e) {
            a.d("check rsa sign exception:" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
