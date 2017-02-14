package com.meizu.e;

public class a {
    private static final char a = ((char) Integer.parseInt("00000011", 2));
    private static final char b = ((char) Integer.parseInt("00001111", 2));
    private static final char c = ((char) Integer.parseInt("00111111", 2));
    private static final char d = ((char) Integer.parseInt("11111100", 2));
    private static final char e = ((char) Integer.parseInt("11110000", 2));
    private static final char f = ((char) Integer.parseInt("11000000", 2));
    private static final char[] g = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    public String a(byte[] from) {
        int i;
        StringBuffer to = new StringBuffer(((int) (((double) from.length) * 1.34d)) + 3);
        int num = 0;
        char currentByte = '\u0000';
        for (i = 0; i < from.length; i++) {
            num %= 8;
            while (num < 8) {
                switch (num) {
                    case 0:
                        currentByte = (char) (((char) (from[i] & d)) >>> 2);
                        break;
                    case 2:
                        currentByte = (char) (from[i] & c);
                        break;
                    case 4:
                        currentByte = (char) (((char) (from[i] & b)) << 2);
                        if (i + 1 >= from.length) {
                            break;
                        }
                        currentByte = (char) (((from[i + 1] & f) >>> 6) | currentByte);
                        break;
                    case 6:
                        currentByte = (char) (((char) (from[i] & a)) << 4);
                        if (i + 1 >= from.length) {
                            break;
                        }
                        currentByte = (char) (((from[i + 1] & e) >>> 4) | currentByte);
                        break;
                    default:
                        break;
                }
                to.append(g[currentByte]);
                num += 6;
            }
        }
        if (to.length() % 4 != 0) {
            for (i = 4 - (to.length() % 4); i > 0; i--) {
                to.append("=");
            }
        }
        return to.toString();
    }
}
