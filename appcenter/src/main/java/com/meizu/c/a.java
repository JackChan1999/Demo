package com.meizu.c;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Process;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

public class a implements UncaughtExceptionHandler {
    private static a g;
    private String a = "CrashHandler";
    private String b;
    private String c = "CrashExceptionLog.txt";
    private String d = null;
    private boolean e = false;
    private UncaughtExceptionHandler f;
    private Context h;

    private a() {
    }

    public static a a() {
        if (g == null) {
            g = new a();
        }
        return g;
    }

    public void a(Context ctx) {
        a(ctx, null, null);
    }

    public void a(Context ctx, String logPath, String serverUrl) {
        if (TextUtils.isEmpty(logPath)) {
            this.b = ctx.getFilesDir().getAbsolutePath();
        } else {
            this.b = logPath;
        }
        if (!this.b.endsWith(File.separator)) {
            this.b += File.separator;
        }
        this.d = serverUrl;
        this.h = ctx;
        this.f = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        new Thread(new Runnable(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.b();
            }
        }).start();
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        if (!a(ex)) {
            Log.e(this.a, "handleException not success");
        }
        if (this.f != null) {
            this.f.uncaughtException(thread, ex);
            return;
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Log.e(this.a, "Error : ", e);
        }
        Process.killProcess(Process.myPid());
        System.exit(10);
    }

    private boolean a(Throwable ex) {
        if (ex != null) {
            c(ex);
            b(ex);
        }
        return true;
    }

    private void b(Throwable ex) {
        if (this.d == null) {
        }
    }

    private String c(Throwable ex) {
        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        ex.printStackTrace(printWriter);
        for (Throwable cause = ex.getCause(); cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
        }
        String logString = info.toString();
        printWriter.close();
        int verCode = -1;
        try {
            PackageInfo pi = this.h.getPackageManager().getPackageInfo(this.h.getPackageName(), 1);
            if (pi != null) {
                verCode = pi.versionCode;
            }
        } catch (NameNotFoundException e1) {
            e1.printStackTrace();
        }
        try {
            long timeStamp = System.currentTimeMillis();
            File pathFile = new File(this.b);
            if (pathFile.exists() || pathFile.mkdirs()) {
                if (!pathFile.isDirectory()) {
                    pathFile.delete();
                    pathFile.mkdirs();
                }
                File logFile = new File(this.b + this.c);
                if (logFile.exists() || logFile.createNewFile()) {
                    a(logFile, verCode, timeStamp, logString);
                } else {
                    a(verCode, timeStamp, logString);
                }
                return this.b + this.c;
            }
            a(verCode, timeStamp, logString);
            return null;
        } catch (Exception e) {
            Log.e(this.a, "an error occured while writing report file...", e);
            return null;
        }
    }

    private void a(int verCode, long timeStamp, String logString) {
        if (this.e) {
            SharedPreferences preference = this.h.getSharedPreferences("CrashExceptionPreference", 0);
            if (preference != null) {
                String strResult = b(verCode, timeStamp, logString);
                Editor editor = preference.edit();
                editor.putString("KeyCrashException", preference.getString("KeyCrashException", "") + strResult);
                editor.apply();
            }
        }
    }

    private void b() {
        if (this.e) {
            try {
                SharedPreferences preference = this.h.getSharedPreferences("CrashExceptionPreference", 0);
                if (preference != null) {
                    String strResult = preference.getString("KeyCrashException", null);
                    if (strResult != null && strResult.length() > 0) {
                        File pathFile = new File(this.b);
                        if (pathFile.exists() || pathFile.mkdirs()) {
                            if (!pathFile.isDirectory()) {
                                pathFile.delete();
                                pathFile.mkdirs();
                            }
                            File logFile = new File(this.b + this.c);
                            if (logFile.exists() || logFile.createNewFile()) {
                                a(logFile, strResult);
                                Editor editor = preference.edit();
                                editor.remove("KeyCrashException");
                                editor.apply();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void a(File logFile, int verCode, long timeStamp, String logString) {
        boolean bAppend = true;
        try {
            if (logFile.length() > 1048576) {
                bAppend = false;
            }
            FileOutputStream out = new FileOutputStream(logFile, bAppend);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            writer.write(b(verCode, timeStamp, logString));
            writer.flush();
            out.flush();
            writer.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String b(int verCode, long timeStamp, String logString) {
        StringBuilder builder = new StringBuilder();
        builder.append("----------------------------------------\n");
        builder.append("Time: " + DateFormat.format("yyyy-MM-dd kk:mm", timeStamp) + "\n");
        builder.append("verCode: " + verCode + "\n");
        builder.append(logString);
        builder.append("\n\n");
        return builder.toString();
    }

    private void a(File logFile, String log) {
        boolean bAppend = true;
        try {
            if (logFile.length() > 1048576) {
                bAppend = false;
            }
            FileOutputStream out = new FileOutputStream(logFile, bAppend);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            writer.write(log);
            writer.flush();
            out.flush();
            writer.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
