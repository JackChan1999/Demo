package com.meizu.gslb.g;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.meizu.gslb.b.d;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class a {
    private static String a;

    public static final void a(String str) {
        Log.w("MzGslbSdk", str);
        e(str);
    }

    public static final void b(String str) {
        Log.d("MzGslbSdk", str);
        e(str);
    }

    public static final void c(String str) {
        Log.w("MzGslbSdk", str);
        e(str);
    }

    public static final void d(String str) {
        Log.e("MzGslbSdk", str);
        e(str);
    }

    public static void e(String str) {
        Throwable th;
        boolean z = true;
        try {
            if (TextUtils.isEmpty(a)) {
                try {
                    a = d.a().c();
                } catch (IllegalArgumentException e) {
                }
            }
            if (TextUtils.isEmpty(a)) {
                Log.e("MzGslbSdk", "cant get package name while write file log!");
                return;
            }
            String str2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/" + a + "/";
            File file = new File(str2);
            if (!(file.exists() && file.isDirectory())) {
                file.mkdir();
            }
            file = new File(str2 + "gslb_sdk_log");
            if (file.exists()) {
                if (file.length() > 131072) {
                    z = false;
                }
            } else if (!file.createNewFile()) {
                return;
            }
            Calendar instance = Calendar.getInstance();
            int i = instance.get(1);
            int i2 = instance.get(2) + 1;
            int i3 = instance.get(5);
            int i4 = instance.get(11);
            int i5 = instance.get(12);
            int i6 = instance.get(13);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            stringBuilder.append(String.format("%04d-%02d-%02d %02d:%02d:%02d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6)}));
            stringBuilder.append("]");
            stringBuilder.append(str);
            stringBuilder.append("\n");
            FileOutputStream fileOutputStream;
            try {
                fileOutputStream = new FileOutputStream(file, z);
                try {
                    fileOutputStream.write(stringBuilder.toString().getBytes());
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }
}
