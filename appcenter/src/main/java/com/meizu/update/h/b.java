package com.meizu.update.h;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class b {
    public static final void a(String msg) {
    }

    public static final void b(String msg) {
        Log.d("MzUpdateComponent:2.0.0", msg);
    }

    public static final void c(String msg) {
        Log.w("MzUpdateComponent:2.0.0", msg);
    }

    public static final void d(String msg) {
        Log.e("MzUpdateComponent:2.0.0", msg);
    }

    public static void a(Context context, String msg) {
        Throwable th;
        try {
            c(msg);
            String dirPathStr = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/" + context.getPackageName() + "/";
            File dirPath = new File(dirPathStr);
            if (!(dirPath.exists() && dirPath.isDirectory())) {
                dirPath.mkdir();
            }
            File file = new File(dirPathStr + "update_component_log");
            boolean append = true;
            if (file.exists()) {
                if (file.length() > 16384) {
                    append = false;
                }
            } else if (!file.createNewFile()) {
                return;
            }
            Calendar CD = Calendar.getInstance();
            int YY = CD.get(1);
            int MM = CD.get(2) + 1;
            int DD = CD.get(5);
            int HH = CD.get(11);
            int NN = CD.get(12);
            int SS = CD.get(13);
            StringBuffer logmsg = new StringBuffer();
            logmsg.append("[");
            logmsg.append(String.format("%04d-%02d-%02d %02d:%02d:%02d", new Object[]{Integer.valueOf(YY), Integer.valueOf(MM), Integer.valueOf(DD), Integer.valueOf(HH), Integer.valueOf(NN), Integer.valueOf(SS)}));
            logmsg.append("]");
            logmsg.append(msg);
            logmsg.append("\n");
            FileOutputStream out = null;
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file, append);
                try {
                    fileOutputStream.write(logmsg.toString().getBytes());
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    out = fileOutputStream;
                    if (out != null) {
                        out.close();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (out != null) {
                    out.close();
                }
                throw th;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void b(final Context context, final String msg) {
        new Thread(new Runnable() {
            public void run() {
                b.a(context, msg);
            }
        }).start();
    }
}
