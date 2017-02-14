package com.meizu.cloud.app.core;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.webkit.JavascriptInterface;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.l;
import com.android.volley.n;
import com.android.volley.n.b;
import com.meizu.cloud.app.downlad.d;
import com.meizu.cloud.app.downlad.e;
import com.meizu.cloud.app.downlad.f.a;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.ActivityWebviewInfo;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.cloud.b.a.c;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.b.a.i;
import java.util.ArrayList;
import java.util.List;

public class EventJavascriptInterface extends e {
    private final String a = "javascript:notifyDownProgress('%s','%s','%s','%s',%cancel,%cancel)";
    private final String b = "javascript:hasLogin(%cancel)";
    private final String c = "javascript:onLotteryStart()";
    private final String d = "javascript:onLotteryStop('%s')";
    private final String e = "javascript:Activity.notifyLotteryTimes()";
    private final String f = "javascript:onWindowHide()";
    private final String g = "javascript:onPaySucess(%d,'%s')";
    private final String h = "javascript:onPayError(%d,'%s',%d,'%s')";
    private final String i = "javascript:onTokenSuccess('%s',%cancel)";
    private final String j = "javascript:onTokenError('%s','%s')";
    private final String k = "javascript:onOauthResponse('%s','%s')";
    private final String l = "javascript:onOauthError('%s','%s')";
    private OnJSCallback m;

    public interface OnJSCallback {
        @JavascriptInterface
        String getClipContent();

        @JavascriptInterface
        String getIMEI();

        @JavascriptInterface
        String getPhoneNumber();

        @JavascriptInterface
        String getUserId();

        @JavascriptInterface
        int getVersionCode(int i, String str);

        @JavascriptInterface
        void gotoAppInfoPage(String str);

        @JavascriptInterface
        void installAppById(int i);

        @JavascriptInterface
        boolean isAppInstalled(String str);

        @JavascriptInterface
        boolean launchApp(String str);

        @JavascriptInterface
        void login();

        @JavascriptInterface
        void lottery(String[] strArr);

        @JavascriptInterface
        boolean oauthRequest(String str, String str2, String str3);

        @JavascriptInterface
        void onAppShowInPage(String[] strArr);

        @JavascriptInterface
        void onInstallButtonClick(int i, String str);

        @JavascriptInterface
        void payApp(boolean z, int i, double d, String str, int i2);

        @JavascriptInterface
        void requestChance(String str);

        @JavascriptInterface
        void requestLoginStatus();

        @JavascriptInterface
        boolean setClipContent(String str);

        @JavascriptInterface
        void share(String str, String str2, String[] strArr, long j, String str3, String str4);

        @JavascriptInterface
        boolean uninstallApp(String str);

        @JavascriptInterface
        boolean updateApp(int i, String str);
    }

    public EventJavascriptInterface(OnJSCallback callback) {
        this.m = callback;
    }

    public String a() {
        return getClass().getSimpleName();
    }

    public Object b() {
        return this.m;
    }

    public String a(Context mActivity, String pkgName, ActivityWebviewInfo activityInfo, t viewController) {
        String js = "";
        boolean bFound = false;
        for (e item : d.a(mActivity).e()) {
            if (item.g().equals(pkgName)) {
                bFound = true;
                js = a(mActivity, item, activityInfo, viewController);
                break;
            }
        }
        if (bFound) {
            return js;
        }
        for (Pair<String, Integer> localAppInfo : x.d(mActivity).a()) {
            if (((String) localAppInfo.first).equals(pkgName)) {
                bFound = true;
                break;
            }
        }
        String text = bFound ? mActivity.getString(i.installed) : mActivity.getString(i.install);
        int textColor = bFound ? a(activityInfo) : mActivity.getResources().getColor(c.white);
        int bgColor = bFound ? mActivity.getResources().getColor(c.transparent) : a(mActivity, activityInfo);
        String str = "javascript:notifyDownProgress('%s','%s','%s','%s',%cancel,%cancel)";
        Object[] objArr = new Object[6];
        objArr[0] = pkgName;
        objArr[1] = text;
        objArr[2] = a(textColor);
        objArr[3] = a(bgColor);
        objArr[4] = Boolean.valueOf(!bFound);
        objArr[5] = Boolean.valueOf(bFound);
        return String.format(str, objArr);
    }

