package com.meizu.cloud.app.fragment;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.l;
import com.android.volley.n;
import com.android.volley.n.a;
import com.android.volley.s;
import com.meizu.cloud.app.core.EventJavascriptInterface;
import com.meizu.cloud.app.core.EventJavascriptInterface.OnJSCallback;
import com.meizu.cloud.app.core.EventTaskInfo;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.downlad.f.b;
import com.meizu.cloud.app.downlad.f.d;
import com.meizu.cloud.app.downlad.f.e;
import com.meizu.cloud.app.downlad.f.f;
import com.meizu.cloud.app.downlad.f.m;
import com.meizu.cloud.app.downlad.g;
import com.meizu.cloud.app.request.FastJsonRequest;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.ActivityWebviewInfo;
import com.meizu.cloud.app.request.model.AppStructDetailsItem;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.app.utils.j;
import com.meizu.cloud.app.utils.k;
import com.meizu.cloud.app.utils.r;
import com.meizu.cloud.app.utils.t;
import com.meizu.cloud.app.utils.u;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.base.app.BaseCommonActivity;
import com.meizu.cloud.statistics.UxipPageSourceInfo;
import com.meizu.cloud.statistics.c;
import com.tencent.mm.sdk.constants.ConstantsAPI.WXApp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX.Req;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class BaseEventWebviewFragment extends u implements OnJSCallback, b, d, e, BaseCommonActivity.d {
    protected static final int ACCOUNT_LOGIC = 1;
    private static final String PRD_APPCENTER_APPID = "wxd0111b398f7aa904";
    private static final String PRD_GAMECENTER_APPID = "wxc36ef30a59720a5c";
    private static final String USER_APPCENTER_APPID = "wxab45df58aeffe0be";
    private static final String USER_GAMECENTER_APPID = "wxe1273feffb06462a";
    protected final String TAG = BaseEventWebviewFragment.class.getSimpleName();
    public Drawable actionbar_bg;
    public int actionbar_title_color;
    public IWXAPI mAPI;
    protected ActivityWebviewInfo mActivityInfo;
    protected FastJsonRequest<AppStructDetailsItem> mDetailRequest;
    protected EventJavascriptInterface mEventJsInterface;
    protected List<EventTaskInfo> mEventTaskInfos;
    protected boolean mHasLotteryRequest;
    private boolean mHasStoreTheme = false;
    protected com.meizu.cloud.a.b mLoginAuthHelper;
    protected String mPageName;
    private int mTokenFailCount = 0;
    private UxipPageSourceInfo mUxipPageSourceInfo;
    private String mUxipSourcePage;
    public Drawable window_bg;

    public abstract String getRequestUrl();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.meizu.cloud.app.downlad.d.a(this.mActivity).a((m) this, new g());
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.mPageName = "Activity_" + bundle.getString("title_name", "");
        }
        this.mPageInfo[1] = 15;
        this.mPageInfo[2] = c.f(getArguments().getString("url", ""));
        getDefaultThemeConfig();
        registerWxApiBeforeShare();
        if (getArguments().containsKey("uxip_page_source_info")) {
            this.mUxipPageSourceInfo = (UxipPageSourceInfo) getArguments().getParcelable("uxip_page_source_info");
        } else if (getArguments().containsKey("source_page")) {
            this.mUxipSourcePage = getArguments().getString("source_page", "");
        }
    }

    public void onDestroy() {
        if (this.mLoginAuthHelper != null) {
            this.mLoginAuthHelper.a();
        }
        if (this.mDetailRequest != null) {
            this.mDetailRequest.cancel();
        }
        com.meizu.cloud.app.downlad.d.a(this.mActivity).b((m) this);
        super.onDestroy();
        this.mAPI.unregisterApp();
    }

    protected void setupActionBar() {
        super.setupActionBar();
        Bundle bundle = getArguments();
        if (bundle != null) {
            CharSequence title = bundle.getString("title_name");
            if (!TextUtils.isEmpty(title)) {
                getActionBar().a(title);
            }
        }
        if (this.mActivityInfo != null) {
            applyThemeColor(this.mActivityInfo);
        }
    }

    public void onDestroyView() {
        if (this.mHasStoreTheme) {
            resumeTheme();
        }
        super.onDestroyView();
    }

    public void onPause() {
        super.onPause();
        if (getWebView() != null) {
            loadJavaScript(this.mEventJsInterface.e());
        }
        ((BaseCommonActivity) getActivity()).k();
    }

    public void onResume() {
        super.onResume();
        ((BaseCommonActivity) getActivity()).a((BaseCommonActivity.d) this);
    }

    protected void resumeTheme() {
        u.a(getActivity(), false);
        if (this.window_bg != null) {
            getActivity().getWindow().setBackgroundDrawable(this.window_bg);
        }
        u.a(getActivity(), this.actionbar_title_color);
        Drawable icon = u.b(getActivity());
        if (icon != null) {
            icon.setColorFilter(this.actionbar_title_color, Mode.MULTIPLY);
        }
        ActionBar actionBar = getActionBar();
        if (!(actionBar == null || this.actionbar_bg == null)) {
            actionBar.a(this.actionbar_bg);
        }
        getActivity().invalidateOptionsMenu();
    }

    protected void applyThemeColor(ActivityWebviewInfo webviewInfo) {
        if (getActivity() != null && webviewInfo != null) {
            try {
                if (!TextUtils.isEmpty(webviewInfo.back_color)) {
                    getActionBar().a(new ColorDrawable(Color.parseColor(webviewInfo.back_color)));
                }
                if (!TextUtils.isEmpty(webviewInfo.title_color)) {
                    int titleColor = Color.parseColor(webviewInfo.title_color);
                    u.a(getActivity(), titleColor);
                    u.b(getActivity()).setColorFilter(titleColor, Mode.MULTIPLY);
                }
                if (!TextUtils.isEmpty(webviewInfo.statusicon_color)) {
                    int statusicon_color = Color.parseColor(webviewInfo.statusicon_color);
                    if (statusicon_color == -1) {
                        u.a(getActivity(), false);
                    } else if (statusicon_color == -16777216) {
                        u.a(getActivity(), true);
                    } else {
                        u.a(getActivity(), true);
                    }
                }
            } catch (IllegalArgumentException e) {
            } catch (Exception e2) {
            }
        }
    }

    protected void getDefaultThemeConfig() {
        try {
            FragmentActivity activity = getActivity();
            this.actionbar_bg = u.c(activity);
            this.actionbar_title_color = u.a(activity);
            this.window_bg = activity.getWindow().getDecorView().getBackground();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mHasStoreTheme = true;
    }

    public void loadData() {
        if (getArguments() != null) {
            String url = getRequestUrl();
            if (!TextUtils.isEmpty(url)) {
                TypeReference typeReference = new TypeReference<ResultModel<ActivityWebviewInfo>>(this) {
                    final /* synthetic */ BaseEventWebviewFragment a;

                    {
                        this.a = r1;
                    }
                };
                showProgress();
                l jsonRequest = new FastJsonRequest(typeReference, 0, url, null, new n.b(this) {
                    final /* synthetic */ BaseEventWebviewFragment a;

                    {
                        this.a = r1;
                    }

                    public void a(Object response) {
                        if (this.a.mRunning) {
                            boolean hasLoadUrl = false;
                            String message = null;
                            if (response != null && (response instanceof ResultModel)) {
                                ResultModel<ActivityWebviewInfo> result = (ResultModel) response;
                                if (result != null) {
                                    this.a.mActivityInfo = (ActivityWebviewInfo) result.getValue();
                                    if (result.getCode() == 200 && this.a.mActivityInfo != null && this.a.mActivityInfo.html_url != null) {
                                        this.a.loadUrlWithParams(this.a.mActivityInfo.html_url);
                                        this.a.applyThemeColor(this.a.mActivityInfo);
                                        hasLoadUrl = true;
                                    } else if (result.getMessage() != null) {
                                        message = result.getMessage();
                                    }
                                }
                            }
                            if (!hasLoadUrl) {
                                if (TextUtils.isEmpty(message)) {
                                    this.a.showEmptyView(this.a.getEmptyTextString(), true);
                                } else {
                                    this.a.showEmptyView(message, false);
                                }
                            }
                        }
                    }
                }, new a(this) {
                    final /* synthetic */ BaseEventWebviewFragment a;

                    {
                        this.a = r1;
                    }

                    public void a(s error) {
                        if (this.a.mRunning) {
                            this.a.showEmptyView(this.a.getEmptyTextString(), true);
                        }
                    }
                });
                jsonRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.mActivity));
                com.meizu.volley.b.a(this.mActivity).a().a(jsonRequest);
            }
        }
    }

    public WebChromeClient createWebChromeClient() {
        return new WebChromeClient(this) {
            final /* synthetic */ BaseEventWebviewFragment a;

            {
                this.a = r1;
            }

            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                AlertDialog dialog = new Builder(this.a.mActivity).setTitle(message).setPositiveButton(i.confirm, new OnClickListener(this) {
                    final /* synthetic */ AnonymousClass13 a;

                    {
                        this.a = r1;
                    }

                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
                result.confirm();
                r.a(dialog);
                return true;
            }
        };
    }

    public WebViewClient createWebviewClient() {
        return new WebViewClient(this) {
            final /* synthetic */ BaseEventWebviewFragment a;

            {
                this.a = r1;
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                this.a.showContent();
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(this.a.mActivity, description, 1).show();
                super.onReceivedError(view, errorCode, description, failingUrl);
                this.a.showEmptyView(this.a.getEmptyTextString(), true);
            }
        };
    }

    public List<com.meizu.cloud.app.core.e> createJavascriptInterfaces() {
        List<com.meizu.cloud.app.core.e> list = new ArrayList();
        this.mEventJsInterface = new EventJavascriptInterface(this);
        list.add(this.mEventJsInterface);
        return list;
    }

    @JavascriptInterface
    public String getUserId() {
        String userId = com.meizu.cloud.a.c.c(this.mActivity);
        return TextUtils.isEmpty(userId) ? "" : userId;
    }

    @JavascriptInterface
    public void requestLoginStatus() {
        loadJavaScript(this.mEventJsInterface.a(!TextUtils.isEmpty(getUserId())));
    }

    @JavascriptInterface
    public String getIMEI() {
        return com.meizu.cloud.app.utils.d.a(this.mActivity);
    }

    @JavascriptInterface
    public void lottery(final String[] zippoIdArray) {
        runOnUi(new Runnable(this) {
            final /* synthetic */ BaseEventWebviewFragment b;

            public void run() {
                k.c(this.b.mActivity, this.b.TAG, "lottery");
                this.b.requestLoginToken(zippoIdArray, false);
            }
        });
    }

    @JavascriptInterface
    public void login() {
        if (getActivity() != null) {
            k.c(this.mActivity, this.TAG, "login");
        }
        requestLoginToken(null, false);
    }

    private void requestLoginToken(final String[] zippoIdArray, boolean invalideToken) {
        final boolean lotterRequest = zippoIdArray != null;
        if (zippoIdArray != null) {
            if (!this.mHasLotteryRequest) {
                this.mHasLotteryRequest = true;
            } else {
                return;
            }
        }
        this.mLoginAuthHelper = new com.meizu.cloud.a.b(this, 1, new com.meizu.cloud.a.a(this) {
            final /* synthetic */ BaseEventWebviewFragment c;

            public void a(String token, boolean isFromLogin) {
                if (this.c.getActivity() != null) {
                    if (this.c.isAdded() && this.c.mActivityInfo != null && zippoIdArray != null && !isFromLogin) {
                        this.c.requestLottery(zippoIdArray);
                    } else if (isFromLogin) {
                        if (lotterRequest) {
                            this.c.mHasLotteryRequest = false;
                        }
                        this.c.loadData();
                    } else {
                        if (lotterRequest) {
                            this.c.mHasLotteryRequest = false;
                        }
                        this.c.reportInstallStatus(this.c.mEventTaskInfos);
                    }
                }
            }

            public void a(int errorCode) {
                this.c.onAuthErrorHandle(errorCode, lotterRequest);
                if (lotterRequest) {
                    this.c.mHasLotteryRequest = false;
                }
            }
        });
        this.mLoginAuthHelper.a(invalideToken);
    }

    private void onAuthErrorHandle(int errorCode, boolean lotteryRequest) {
        if (isAdded()) {
            String msg;
            if (errorCode != 4) {
                msg = getString(i.access_account_info_error);
                Toast.makeText(this.mActivity, msg, 0).show();
            } else {
                msg = getString(i.unlogin);
            }
            if (lotteryRequest) {
                loadJavaScript(this.mEventJsInterface.a(msg));
            }
            k.c(this.mActivity, this.TAG, msg);
        }
    }

    private void reportInstallStatus(List<EventTaskInfo> eventTaskInfos) {
        if (this.mEventJsInterface != null && this.mActivityInfo != null && eventTaskInfos != null) {
            ArrayList<String> reportList = new ArrayList();
            com.meizu.cloud.app.utils.s.a(this.mActivity);
            for (EventTaskInfo e : eventTaskInfos) {
                boolean isInstalled = x.d(this.mActivity).a(e.pkgName);
                boolean isReported = com.meizu.cloud.app.utils.s.b(this.mActivity, String.valueOf(this.mActivityInfo.id), String.valueOf(e.taskId));
                if (isInstalled && !isReported) {
                    reportList.add(String.valueOf(e.taskId));
                }
            }
            refreshChance(reportList);
        }
    }

    private void refreshChance(final ArrayList<String> reportList) {
        if (!com.meizu.cloud.app.utils.m.b(this.mActivity)) {
            getWebView().setVisibility(8);
            showEmptyView(getString(i.nonetwork), true);
        } else if (reportList == null || reportList.size() <= 0) {
            loadJavaScript(this.mEventJsInterface.d());
        } else {
            this.mEventJsInterface.a(this.mActivity, String.valueOf(this.mActivityInfo.id), (List) reportList, new n.b<ResultModel<JSONObject>>(this) {
                final /* synthetic */ BaseEventWebviewFragment b;

                public void a(ResultModel<JSONObject> resultModel) {
                    if (this.b.mRunning) {
                        this.b.loadJavaScript(this.b.mEventJsInterface.d());
                        com.meizu.cloud.app.utils.s.a(this.b.mActivity, String.valueOf(this.b.mActivityInfo.id), reportList);
                    }
                }
            }, new a(this) {
                final /* synthetic */ BaseEventWebviewFragment a;

                {
                    this.a = r1;
                }

                public void a(s error) {
                    if (this.a.mRunning) {
                        k.c(this.a.mActivity, this.a.TAG, "requestLottery,onErrorResponse");
                        if (!(error instanceof com.android.volley.a) || this.a.mTokenFailCount >= 10) {
                            this.a.loadJavaScript(this.a.mEventJsInterface.d());
                        } else {
                            this.a.mTokenFailCount = this.a.mTokenFailCount + 1;
                            k.c(this.a.mActivity, this.a.TAG, "token invalid");
                            this.a.requestLoginToken(null, true);
                        }
                        if (!com.meizu.cloud.app.utils.m.b(this.a.mActivity)) {
                            this.a.getWebView().setVisibility(8);
                            this.a.showEmptyView(this.a.getString(i.nonetwork), true);
                        }
                    }
                }
            });
        }
    }

    private void requestLottery(final String[] zippoIdArray) {
        if (com.meizu.cloud.app.utils.m.b(this.mActivity)) {
            loadJavaScript(this.mEventJsInterface.c());
            this.mEventJsInterface.a(this.mActivity, String.valueOf(this.mActivityInfo.id), zippoIdArray, new n.b<String>(this) {
                final /* synthetic */ BaseEventWebviewFragment a;

                {
                    this.a = r1;
                }

                public void a(String resultModel) {
                    if (this.a.mRunning) {
                        this.a.loadJavaScript(this.a.mEventJsInterface.a(resultModel));
                        this.a.mHasLotteryRequest = false;
                    }
                }
            }, new a(this) {
                final /* synthetic */ BaseEventWebviewFragment b;

                public void a(s error) {
                    if (this.b.mRunning) {
                        j.c(this.b.TAG, "requestLottery,onErrorResponse");
                        this.b.mHasLotteryRequest = false;
                        if (!(error instanceof com.android.volley.a) || this.b.mTokenFailCount >= 10) {
                            this.b.loadJavaScript(this.b.mEventJsInterface.a(com.meizu.cloud.app.utils.m.b(this.b.mActivity) ? this.b.getString(i.nonetwork) : this.b.getString(i.server_error)));
                            return;
                        }
                        this.b.mTokenFailCount = this.b.mTokenFailCount + 1;
                        this.b.requestLoginToken(zippoIdArray, true);
                    }
                }
            });
            return;
        }
        showNoticeOnUi(getString(i.nonetwork));
        this.mHasLotteryRequest = false;
        loadJavaScript(this.mEventJsInterface.a(getString(i.nonetwork)));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (this.mLoginAuthHelper != null) {
                    this.mLoginAuthHelper.a(requestCode, resultCode, data);
                    return;
                }
                return;
            default:
                return;
        }
    }

    @JavascriptInterface
    public void installAppById(int appId) {
        if (com.meizu.cloud.app.utils.m.b(this.mActivity)) {
            requestDownload(appId, null);
        } else {
            showNoticeOnUi(getString(i.nonetwork));
        }
    }

    @JavascriptInterface
    public boolean isAppInstalled(String pkgName) {
        return x.d(this.mActivity).a(pkgName);
    }

    @JavascriptInterface
    public void requestChance(String jsonArrayStr) {
        try {
            this.mEventTaskInfos = JSON.parseArray(jsonArrayStr, EventTaskInfo.class);
            if (!TextUtils.isEmpty(getUserId())) {
                requestLoginToken(null, false);
            }
        } catch (Exception e) {
            if (getActivity() != null) {
                k.c(getActivity(), this.TAG, e.toString());
            }
            com.meizu.cloud.app.utils.a.a(getActivity(), getString(i.server_error));
        }
    }

    @JavascriptInterface
    public void share(String appName, String content, String[] imgUrls, long task_id, String title, String websiteUrl) {
        if (!com.meizu.cloud.app.utils.m.b(this.mActivity)) {
            showNoticeOnUi(getString(i.nonetwork));
        } else if (checkShareApp(appName)) {
            final String[] strArr = imgUrls;
            final String str = appName;
            final String str2 = title;
            final String str3 = content;
            final long j = task_id;
            final String str4 = websiteUrl;
            asyncExec(new Runnable(this) {
                final /* synthetic */ BaseEventWebviewFragment g;

                public void run() {
                    this.g.runOnUi(new Runnable(this) {
                        final /* synthetic */ AnonymousClass4 a;

                        {
                            this.a = r1;
                        }

                        public void run() {
                            this.a.g.showProgress();
                        }
                    });
                    final ArrayList<File> fileList = this.g.downloadImges(strArr);
                    this.g.runOnUi(new Runnable(this) {
                        final /* synthetic */ AnonymousClass4 b;

                        public void run() {
                            this.b.g.initShareIntent(str, str2, str3, fileList, j, str4);
                            this.b.g.hideProgress();
                        }
                    });
                }
            });
        }
    }

    @JavascriptInterface
    public void onAppShowInPage(String[] pkgNames) {
        for (String pkgName : pkgNames) {
            notifyDataChange(pkgName);
        }
    }

    @JavascriptInterface
    public void onInstallButtonClick(int appId, String apkName) {
        boolean bFound = false;
        for (com.meizu.cloud.app.downlad.e item : com.meizu.cloud.app.downlad.d.a(this.mActivity).g(1, 3)) {
            if (item.i() == appId) {
                bFound = true;
                this.mPageInfo[2] = (int) this.mActivityInfo.id;
                item.m().page_info = this.mPageInfo;
                item.m().install_page = this.mPageName;
                if (!(this.mViewController == null || TextUtils.isEmpty(this.mPageName) || !TextUtils.isEmpty(this.mViewController.b()))) {
                    this.mViewController.a(this.mPageName);
                }
                this.mViewController.a(new com.meizu.cloud.app.core.k(item.m()));
                if (!bFound) {
                    for (Pair<String, Integer> localAppInfo : x.d(this.mActivity).a()) {
                        if (((String) localAppInfo.first).equals(apkName)) {
                            bFound = true;
                            break;
                        }
                    }
                    if (bFound) {
                    }
                    if (com.meizu.cloud.app.utils.m.b(this.mActivity)) {
                        showNoticeOnUi(getString(i.nonetwork));
                    } else {
                        requestDownload(appId, apkName);
                    }
                }
            }
        }
        if (!bFound) {
            while (i$.hasNext()) {
                if (((String) localAppInfo.first).equals(apkName)) {
                    bFound = true;
                    break;
                }
            }
            if (bFound) {
                if (com.meizu.cloud.app.utils.m.b(this.mActivity)) {
                    showNoticeOnUi(getString(i.nonetwork));
                } else {
                    requestDownload(appId, apkName);
                }
            }
        }
    }

    public void onInstallStateChange(com.meizu.cloud.app.downlad.e wrapper) {
        notifyDataChange(wrapper.g());
        if (wrapper.f() == f.INSTALL_SUCCESS && this.mEventTaskInfos != null && !TextUtils.isEmpty(getUserId())) {
            requestLoginToken(null, false);
        }
    }

    public void onFetchStateChange(com.meizu.cloud.app.downlad.e wrapper) {
        if (wrapper.f() == com.meizu.cloud.app.downlad.f.n.FETCHING) {
            notifyDataStart(wrapper.g());
        } else {
            notifyDataChange(wrapper.g());
        }
    }

    public void onDownloadProgress(com.meizu.cloud.app.downlad.e wrapper) {
        notifyDataChange(wrapper.g());
    }

    public void onDownloadStateChanged(com.meizu.cloud.app.downlad.e wrapper) {
        if (wrapper.f() != com.meizu.cloud.app.downlad.f.c.TASK_STARTED) {
            notifyDataChange(wrapper.g());
        }
    }

    protected void requestDownload(final int appId, final String callBackPkgName) {
        if (!TextUtils.isEmpty(callBackPkgName)) {
            notifyDataStart(callBackPkgName);
        }
        this.mDetailRequest = new FastJsonRequest(new TypeReference<ResultModel<AppStructDetailsItem>>(this) {
            final /* synthetic */ BaseEventWebviewFragment a;

            {
                this.a = r1;
            }
        }, 0, RequestConstants.getRuntimeDomainUrl(this.mActivity, "/public/detail/") + appId, null, new n.b<ResultModel<AppStructDetailsItem>>(this) {
            final /* synthetic */ BaseEventWebviewFragment b;

            public void a(ResultModel<AppStructDetailsItem> response) {
                if (this.b.mRunning) {
                    boolean bPerformed = false;
                    if (!(response == null || response.getValue() == null)) {
                        if (this.b.mActivityInfo != null) {
                            this.b.mPageInfo[2] = (int) this.b.mActivityInfo.id;
                            ((AppStructDetailsItem) response.getValue()).page_info = this.b.mPageInfo;
                            ((AppStructDetailsItem) response.getValue()).install_page = this.b.mPageName;
                        }
                        AppStructItem item = (AppStructDetailsItem) response.getValue();
                        if (item != null) {
                            com.meizu.cloud.app.downlad.e downloadWrapper;
                            if (item.price <= 0.0d) {
                                downloadWrapper = com.meizu.cloud.app.downlad.d.a(this.b.mActivity).a(item, new g(2, 1));
                                com.meizu.cloud.app.downlad.d.a(this.b.mActivity).a(this.b.mActivity, downloadWrapper);
                                com.meizu.cloud.statistics.b.a().a("install", this.b.mPageName, c.b(downloadWrapper.m()));
                            } else if (com.meizu.cloud.app.core.i.c(this.b.getActivity())) {
                                downloadWrapper = com.meizu.cloud.app.downlad.d.a(this.b.mActivity).a(item, new g(4, 1));
                                com.meizu.cloud.app.downlad.d.a(this.b.mActivity).a(this.b.mActivity, downloadWrapper);
                                com.meizu.cloud.statistics.b.a().a("install", this.b.mPageName, c.b(downloadWrapper.m()));
                            } else {
                                t.a(this.b.getActivity(), this.b.mActivity.getString(i.appcenter_not_install), 0, 0);
                            }
                            bPerformed = true;
                        }
                    }
                    if (!bPerformed) {
                        if (response != null) {
                            t.a(this.b.mActivity, TextUtils.isEmpty(response.getMessage()) ? this.b.getString(i.download_error) : response.getMessage(), 0, 0);
                        }
                        if (!TextUtils.isEmpty(callBackPkgName)) {
                            this.b.notifyDataChange(callBackPkgName);
                        }
                    }
                }
            }
        }, new a(this) {
            final /* synthetic */ BaseEventWebviewFragment c;

            public void a(s error) {
                if (this.c.mRunning) {
                    Log.w(toString(), "get detail error: " + appId);
                    t.a(this.c.mActivity, this.c.getString(i.download_error), 0, 0);
                    if (!TextUtils.isEmpty(callBackPkgName)) {
                        this.c.notifyDataChange(callBackPkgName);
                    }
                }
            }
        });
        this.mDetailRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(getActivity()));
        com.meizu.volley.b.a(getActivity()).a().a(this.mDetailRequest);
        com.meizu.cloud.statistics.b.a().a("install_activities", this.mPageName, null);
    }

    private void notifyDataStart(String pkgName) {
        if (this.mEventJsInterface != null && this.mActivity != null) {
            loadJavaScript(this.mEventJsInterface.a(pkgName, getString(i.waiting_url), getResources().getColor(com.meizu.cloud.b.a.c.btn_operation_downloading_text), getResources().getColor(com.meizu.cloud.b.a.c.transparent), false, false));
        }
    }

    private void notifyDataChange(String pkgName) {
        if (this.mEventJsInterface != null && this.mActivity != null) {
            final String js = this.mEventJsInterface.a(this.mActivity, pkgName, this.mActivityInfo, this.mViewController);
            asyncExec(new Runnable(this) {
                final /* synthetic */ BaseEventWebviewFragment b;

                public void run() {
                    Process.setThreadPriority(10);
                    this.b.loadJavaScript(js);
                }
            });
        }
    }

    private ArrayList<File> downloadImges(String[] imgUrls) {
        ArrayList<File> imageFiles = new ArrayList();
        for (String a : imgUrls) {
            File fileImg = new File(String.format("%s/%s[%d].png", new Object[]{h.b(this.mActivity), "share", Integer.valueOf(i)}));
            try {
                h.a(a, fileImg);
                imageFiles.add(fileImg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return imageFiles;
    }

    private void initShareIntent(String activityName, String title, String content, ArrayList<File> files, long task_id, String websiteUrl) {
        Intent share = new Intent("android.intent.action.SEND_MULTIPLE");
        share.setType("image/*");
        ArrayList<Uri> imageUris = new ArrayList();
        Iterator i$ = files.iterator();
        while (i$.hasNext()) {
            File file = (File) i$.next();
            if (file.exists() && file.isFile()) {
                imageUris.add(Uri.fromFile(file));
            }
        }
        List<ResolveInfo> resInfo = this.mActivity.getPackageManager().queryIntentActivities(share, 0);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.name.equals(activityName)) {
                    share.putExtra("android.intent.extra.SUBJECT", title);
                    if (activityName.equals("com.tencent.mm.ui.tools.ShareToTimeLineUI")) {
                        shareUseWechat(title, content, files, websiteUrl, true);
                    } else if (activityName.equals("com.tencent.mm.ui.tools.ShareImgUI")) {
                        shareUseWechat(title, content, files, websiteUrl, false);
                    } else {
                        share.putExtra("android.intent.extra.TEXT", content);
                        share.putParcelableArrayListExtra("android.intent.extra.STREAM", imageUris);
                        share.setPackage(info.activityInfo.packageName);
                        share.setClassName(info.activityInfo.packageName, info.activityInfo.name);
                        try {
                            startActivity(share);
                            break;
                        } catch (ActivityNotFoundException e) {
                        }
                    }
                }
            }
        }
        if (activityName.equals("com.sina.weibo.ComposerDispatchActivity")) {
            com.meizu.cloud.statistics.b.a().a("share_to_microblog", this.mPageName, null);
        } else if (activityName.equals("com.tencent.mm.ui.tools.ShareToTimeLineUI")) {
            com.meizu.cloud.statistics.b.a().a("share_to_moments", this.mPageName, null);
        }
    }

    protected void shareUseWechat(String title, String content, ArrayList<File> files, String websiteUrl, boolean isTOTimeLine) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = websiteUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = content;
        if (files != null && files.size() > 0) {
            msg.thumbData = com.meizu.cloud.app.share.b.a(Bitmap.createScaledBitmap(BitmapFactory.decodeFile(((File) files.get(0)).getAbsolutePath()), 90, 90, true), true);
        }
        Req req = new Req();
        req.transaction = com.meizu.cloud.app.share.b.a("webpage");
        req.message = msg;
        if (isTOTimeLine) {
            req.scene = 1;
        } else {
            req.scene = 0;
        }
        getWxApi().sendReq(req);
    }

    protected void registerWxApiBeforeShare() {
        if (x.b(getActivity())) {
            if (com.meizu.cloud.app.share.b.a(getActivity(), "com.meizu.flyme.gamecenter").equals("c4aa9b9deb124fe4bae4c2ffdc05fac6")) {
                this.mAPI = WXAPIFactory.createWXAPI(getActivity(), USER_GAMECENTER_APPID, true);
                this.mAPI.registerApp(USER_GAMECENTER_APPID);
                return;
            }
            this.mAPI = WXAPIFactory.createWXAPI(getActivity(), PRD_GAMECENTER_APPID, true);
            this.mAPI.registerApp(PRD_GAMECENTER_APPID);
        } else if (com.meizu.cloud.app.share.b.a(getActivity(), "com.meizu.mstore").equals("c4aa9b9deb124fe4bae4c2ffdc05fac6")) {
            this.mAPI = WXAPIFactory.createWXAPI(getActivity(), USER_APPCENTER_APPID, true);
            this.mAPI.registerApp(USER_APPCENTER_APPID);
        } else {
            this.mAPI = WXAPIFactory.createWXAPI(getActivity(), PRD_APPCENTER_APPID, true);
            this.mAPI.registerApp(PRD_APPCENTER_APPID);
        }
    }

    public IWXAPI getWxApi() {
        return this.mAPI;
    }

    private boolean checkShareApp(String activityName) {
        String msg = "";
        String pkgName = "";
        if (activityName.contains("com.sina.weibo")) {
            if (x.d(this.mActivity).a("com.sina.weibo")) {
                return true;
            }
            msg = getString(i.event_install_weibo);
            pkgName = "com.sina.weibo";
        } else if (activityName.contains(WXApp.WXAPP_PACKAGE_NAME)) {
            if (x.d(this.mActivity).a(WXApp.WXAPP_PACKAGE_NAME)) {
                return true;
            }
            msg = getString(i.event_install_wechat);
            pkgName = WXApp.WXAPP_PACKAGE_NAME;
        }
        final String installPkg = pkgName;
        if (!(TextUtils.isEmpty(msg) || TextUtils.isEmpty(pkgName))) {
            r.a(new Builder(this.mActivity).setTitle(msg).setMessage(i.event_share).setNegativeButton(i.cancel, new OnClickListener(this) {
                final /* synthetic */ BaseEventWebviewFragment a;

                {
                    this.a = r1;
                }

                public void onClick(DialogInterface dialog, int which) {
                }
            }).setPositiveButton(i.install, new OnClickListener(this) {
                final /* synthetic */ BaseEventWebviewFragment b;

                public void onClick(DialogInterface dialog, int which) {
                    this.b.gotoAppInfoPage(installPkg);
                }
            }).show());
        }
        return false;
    }

    public void onStart() {
        super.onStart();
        com.meizu.cloud.statistics.b.a().a(this.mPageName);
    }

    public void onStop() {
        super.onStop();
        com.meizu.cloud.statistics.b.a().a(this.mPageName, buildWdmParamsMap());
    }

    public Map<String, String> buildWdmParamsMap() {
        Map<String, String> wdmParamsMap = new HashMap();
        wdmParamsMap.put("topicid", String.valueOf(this.mPageInfo[2]));
        wdmParamsMap.put("topicname", getArguments().getString("title_name", ""));
        if (this.mUxipPageSourceInfo != null) {
            wdmParamsMap.put("source_page", this.mUxipPageSourceInfo.f);
            wdmParamsMap.put("source_block_id", String.valueOf(this.mUxipPageSourceInfo.b));
            wdmParamsMap.put("source_block_name", this.mUxipPageSourceInfo.c);
            wdmParamsMap.put("source_block_type", this.mUxipPageSourceInfo.a);
            if (this.mUxipPageSourceInfo.g > 0) {
                wdmParamsMap.put("source_block_profile_id", String.valueOf(this.mUxipPageSourceInfo.g));
            }
            wdmParamsMap.put("source_pos", String.valueOf(this.mUxipPageSourceInfo.d));
            if (this.mUxipPageSourceInfo.e > 0) {
                wdmParamsMap.put("source_hor_pos", String.valueOf(this.mUxipPageSourceInfo.e));
            }
        } else if (!TextUtils.isEmpty(this.mUxipSourcePage)) {
            wdmParamsMap.put("source_page", this.mUxipSourcePage);
        }
        long pushId = getArguments().getLong("push_message_id", 0);
        if (pushId > 0) {
            wdmParamsMap.put("push_id", String.valueOf(pushId));
        }
        return wdmParamsMap;
    }

    public boolean onWebViewBackPressed() {
        if (getWebView() == null || !getWebView().canGoBack()) {
            return false;
        }
        getWebView().goBack();
        return true;
    }
}
