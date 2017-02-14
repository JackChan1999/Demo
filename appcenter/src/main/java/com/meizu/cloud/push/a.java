package com.meizu.cloud.push;

import android.app.Notification;
import android.app.Notification.BigTextStyle;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.l;
import com.android.volley.n.b;
import com.android.volley.s;
import com.meizu.cloud.app.core.i;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.downlad.d;
import com.meizu.cloud.app.downlad.g;
import com.meizu.cloud.app.request.FastJsonRequest;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.RequestManager;
import com.meizu.cloud.app.request.model.AppStructDetailsItem;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.b.a.e;
import com.meizu.cloud.pushsdk.PushManager;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.update.h.f;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a {
    public static final String[] a = new String[]{"appdownload"};
    public static final String[] b = new String[]{"gamedownload"};
    private static final String c = a.class.getSimpleName();
    private static Object d = new Object();

    public static final String[] a(Context context) {
        if (x.a(context)) {
            return a;
        }
        return b;
    }

    private static boolean b(final Context context, String data) {
        try {
            JSONObject object = new JSONObject(data);
            if (object.has(a(context)[0]) && object.getJSONObject(a(context)[0]).has("id")) {
                JSONObject pushKeyObject = object.getJSONObject(a(context)[0]);
                final String id = pushKeyObject.getString("id");
                final String appname = pushKeyObject.getString("name");
                l jsonRequest = new FastJsonRequest(new TypeReference<ResultModel<AppStructDetailsItem>>() {
                }, 0, RequestConstants.getRuntimeDomainUrl(context, "/public/detail/") + id, null, new b<ResultModel<AppStructDetailsItem>>() {
                    public void a(ResultModel<AppStructDetailsItem> response) {
                        if (response == null || response.getValue() == null) {
                            a.b(context, id, appname);
                            return;
                        }
                        AppStructItem item = (AppStructDetailsItem) response.getValue();
                        if (i.f(context, item.package_name) >= item.version_code) {
                            Log.w(toString(), "app has installed " + id + " " + item.package_name);
                        } else if (item.price <= 0.0d) {
                            item.page_info = new int[]{0, 19, 0};
                            d.a(context).a(null, d.a(context).a(item, new g(2, 1)));
                        } else {
                            Log.w(a.c, "can not start update : " + item.package_name + " is a fee app");
                        }
                    }
                }, new com.android.volley.n.a() {
                    public void a(s error) {
                        a.b(context, id, appname);
                        Log.w(toString(), "get detail error: " + id);
                    }
                });
                jsonRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(context));
                com.meizu.volley.b.a(context).a().a(jsonRequest);
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            com.meizu.update.h.b.c("unknown server push : " + data);
        } catch (Exception e2) {
            e2.printStackTrace();
            com.meizu.update.h.b.c("unknown server push : " + data);
        }
        return false;
    }

    private static void b(Context context, String id, String appname) {
        Notification notification = com.meizu.cloud.app.core.g.a(context, context.getPackageManager().getApplicationIcon(context.getApplicationInfo()), e.mz_stat_sys_download_error, appname, context.getString(com.meizu.cloud.b.a.i.download_error), context.getString(com.meizu.cloud.b.a.i.download_error_formatted, new Object[]{appname}));
        notification.flags = 16;
        Intent intent = null;
        String segment = RequestConstants.APP_DETAIL_PATH_URL;
        if (x.a(context)) {
            intent = new Intent("com.meizu.flyme.appcenter.app.detail");
        } else if (x.b(context)) {
            intent = new Intent("com.meizu.flyme.gamecenter.game.detail");
            segment = RequestConstants.GAME_DETAIL_PATH_URL;
        }
        int key = (int) System.currentTimeMillis();
        String url = segment + id;
        intent.putExtra("app_id", id);
        intent.putExtra("detail_url", url);
        intent.putExtra("perform_internal", false);
        notification.contentIntent = PendingIntent.getActivity(context, key, intent, 134217728);
        try {
            ((NotificationManager) context.getSystemService("notification")).notify(Integer.parseInt(id), notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean c(Context context, String data) {
        if (com.meizu.cloud.app.settings.a.a(context).b()) {
            com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(data);
            if (jsonObject.containsKey("notice")) {
                com.alibaba.fastjson.JSONObject notice = jsonObject.getJSONObject("notice");
                if (notice.containsKey("layout")) {
                    Long id = notice.getLong("id");
                    Context context2 = context;
                    a(context2, id.longValue(), notice.getString("subject"), notice.getString(PushConstants.CONTENT), notice.getString("url"), notice.getString("layout"), notice.getString("icon_url"));
                    return true;
                }
            }
        }
        return false;
    }

    private static void a(Context context, long id, String subject, String content, String url, String layout, String iconUrl) {
        final String str = layout;
        final Context context2 = context;
        final String str2 = url;
        final long j = id;
        final String str3 = subject;
        final String str4 = iconUrl;
        final String str5 = content;
        new Thread(new Runnable() {
            public void run() {
                Process.setThreadPriority(10);
                Intent intent = null;
                if ("APP_DETAIL".equals(str)) {
                    if (x.a(context2)) {
                        intent = new Intent("com.meizu.flyme.appcenter.app.detail");
                    } else if (x.b(context2)) {
                        intent = new Intent("com.meizu.flyme.gamecenter.game.detail");
                    }
                    if (!(intent == null || str2 == null)) {
                        intent.putExtra("detail_url", str2);
                    }
                } else if ("GIFT_DETAIL".equals(str)) {
                    if (i.f(context2, "com.meizu.flyme.gamecenter") >= 19) {
                        intent = new Intent("com.meizu.flyme.gamecenter.gift.detail");
                    }
                } else if ("HTML".equals(str)) {
                    if (x.a(context2)) {
                        intent = new Intent("com.meizu.flyme.appcenter.html");
                    } else if (x.b(context2)) {
                        intent = new Intent("com.meizu.flyme.gamecenter.html");
                    }
                } else if ("SPECIAL_DETAIL".equals(str)) {
                    if (x.a(context2)) {
                        intent = new Intent("com.meizu.flyme.appcenter.app.special");
                    } else if (x.b(context2)) {
                        intent = new Intent("com.meizu.flyme.gamecenter.game.special");
                    }
                } else if (!"ACTIVITY_DETAIL".equals(str)) {
                    return;
                } else {
                    if (x.a(context2)) {
                        intent = new Intent("com.meizu.flyme.appcenter.event");
                    } else if (x.b(context2)) {
                        intent = new Intent("com.meizu.flyme.gamecenter.event");
                    }
                }
                if (intent != null) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("perform_internal", false);
                    bundle.putString("url", str2);
                    bundle.putLong("push_message_id", j);
                    bundle.putString("title_name", str3);
                    intent.putExtras(bundle);
                    Drawable icon = null;
                    if (!TextUtils.isEmpty(str4)) {
                        Bitmap bitmap = h.a(context2, str4);
                        if (bitmap != null) {
                            icon = h.a(bitmap);
                        }
                    }
                    if (icon == null) {
                        icon = context2.getResources().getDrawable(e.ic_status_notification);
                    }
                    Builder builder = new Builder(context2);
                    builder.setLargeIcon(h.a(icon));
                    builder.setSmallIcon(e.mz_stat_sys_appcenter);
                    if (TextUtils.isEmpty(str3)) {
                        builder.setContentTitle(i.b(context2));
                    } else {
                        builder.setContentTitle(str3);
                    }
                    builder.setContentText(str5);
                    builder.setStyle(new BigTextStyle().bigText(str5));
                    Notification notification = builder.build();
                    notification.contentIntent = PendingIntent.getActivity(context2, (int) j, intent, 268435456);
                    notification.flags = 16;
                    ((NotificationManager) context2.getSystemService("notification")).notify((int) j, notification);
                }
            }
        }).start();
    }

    public static boolean a(Context context, String data) {
        if (b(context, data) || c(context, data)) {
            return true;
        }
        return false;
    }

    public static void b(Context context) {
        String pushid = PushManager.getPushId(context);
        if (!TextUtils.isEmpty(pushid) && !c(context)) {
            d(context, pushid);
        }
    }

    public static final void a(Context context, boolean register) {
        Editor e = d(context).edit();
        if (register) {
            e.putBoolean("bRegiste", true);
        } else {
            e.putBoolean("bRegiste", false);
        }
        e.apply();
    }

    public static final boolean c(Context context) {
        return d(context).getBoolean("bRegiste", false);
    }

    public static final SharedPreferences d(Context context) {
        return context.getSharedPreferences("app_push_history", 0);
    }

    private static final void d(final Context context, final String registerId) {
        new Thread(new Runnable() {
            public void run() {
                synchronized (a.d) {
                    if (!a.c(context)) {
                        if (a.b(context, registerId, a.a(context), true)) {
                            a.a(context, true);
                        } else {
                            a.a(context, false);
                        }
                    }
                }
            }
        }).start();
    }

    private static boolean b(Context context, String pushid, String[] types, boolean enable) {
        try {
            String imei = com.meizu.update.h.g.e(context);
            String model = com.meizu.update.h.g.d(context);
            String sn = com.meizu.update.h.g.f(context);
            String androidVersion = com.meizu.update.h.g.c(context);
            String flymeVersion = com.meizu.update.h.g.b(context);
            String packageName = context.getPackageName();
            String appVersion = com.meizu.update.h.g.a(context);
            JSONArray appArray = new JSONArray();
            int status = enable ? 1 : 0;
            for (String type : types) {
                JSONObject appObject = new JSONObject();
                appObject.put("type", type);
                appObject.put("subStatus", status);
                appArray.put(appObject);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("deviceType", model);
            jsonObject.put(RequestManager.FIRMWARE, androidVersion);
            jsonObject.put("sysVer", flymeVersion);
            jsonObject.put(RequestManager.IMEI, imei);
            jsonObject.put(RequestManager.SN, sn);
            jsonObject.put("serviceToken", pushid);
            jsonObject.put("serviceName", packageName);
            jsonObject.put("version", appVersion);
            jsonObject.put("services", appArray);
            jsonObject.put("services", appArray);
            String subStr = jsonObject.toString();
            StringBuffer buf = new StringBuffer();
            buf.append(subStr).append("2635881a7ab0593849fe89e685fc56cd");
            String sign = com.meizu.update.h.g.b(buf.toString());
            List<Pair<String, String>> params = new ArrayList();
            params.add(new Pair("subservices", subStr));
            params.add(new Pair("sign", sign));
            String result = f.a("http://u.meizu.com/subscription/registerTypeWithSign", params);
            if (result != null) {
                JSONObject responseJson = new JSONObject(result).getJSONObject("reply");
                if (responseJson.getInt("code") == 200) {
                    JSONObject typesOjtect = responseJson.getJSONObject("value");
                    for (String typeKey : types) {
                        int switcher = typesOjtect.getInt(typeKey);
                        if (switcher != status) {
                            Log.w(c, "register fail while switch not match : " + status + " -> " + switcher);
                            return false;
                        }
                    }
                    com.meizu.update.h.b.b("register type push success");
                    return true;
                }
                com.meizu.update.h.b.d("register type push failed: " + result);
            } else {
                com.meizu.update.h.b.d("register type push response null");
            }
        } catch (JSONException ignore) {
            ignore.printStackTrace();
        }
        return false;
    }
}
