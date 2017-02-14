package com.meizu.e;

import android.util.Log;
import com.meizu.cloud.app.utils.c;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class e {
    private static final String[] a = new String[]{"0", PushConstants.CLICK_TYPE_ACTIVITY, PushConstants.CLICK_TYPE_WEB, "3", "4", "5", "6", "7", "8", "9", "name", "value", "c", "d", "e", "f"};

    public static String a(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        if (b == null) {
            return "";
        }
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

    public static byte[] b(byte[] origin) {
        try {
            return MessageDigest.getInstance("MD5").digest(origin);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static final byte[] b(String filePath) {
        Exception e;
        Throwable th;
        byte[] bArr = null;
        if (!(filePath == null || filePath.length() == 0)) {
            File hFile = new File(filePath);
            if (hFile.exists()) {
                FileInputStream iStream = null;
                try {
                    FileInputStream iStream2 = new FileInputStream(hFile);
                    try {
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        byte[] buffer = new byte[4096];
                        while (true) {
                            int len = iStream2.read(buffer);
                            if (len < 0) {
                                break;
                            }
                            md.update(buffer, 0, len);
                        }
                        bArr = md.digest();
                        if (iStream2 != null) {
                            try {
                                iStream2.close();
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                    } catch (Exception e3) {
                        e2 = e3;
                        iStream = iStream2;
                    } catch (Throwable th2) {
                        th = th2;
                        iStream = iStream2;
                    }
                } catch (Exception e4) {
                    e2 = e4;
                    try {
                        if (c.b) {
                            Log.e(c.a, "md5 exception:" + e2.getMessage());
                        }
                        if (iStream != null) {
                            try {
                                iStream.close();
                            } catch (Exception e22) {
                                e22.printStackTrace();
                            }
                        }
                        return bArr;
                    } catch (Throwable th3) {
                        th = th3;
                        if (iStream != null) {
                            try {
                                iStream.close();
                            } catch (Exception e222) {
                                e222.printStackTrace();
                            }
                        }
                        throw th;
                    }
                }
            } else if (c.b) {
                Log.e(c.a, "md5 file not exist");
            }
        }
        return bArr;
    }
}
