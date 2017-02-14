package com.meizu.update;

import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;
import com.meizu.cloud.app.request.RequestManager;
import com.meizu.update.h.a;
import com.meizu.update.h.b;
import com.meizu.update.h.f;
import com.meizu.update.h.g;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c {
    public static final UpdateInfo a(Context context) {
        return a(context, context.getPackageName());
    }

    public static final UpdateInfo a(Context context, String packageName) {
        try {
            return b(context, packageName);
        } catch (a e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final UpdateInfo b(Context context, String packageName) throws a {
        try {
            String imei = g.e(context);
            String model = g.d(context);
            String sn = g.f(context);
            String androidVersion = g.c(context);
            String flymeVersion = g.b(context);
            String appVersion = g.a(context, packageName);
            JSONObject serviceObj = new JSONObject();
            serviceObj.put("serviceName", packageName);
            serviceObj.put("version", appVersion);
            JSONArray serviceArray = new JSONArray();
            serviceArray.put(serviceObj);
            JSONObject object = new JSONObject();
            object.put("deviceType", model);
            object.put(RequestManager.FIRMWARE, androidVersion);
            object.put("sysVer", flymeVersion);
            object.put(RequestManager.IMEI, imei);
            object.put(RequestManager.SN, sn);
            object.put("services", serviceArray);
            String json = object.toString();
            StringBuffer buf = new StringBuffer();
            buf.append(json).append("2635881a7ab0593849fe89e685fc56cd");
            String res = b(json, g.b(buf.toString()));
            if (TextUtils.isEmpty(res)) {
                b.c("check update response null.");
                throw new a("Check update response null.");
            }
            UpdateInfo info = a(res, packageName);
            if (info != null) {
                if (info.mNeedUpdate || info.mExistsUpdate) {
                    b.b("new version : " + info.mVersionName);
                    if (packageName.equalsIgnoreCase(context.getPackageName())) {
                        com.meizu.update.a.b.a(context, res);
                    }
                } else {
                    b.b("no update");
                }
                return info;
            }
            b.c("check update parse failed.");
            throw new a("Cant parse server response:" + res);
        } catch (a e) {
            throw e;
        } catch (Exception ignore) {
            ignore.printStackTrace();
            throw new a(ignore.getMessage());
        }
    }

    private static String b(String json, String sign) throws a {
        List<Pair<String, String>> params = new ArrayList();
        params.add(new Pair("apps", json));
        params.add(new Pair("sign", sign));
        return f.b("http://u.meizu.com/appupgrade/check", params);
    }

    public static UpdateInfo a(String res, String targetPackage) throws JSONException {
        JSONObject object = new JSONObject(res).getJSONObject("reply");
        int code = object.getInt("code");
        if (code == 200) {
            b.a("updateinfo: " + object.toString());
            JSONArray array = object.getJSONArray("value");
            int size = array.length();
            if (size == 1) {
                JSONObject updateObject = array.getJSONObject(0);
                String packageName = updateObject.getString("serviceName");
                if (targetPackage.equals(packageName)) {
                    UpdateInfo info = new UpdateInfo();
                    info.mExistsUpdate = updateObject.getBoolean("existsUpdate");
                    info.mNeedUpdate = updateObject.getBoolean("needUpdate");
                    if (!info.mExistsUpdate && !info.mNeedUpdate) {
                        return info;
                    }
                    info.mUpdateUrl = updateObject.getString("updateUrl");
                    info.mSize = updateObject.getString("fileSize");
                    info.mVersionDate = updateObject.getString("releaseDate");
                    info.mVersionDesc = updateObject.getString("releaseNote");
                    info.mVersionName = updateObject.getString("latestVersion");
                    if (updateObject.has("digest")) {
                        info.mDigest = updateObject.getString("digest");
                    }
                    if (updateObject.has("verifyMode")) {
                        info.mVerifyMode = updateObject.getInt("verifyMode");
                    }
                    if (updateObject.has("size")) {
                        info.mSizeByte = updateObject.getLong("size");
                    }
                    if (updateObject.has("updateUrl2")) {
                        info.mUpdateUrl2 = updateObject.getString("updateUrl2");
                    }
                    if (g.c() && !TextUtils.isEmpty(info.mVersionName)) {
                        String internationTrail = "_i";
                        if (info.mVersionName.endsWith("_i")) {
                            info.mVersionName = info.mVersionName.substring(0, info.mVersionName.length() - "_i".length());
                        }
                    }
                    if (!updateObject.has("noteNetwork")) {
                        return info;
                    }
                    info.mNoteNetWork = updateObject.getBoolean("noteNetwork");
                    return info;
                }
                b.d("server return package : " + packageName);
            } else {
                b.d("server return size : " + size);
            }
        } else {
            b.c("unknown server code : " + code);
        }
        return null;
    }

    public static boolean c(Context context, String registerId) {
        try {
            b.a(context, "start register push to server");
            String imei = g.e(context);
            String model = g.d(context);
            String sn = g.f(context);
            String androidVersion = g.c(context);
            String flymeVersion = g.b(context);
            String packageName = context.getPackageName();
            String appVersion = g.a(context);
            JSONObject appObject = new JSONObject();
            appObject.put("serviceName", packageName);
            appObject.put("subStatus", 1);
            appObject.put("version", appVersion);
            appObject.put("serviceToken", registerId);
            JSONArray appArray = new JSONArray();
            appArray.put(appObject);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("deviceType", model);
            jsonObject.put(RequestManager.FIRMWARE, androidVersion);
            jsonObject.put("sysVer", flymeVersion);
            jsonObject.put(RequestManager.IMEI, imei);
            jsonObject.put(RequestManager.SN, sn);
            jsonObject.put("services", appArray);
            String subserviceString = jsonObject.toString();
            StringBuffer buf = new StringBuffer();
            buf.append(subserviceString).append("2635881a7ab0593849fe89e685fc56cd");
            return a(context, subserviceString, g.b(buf.toString()));
        } catch (Exception ignore) {
            b.a(context, "register push to server exception:" + ignore.getMessage());
            ignore.printStackTrace();
            return false;
        }
    }

    private static boolean a(Context context, String subserviceString, String sign) throws JSONException {
        List<Pair<String, String>> params = new ArrayList();
        params.add(new Pair("subservices", subserviceString));
        params.add(new Pair("sign", sign));
        String result = f.a("http://u.meizu.com/subscription/registerWithSign", params);
        if (result == null) {
            b.a(context, "register push response null");
        } else if (new JSONObject(result).getJSONObject("reply").getInt("code") == 200) {
            b.a(context, "register push success");
            return true;
        } else {
            b.a(context, "register push failed: " + result);
        }
        return false;
    }
}
