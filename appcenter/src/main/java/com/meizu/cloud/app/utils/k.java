package com.meizu.cloud.app.utils;

import android.content.Context;
import android.os.Environment;
import android.os.Process;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class k {
    public static final String a = (Environment.getExternalStorageDirectory().getPath() + "/Android/data/");

    private static class a extends Thread {
        private Context a;
        private String b = "";
        private String c = "";
        private String d = "";

        public a(Context context, String logFileName, String tag, String message) {
            this.a = context;
            this.b = logFileName;
            this.c = tag;
            this.d = message;
        }

        public void run() {
            IOException e;
            Throwable th;
            Process.setThreadPriority(10);
            FileOutputStream out = null;
            try {
                String logMsg = String.format("[%s] %s : %s\r\n", new Object[]{a(), this.c, this.d});
                String dirPathStr = k.a + this.a.getPackageName() + "/logs/";
                File dirPath = new File(dirPathStr);
                if (!(dirPath.exists() && dirPath.isDirectory())) {
                    dirPath.mkdirs();
                }
                File saveFile = new File(dirPathStr + this.b);
                if (saveFile.length() > 307200) {
                    a(saveFile);
                }
                if (!saveFile.exists()) {
                    saveFile.createNewFile();
                }
                FileOutputStream out2 = new FileOutputStream(saveFile, true);
                try {
                    out2.write(logMsg.getBytes());
                    if (out2 != null) {
                        try {
                            out2.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                            out = out2;
                            return;
                        }
                    }
                    out = out2;
                } catch (IOException e3) {
                    e2 = e3;
                    out = out2;
                    try {
                        e2.printStackTrace();
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e22) {
                                e22.printStackTrace();
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e222) {
                                e222.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    out = out2;
                    if (out != null) {
                        out.close();
                    }
                    throw th;
                }
            } catch (IOException e4) {
                e222 = e4;
                e222.printStackTrace();
                if (out != null) {
                    out.close();
                }
            }
        }

        private String a() {
            Calendar CD = Calendar.getInstance();
            int YY = CD.get(1);
            int MM = CD.get(2) + 1;
            int DD = CD.get(5);
            int HH = CD.get(11);
            int NN = CD.get(12);
            int SS = CD.get(13);
            return String.format("%04d-%02d-%02d_%02d:%02d:%02d", new Object[]{Integer.valueOf(YY), Integer.valueOf(MM), Integer.valueOf(DD), Integer.valueOf(HH), Integer.valueOf(NN), Integer.valueOf(SS)});
        }

        private void a(File saveFile) {
            saveFile.renameTo(new File(saveFile.getAbsolutePath() + a()));
            int logFileCount = 0;
            File oldestFile = null;
            for (File f : saveFile.getParentFile().listFiles()) {
                if (f.isFile() && f.getName().contains(saveFile.getName())) {
                    logFileCount++;
                    if (oldestFile == null) {
                        oldestFile = f;
                    } else if (f.lastModified() < oldestFile.lastModified()) {
                        oldestFile = f;
                    }
                }
            }
            if (logFileCount >= 3 && oldestFile != null && oldestFile.exists() && oldestFile.isFile()) {
                oldestFile.delete();
            }
        }
    }

    public static void a(Context context, String tag, String msg) {
        j.a(tag, msg);
        new a(context, "installed_log", tag, msg).start();
    }

    public static void b(Context context, String tag, String msg) {
        j.a(tag, msg);
        new a(context, "update_log", tag, msg).start();
    }

    public static void c(Context context, String tag, String msg) {
        j.a(tag, msg);
        new a(context, "activity_log", tag, msg).start();
    }

    public static void d(Context context, String tag, String msg) {
        j.a(tag, msg);
        new a(context, "payment_log", tag, msg).start();
    }

    public static void e(Context context, String tag, String msg) {
        j.a(tag, msg);
        new a(context, "database_log", tag, msg).start();
    }
}