    public String a(Context context, e downloadWrapper, ActivityWebviewInfo activityInfo, t viewController) {
        Drawable drawable;
        int bgColor;
        boolean z;
        boolean z2;
        CirProButton btn = (CirProButton) LayoutInflater.from(context).inflate(g.install_btn_layout, null).findViewById(f.btnInstall);
        v viewDisplay = viewController.a(downloadWrapper, btn);
        int textColor = btn.b() ? btn.getTextView().getCurrentTextColor() : btn.getButton().getCurrentTextColor();
        String text = viewDisplay.b();
        if (btn.b()) {
            drawable = null;
        } else {
            drawable = btn.getButton().getBackground();
        }
        if ((downloadWrapper.f() instanceof com.meizu.cloud.app.downlad.f.c) && downloadWrapper.f() == com.meizu.cloud.app.downlad.f.c.TASK_STARTED) {
            drawable = null;
            if (TextUtils.isEmpty(text)) {
                text = "" + downloadWrapper.o() + "%";
            }
        }
        if (drawable == null) {
            bgColor = context.getResources().getColor(c.theme_color);
        } else {
            bgColor = a(context, activityInfo);
        }
        String g = downloadWrapper.g();
        if (btn.b()) {
            z = false;
        } else {
            z = true;
        }
        if (viewDisplay.c() == a.INSTALLED || viewDisplay.c() == com.meizu.cloud.app.downlad.f.f.INSTALL_SUCCESS) {
            z2 = true;
        } else {
            z2 = false;
        }
        return a(g, text, textColor, bgColor, z, z2);
    }

    public String a(String pkgName, String text, int textColor, int bgColor, boolean enable, boolean isInstalled) {
        return String.format("javascript:notifyDownProgress('%s','%s','%s','%s',%cancel,%cancel)", new Object[]{pkgName, text, a(textColor), a(bgColor), Boolean.valueOf(enable), Boolean.valueOf(isInstalled)});
    }

    private int a(ActivityWebviewInfo activityInfo) {
        int installedTextColor = -16777216;
        if (activityInfo != null) {
            try {
                if (activityInfo.btn_selected_color != null) {
                    installedTextColor = Color.parseColor(activityInfo.btn_selected_color);
                }
            } catch (Exception e) {
            }
        }
        return installedTextColor;
    }

    private int a(Context context, ActivityWebviewInfo activityInfo) {
        int installBgColor = context.getResources().getColor(c.theme_color);
        if (activityInfo != null) {
            try {
                if (activityInfo.btn_color != null) {
                    installBgColor = Color.parseColor(activityInfo.btn_color);
                }
            } catch (Exception e) {
            }
        }
        return installBgColor;
    }

    private String a(int color) {
        return String.format("rgba(%d,%d,%d,%.1f)", new Object[]{Integer.valueOf(Color.red(color)), Integer.valueOf(Color.green(color)), Integer.valueOf(Color.blue(color)), Float.valueOf(((float) Color.alpha(color)) / 255.0f)});
    }

    public String a(boolean hasLogin) {
        return String.format("javascript:hasLogin(%cancel)", new Object[]{Boolean.valueOf(hasLogin)});
    }

    public String c() {
        return "javascript:onLotteryStart()";
    }

    public String a(String message) {
        return String.format("javascript:onLotteryStop('%s')", new Object[]{message});
    }

    public String d() {
        return "javascript:Activity.notifyLotteryTimes()";
    }

    public String e() {
        return "javascript:onWindowHide()";
    }

    public String a(int appId, String pkgName) {
        return String.format("javascript:onPaySucess(%d,'%s')", new Object[]{Integer.valueOf(appId), pkgName});
    }

