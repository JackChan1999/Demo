package com.meizu.cloud.download.c;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.Collator;
import java.text.DecimalFormat;

public class h {
    public static boolean a = true;
    public static boolean b = false;
    private static final Method c = c();
    private static final String[] d = new String[]{"_data"};
    private static float e = 1.0f;
    private static int f = -1;
    private static final DecimalFormat g = new DecimalFormat("#0.0");
    private static final DecimalFormat h = new DecimalFormat("#0.00");
    private static Collator i = null;
    private static boolean j = true;
    private static String k = null;

    private static Method c() {
        try {
            return Environment.class.getMethod("getExternalStorageDirectoryMzInternal", new Class[0]);
        } catch (Exception e) {
            return null;
        }
    }

    public static void a(boolean cond) {
        if (!cond) {
            throw new AssertionError();
        }
    }

    public static void a(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (Throwable t) {
                Log.w("Utils", "close fail", t);
            }
        }
    }

    public static void a(String format, Object... args) {
        if (a) {
            String str = String.format(format, args);
            if (b) {
                a(str);
            }
            Log.d("Utils", str);
        }
    }

    @SuppressLint({"SdCardPath"})
    public static void a(String log) {
        Exception e;
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/");
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            File file2 = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/network.txt");
            try {
                OutputStream out = new FileOutputStream(file2, true);
                out.write(log.getBytes());
                out.write("\r\n".getBytes());
                out.close();
                if (file2.length() > 1048576) {
                    file2.delete();
                }
                file = file2;
            } catch (Exception e2) {
                e = e2;
                file = file2;
                e.printStackTrace();
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
        }
    }

    public static long a() {
        if (!Environment.getExternalStorageState().equals("mounted")) {
            return 0;
        }
        StatFs sf = new StatFs(b().getAbsolutePath());
        return ((long) sf.getBlockSize()) * ((long) sf.getAvailableBlocks());
    }

    public static long b(String path) {
        try {
            File file = new File(path);
            if (file.exists() && file.isDirectory()) {
                return file.getFreeSpace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean c(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static File b() {
        if (c == null) {
            return Environment.getExternalStorageDirectory();
        }
        try {
            return (File) c.invoke(null, new Object[0]);
        } catch (Exception e) {
            return Environment.getExternalStorageDirectory();
        }
    }
}
