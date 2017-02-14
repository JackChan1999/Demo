package com.meizu.flyme.appcenter.recommend;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import com.meizu.cloud.app.request.RequestManager;
import com.meizu.cloud.app.utils.m;
import com.meizu.e.RequeseParams;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class b {
    private static b d;
    private Context a;
    private long b = 0;
    private boolean c = false;

    public static b a(Context context) {
        if (d == null) {
            d = new b(context);
        }
        return d;
    }

    private b(Context context) {
        this.a = context.getApplicationContext();
    }

    public void a(boolean force) {
        if (b(force)) {
            this.c = true;
            new Thread(new Runnable(this) {
                final /* synthetic */ b a;

                {
                    this.a = r1;
                }

                /* JADX WARNING: inconsistent code. */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /*
                    r14 = this;
                    r13 = 0;
                    r8 = 10;
                    android.os.Process.setThreadPriority(r8);
                    r8 = r14.a;	 Catch:{ Exception -> 0x0086 }
                    r8 = r8.a;	 Catch:{ Exception -> 0x0086 }
                    r8 = r8.getResources();	 Catch:{ Exception -> 0x0086 }
                    r0 = r8.getDisplayMetrics();	 Catch:{ Exception -> 0x0086 }
                    r6 = r0.widthPixels;	 Catch:{ Exception -> 0x0086 }
                    r5 = r0.heightPixels;	 Catch:{ Exception -> 0x0086 }
                    r8 = r14.a;	 Catch:{ Exception -> 0x0086 }
                    r9 = "%dx%d";
                    r10 = 2;
                    r10 = new java.lang.Object[r10];	 Catch:{ Exception -> 0x0086 }
                    r11 = 0;
                    r12 = java.lang.Integer.valueOf(r6);	 Catch:{ Exception -> 0x0086 }
                    r10[r11] = r12;	 Catch:{ Exception -> 0x0086 }
                    r11 = 1;
                    r12 = java.lang.Integer.valueOf(r5);	 Catch:{ Exception -> 0x0086 }
                    r10[r11] = r12;	 Catch:{ Exception -> 0x0086 }
                    r9 = java.lang.String.format(r9, r10);	 Catch:{ Exception -> 0x0086 }
                    r3 = r8.a(r9);	 Catch:{ Exception -> 0x0086 }
                    if (r3 == 0) goto L_0x00c2;
                L_0x0037:
                    r8 = r3.length();	 Catch:{ Exception -> 0x0086 }
                    if (r8 <= 0) goto L_0x00c2;
                L_0x003d:
                    r2 = new org.json.JSONObject;	 Catch:{ Exception -> 0x0086 }
                    r2.<init>(r3);	 Catch:{ Exception -> 0x0086 }
                    r8 = "code";
                    r4 = r2.getInt(r8);	 Catch:{ Exception -> 0x0086 }
                    r8 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
                    if (r4 != r8) goto L_0x007c;
                L_0x004c:
                    r8 = "value";
                    r8 = r2.has(r8);	 Catch:{ Exception -> 0x0086 }
                    if (r8 == 0) goto L_0x006d;
                L_0x0054:
                    r8 = "value";
                    r7 = r2.getJSONObject(r8);	 Catch:{ Exception -> 0x0086 }
                    r8 = "data";
                    r8 = r7.has(r8);	 Catch:{ Exception -> 0x0086 }
                    if (r8 == 0) goto L_0x006d;
                L_0x0062:
                    r8 = r14.a;	 Catch:{ Exception -> 0x0086 }
                    r9 = "data";
                    r9 = r7.getJSONArray(r9);	 Catch:{ Exception -> 0x0086 }
                    r8.a(r9);	 Catch:{ Exception -> 0x0086 }
                L_0x006d:
                    r8 = r14.a;
                    r8.c = r13;
                    r8 = r14.a;
                    r10 = java.lang.System.currentTimeMillis();
                    r8.cancel = r10;
                L_0x007b:
                    return;
                L_0x007c:
                    r8 = -3;
                    if (r4 != r8) goto L_0x0099;
                L_0x007f:
                    r8 = r14.a;	 Catch:{ Exception -> 0x0086 }
                    r9 = 0;
                    r8.a(r9);	 Catch:{ Exception -> 0x0086 }
                    goto L_0x006d;
                L_0x0086:
                    r1 = move-exception;
                    r1.printStackTrace();	 Catch:{ all -> 0x00b2 }
                    r8 = r14.a;
                    r8.c = r13;
                    r8 = r14.a;
                    r10 = java.lang.System.currentTimeMillis();
                    r8.cancel = r10;
                    goto L_0x007b;
                L_0x0099:
                    r8 = "RecommendHelper";
                    r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0086 }
                    r9.<init>();	 Catch:{ Exception -> 0x0086 }
                    r10 = "load recommend return code = ";
                    r9 = r9.append(r10);	 Catch:{ Exception -> 0x0086 }
                    r9 = r9.append(r4);	 Catch:{ Exception -> 0x0086 }
                    r9 = r9.toString();	 Catch:{ Exception -> 0x0086 }
                    android.util.Log.w(r8, r9);	 Catch:{ Exception -> 0x0086 }
                    goto L_0x006d;
                L_0x00b2:
                    r8 = move-exception;
                    r9 = r14.a;
                    r9.c = r13;
                    r9 = r14.a;
                    r10 = java.lang.System.currentTimeMillis();
                    r9.cancel = r10;
                    throw r8;
                L_0x00c2:
                    r8 = "RecommendHelper";
                    r9 = "load recommend return null!";
                    android.util.Log.w(r8, r9);	 Catch:{ Exception -> 0x0086 }
                    goto L_0x006d;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.meizu.flyme.appcenter.recommend.cancel.1.run():void");
                }
            }).start();
        }
    }

    private boolean b(boolean force) {
        if (!this.c && m.b(this.a) && ((force && Math.abs(System.currentTimeMillis() - this.b) >= 120000) || Math.abs(System.currentTimeMillis() - this.b) > 3600000)) {
            long lastTime = b(this.a);
            if (force || Math.abs(System.currentTimeMillis() - lastTime) > 604800000) {
                return true;
            }
        }
        return false;
    }

    private String a(String screenSize) {
        try {
            return RequestManager.getInstance(this.a).requestGet("http://api-app.meizu.com/apps/public/mime/recommend", new RequeseParams[]{new RequeseParams(RequestManager.SCREEN_SIZE, screenSize)});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected String a(String url, String name) {
        try {
            HttpURLConnection urlConn = (HttpURLConnection) new URL(url).openConnection();
            urlConn.setDoInput(true);
            urlConn.connect();
            InputStream is = urlConn.getInputStream();
            File dir = new File(this.a.getCacheDir(), "recommend_icons");
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(dir, name);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            while (true) {
                int n = is.read(buffer);
                if (n > 0) {
                    fileOutputStream.write(buffer, 0, n);
                } else {
                    fileOutputStream.close();
                    is.close();
                    return file.getAbsolutePath();
                }
            }
        } catch (MalformedURLException e) {
            System.out.println("[getNetWorkBitmap->]MalformedURLException");
            e.printStackTrace();
            return "";
        } catch (IOException e2) {
            System.out.println("[getNetWorkBitmap->]IOException");
            e2.printStackTrace();
            return "";
        }
    }

    private void a(JSONArray recommendArray) {
        if (recommendArray != null) {
            try {
                a db = new a(this.a);
                db.b();
                boolean bError = false;
                try {
                    int deleteCound = db.d();
                    if (c.b) {
                        Log.d("RecommendHelper", "delete " + deleteCound);
                    }
                    for (int i = 0; i < recommendArray.length(); i++) {
                        JSONObject recommendObject = recommendArray.getJSONObject(i);
                        String typeArray = recommendObject.getString("docType");
                        String appId = recommendObject.getString("appId");
                        String schemeArray = recommendObject.getString("scheme");
                        String cn = recommendObject.getString("appNameZhCn");
                        String tw = recommendObject.getString("appNameZhTw");
                        String en = recommendObject.getString("appNameEnUs");
                        String icon = recommendObject.getString("appIcon");
                        String url = recommendObject.getString("url");
                        String[] schemes = null;
                        if (schemeArray != null) {
                            schemes = schemeArray.split(",");
                        }
                        String localIcon = a(icon, appId);
                        Log.d(RecommendProvider.class.getSimpleName(), "localIcon=" + localIcon);
                        if (typeArray.contains(",")) {
                            for (String type : typeArray.split(",")) {
                                db.a(type.trim(), appId, schemes, cn, tw, en, icon, url, localIcon);
                            }
                        } else {
                            db.a(typeArray, appId, schemes, cn, tw, en, icon, url, localIcon);
                        }
                    }
                    if (db != null) {
                        db.c();
                    }
                    if (bError) {
                        Log.d("RecommendHelper", "get recommend something is error");
                    } else {
                        Log.d("RecommendHelper", "get recommend success");
                    }
                } catch (Exception e) {
                    bError = true;
                    e.printStackTrace();
                } catch (Throwable th) {
                    if (db != null) {
                        db.c();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            Log.d("RecommendHelper", "get recommend success, no update");
        }
        a(this.a, System.currentTimeMillis());
    }

    public static long b(Context context) {
        SharedPreferences recordPreferences = context.getSharedPreferences("UpdateCheckServiceData", 0);
        if (recordPreferences != null) {
            return recordPreferences.getLong("PreferenceLastUpdateRecommendTime", 0);
        }
        Log.w(c.a, "get SharedPreferences fail!");
        return 0;
    }

    public static void a(Context context, long lTime) {
        SharedPreferences recordPreferences = context.getSharedPreferences("UpdateCheckServiceData", 0);
        if (recordPreferences == null) {
            Log.w(c.a, "get SharedPreferences fail!");
            return;
        }
        Editor editor = recordPreferences.edit();
        editor.putLong("PreferenceLastUpdateRecommendTime", lTime);
        editor.apply();
    }
}