    public String a(int appId, String pkgName, int errorCode, String errorStr) {
        return String.format("javascript:onPayError(%d,'%s',%d,'%s')", new Object[]{Integer.valueOf(appId), pkgName, Integer.valueOf(errorCode), errorStr});
    }

    public String a(String tag, boolean isFromLogin) {
        return String.format("javascript:onTokenSuccess('%s',%cancel)", new Object[]{tag, Boolean.valueOf(isFromLogin)});
    }

    public String a(String tag, int errorCode) {
        return String.format("javascript:onTokenError('%s','%s')", new Object[]{tag, Integer.valueOf(errorCode)});
    }

    public String a(String tag, String resultJson) {
        return String.format("javascript:onOauthResponse('%s','%s')", new Object[]{tag, resultJson});
    }

    public String b(String tag, String errorMsg) {
        return String.format("javascript:onOauthError('%s','%s')", new Object[]{tag, errorMsg});
    }

    public void a(Context context, String activityId, String[] zippoIdList, b<String> resultListener, n.a errorListener) {
        TypeReference<String> typeReference = new TypeReference<String>(this) {
            final /* synthetic */ EventJavascriptInterface a;

            {
                this.a = r1;
            }
        };
        String url = String.format(RequestConstants.getRuntimeDomainUrl(context, RequestConstants.LOTTERY_REQUEST), new Object[]{activityId});
        List<com.meizu.volley.b.a> paramPairs = new ArrayList();
        String zippoIds = "";
        for (String t : zippoIdList) {
            zippoIds = zippoIds + "," + t;
        }
        paramPairs.add(new com.meizu.volley.b.a("zippo_ids", zippoIds.replaceFirst(",", "")));
        l jsonAuthRequest = new com.meizu.volley.a.c(context, typeReference, url, paramPairs, resultListener, errorListener);
        jsonAuthRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(context));
        com.meizu.volley.b.a(context).a().a(jsonAuthRequest);
    }

    public void a(Context context, String activityId, List<String> taskIdList, b<ResultModel<JSONObject>> resultListener, n.a errorListener) {
        TypeReference<ResultModel<JSONObject>> typeReference = new TypeReference<ResultModel<JSONObject>>(this) {
            final /* synthetic */ EventJavascriptInterface a;

            {
                this.a = r1;
            }
        };
        String url = String.format(RequestConstants.getRuntimeDomainUrl(context, RequestConstants.LOTTERY_REPORT), new Object[]{activityId});
        List<com.meizu.volley.b.a> paramPairs = new ArrayList();
        String taskIds = "";
        for (String t : taskIdList) {
            taskIds = taskIds + "," + t;
        }
        paramPairs.add(new com.meizu.volley.b.a("task_ids", taskIds.replaceFirst(",", "")));
        l jsonAuthRequest = new com.meizu.volley.a.b(context, typeReference, url, paramPairs, resultListener, errorListener);
        jsonAuthRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(context));
        com.meizu.volley.b.a(context).a().a(jsonAuthRequest);
    }

    public boolean a(Context context, String businessUrl, String paramsJsonObject, b<String> resultListener, n.a errorListener) {
        TypeReference<String> typeReference = new TypeReference<String>(this) {
            final /* synthetic */ EventJavascriptInterface a;

            {
                this.a = r1;
            }
        };
        String url = RequestConstants.getRuntimeDomainUrl(context, businessUrl);
        List<com.meizu.volley.b.a> paramPairs = new ArrayList();
        JSONObject jsonObject = JSON.parseObject(paramsJsonObject);
        if (jsonObject == null) {
            return false;
        }
        for (String key : jsonObject.keySet()) {
            paramPairs.add(new com.meizu.volley.b.a(key, String.valueOf(jsonObject.get(key))));
        }
        l jsonAuthRequest = new com.meizu.volley.a.c(context, typeReference, url, paramPairs, resultListener, errorListener);
        jsonAuthRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(context));
        com.meizu.volley.b.a(context).a().a(jsonAuthRequest);
        return true;
    }
}
