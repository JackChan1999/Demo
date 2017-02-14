package com.meizu.cloud.app.utils;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public final class n {
    private static final String a = new String(new char[]{'U', 'T', 'F', '8'});
    private final String b;

    private static final void a(long l, byte[] bytes, int off) {
        int end = Math.min(bytes.length, off + 8);
        for (int i = off; i < end; i++) {
            bytes[i] = (byte) ((int) l);
            l >>= 8;
        }
    }

    public n(long[] obfuscated) {
        int i;
        int length = obfuscated.length;
        byte[] encoded = new byte[((length - 1) * 8)];
        Random prng = new Random(obfuscated[0]);
        for (i = 1; i < length; i++) {
            a(obfuscated[i] ^ prng.nextLong(), encoded, (i - 1) * 8);
        }
        try {
            String decoded = new String(encoded, a);
            i = decoded.indexOf(0);
            if (i != -1) {
                decoded = decoded.substring(0, i);
            }
            this.b = decoded;
        } catch (UnsupportedEncodingException ex) {
            throw new AssertionError(ex);
        }
    }

    public String toString() {
        return this.b;
    }
}
