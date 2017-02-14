package sdk.meizu.traffic;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v7.app.AlertDialog.Builder;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.meizu.account.pay.MzPayPlatform;
import com.meizu.account.pay.OutTradeOrderInfo;
import com.meizu.account.pay.PayListener;
import com.meizu.statsapp.UsageStatsProxy;
import defpackage.ang;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sdk.meizu.traffic.auth.AuthListener;
import sdk.meizu.traffic.auth.CUserManager;
import sdk.meizu.traffic.auth.MeizuAccountInfo;
import sdk.meizu.traffic.auth.MzAccountManager;
import sdk.meizu.traffic.auth.MzAuthException;
import sdk.meizu.traffic.hybird.HyBirdView;
import sdk.meizu.traffic.hybird.JsCmd;
import sdk.meizu.traffic.hybird.PageConstants;
import sdk.meizu.traffic.hybird.method.BaseNativeInterface;
import sdk.meizu.traffic.hybird.method.BaseNativeInterface.AuthHandler;
import sdk.meizu.traffic.hybird.method.BaseNativeInterface.EventHandler;
import sdk.meizu.traffic.hybird.method.BaseNativeInterface.ImHandler;
import sdk.meizu.traffic.hybird.method.BaseNativeInterface.LoadingHandler;
import sdk.meizu.traffic.hybird.method.BaseNativeInterface.PayHandler;
import sdk.meizu.traffic.hybird.method.BaseNativeInterface.PhoneHandler;
import sdk.meizu.traffic.hybird.method.BaseNativeInterface.ToastHandler;
import sdk.meizu.traffic.interfaces.ActionBarHandler;
import sdk.meizu.traffic.util.ContactUtil;
import sdk.meizu.traffic.util.GeoUtil;
import sdk.meizu.traffic.util.InputMethodHelper;
import sdk.meizu.traffic.util.PhoneUtils;

public class TrafficMainFragment extends Fragment {
    private static final int MESSAGE_SEARCH_CONTACT = 10000;
    private static final String TAG = TrafficMainFragment.class.getSimpleName();
    private final int REQUEST_CODE_LOGIN = 100;
    private final int REQUEST_CODE_LOGIN_INIT = 101;
    private final int REQUEST_CONTACT = 1000;
    private Activity mActivity;
    private JsCmd mGetNumberCmd;
    private HyBirdView mHybirdView;
    private BaseNativeInterface mNativeInterface;
    private String mPageData;
    private String mPageUrl;
    protected ProgressDialog mProgressDialog;
    protected SearchContactThread mSearchContactThread;
    protected Handler mUiHandler = new Handler() {
        public void handleMessage(Message message) {
            if (message.what == 10000 && TrafficMainFragment.this.mSearchContactThread != null) {
                TrafficMainFragment.this.mSearchContactThread.start();
            }
        }
    };

    public class SearchContactThread extends Thread {
        private Activity activity;
        private String mQueryPhone = "";
        private JsCmd mSearchCallback;

        public SearchContactThread(Activity activity, String str, JsCmd jsCmd) {
            this.mQueryPhone = str;
            this.mSearchCallback = jsCmd;
            this.activity = activity;
        }

        public void run() {
            ArrayList searchContactsByNum = ContactUtil.searchContactsByNum(this.activity, this.mQueryPhone);
            final JSONArray jSONArray = new JSONArray();
            try {
                Iterator it = searchContactsByNum.iterator();
                while (it.hasNext()) {
                    Pair pair = (Pair) it.next();
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("pName", pair.first);
                    jSONObject.put("pNums", pair.second);
                    jSONArray.put(jSONObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            TrafficMainFragment.this.mUiHandler.post(new Runnable() {
                public void run() {
                    if (TrafficMainFragment.this.mHybirdView != null && TrafficMainFragment.this.mHybirdView.getWebView() != null) {
                        SearchContactThread.this.mSearchCallback.setMethodArgs(jSONArray.toString()).execute(TrafficMainFragment.this.mHybirdView.getWebView());
                    }
                }
            });
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mActivity = getActivity();
        this.mActivity.setTitle(R.string.title_buy_data);
        ((ActionBarHandler) this.mActivity).enableCustomView(true);
        ((ActionBarHandler) this.mActivity).enableHomeBack(false);
        this.mProgressDialog = new ProgressDialog(this.mActivity);
        this.mProgressDialog.setMessage(getString(R.string.msg_loading));
        getToken();
        initParams();
        return initContentView();
    }

    protected void getToken() {
        CUserManager.getInstance(this.mActivity).setCacheToken(null);
        CUserManager.getInstance(this.mActivity.getApplicationContext()).getTokenAsync(false, new AuthListener() {
            public void onLoginRequst(Intent intent) {
                if (TrafficMainFragment.this.isAdded()) {
                    TrafficMainFragment.this.startActivityForResult(intent, 101);
                }
            }

            public void onSuccess(String str) {
                TrafficMainFragment.this.loadPage();
            }

            public void onError(int i) {
                if (!ang.b(TrafficMainFragment.this.mActivity)) {
                    TrafficMainFragment.this.showNoNetworkView();
                }
            }
        });
    }

    protected void loadPage() {
        if (ang.b(this.mActivity)) {
            this.mHybirdView.postDelayed(new Runnable() {
                public void run() {
                    TrafficMainFragment.this.mHybirdView.getWebView().loadUrl(TrafficMainFragment.this.mPageUrl);
                    TrafficMainFragment.this.mHybirdView.startLoading();
                }
            }, 100);
        } else {
            showNoNetworkView();
        }
    }

    private void showNoNetworkView() {
        this.mHybirdView.showNoNetwork();
    }

    protected void initParams() {
        Bundle bundle = null;
        Intent intent = this.mActivity.getIntent();
        if (intent != null) {
            bundle = intent.getExtras();
        }
        if (bundle != null) {
            this.mPageUrl = bundle.getString("url");
            this.mPageData = bundle.getString(PageConstants.PARAM_DATA);
        }
        if (TextUtils.isEmpty(this.mPageUrl)) {
            this.mActivity.finish();
        }
    }

    protected View initContentView() {
        this.mNativeInterface = new BaseNativeInterface();
        this.mNativeInterface.setLoadingHandler(new LoadingHandler() {
            public void startLoading(String str) {
                TrafficMainFragment.this.mUiHandler.post(new Runnable() {
                    public void run() {
                        if (TrafficMainFragment.this.mHybirdView != null) {
                            TrafficMainFragment.this.mProgressDialog.show();
                        }
                    }
                });
            }

            public void stopLoading() {
                TrafficMainFragment.this.mUiHandler.post(new Runnable() {
                    public void run() {
                        if (TrafficMainFragment.this.mHybirdView != null) {
                            TrafficMainFragment.this.mProgressDialog.dismiss();
                        }
                    }
                });
            }
        });
        this.mNativeInterface.setToastHandler(new ToastHandler() {
            public void toast(String str) {
                Toast.makeText(TrafficMainFragment.this.mActivity, str, 0).show();
            }

            public void toastError(String str) {
                Toast.makeText(TrafficMainFragment.this.mActivity, str, 0).show();
            }
        });
        this.mNativeInterface.setAuthHandler(new AuthHandler() {
            public void getAuthToken(boolean z, final JsCmd jsCmd) {
                try {
                    final String token = CUserManager.getInstance(TrafficMainFragment.this.mActivity).getToken(z);
                    TrafficMainFragment.this.mUiHandler.postDelayed(new Runnable() {
                        public void run() {
                            if (TextUtils.isEmpty(token)) {
                                Log.e(TrafficMainFragment.TAG, "token null");
                            } else if (TrafficMainFragment.this.mHybirdView.getWebView() != null) {
                                jsCmd.setMethodArgs("\"" + token + "\"").execute(TrafficMainFragment.this.mHybirdView.getWebView());
                                Log.e("BaseNativeInterface", "token callback :" + jsCmd.toString());
                            }
                        }
                    }, 200);
                } catch (final MzAuthException e) {
                    if (e.needLogin()) {
                        TrafficMainFragment.this.mUiHandler.post(new Runnable() {
                            public void run() {
                                if (TrafficMainFragment.this.isAdded()) {
                                    TrafficMainFragment.this.startActivityForResult(e.getLoginIntent(), 100);
                                }
                            }
                        });
                    }
                }
            }
        });
        this.mNativeInterface.setEventHandler(new EventHandler() {
            public void usageEvent(String str, JSONObject jSONObject) {
                if (!TextUtils.isEmpty(str)) {
                    Map linkedHashMap = new LinkedHashMap();
                    if (jSONObject != null) {
                        Iterator keys = jSONObject.keys();
                        while (keys.hasNext()) {
                            String str2 = (String) keys.next();
                            try {
                                CharSequence string = jSONObject.getString(str2);
                                if (!TextUtils.isEmpty(string)) {
                                    linkedHashMap.put(str2, string);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    UsageStatsProxy.a(TrafficMainFragment.this.mActivity, true).a(str, TrafficMainFragment.TAG, linkedHashMap);
                    Log.v("UsageEvent", "eventName:" + str);
                }
            }
        });
        this.mNativeInterface.setPhoneHandler(new PhoneHandler() {
            public void getDefaultNum(final JsCmd jsCmd) {
                CharSequence checkAndNormalizePhoneNum = PhoneUtils.checkAndNormalizePhoneNum(TrafficMainFragment.this.mActivity, ((TelephonyManager) TrafficMainFragment.this.mActivity.getSystemService("phone")).getLine1Number());
                Object obj = "本机号码";
                if (TextUtils.isEmpty(checkAndNormalizePhoneNum)) {
                    MeizuAccountInfo loadMzAccountInfo = MzAccountManager.loadMzAccountInfo(TrafficMainFragment.this.mActivity);
                    if (loadMzAccountInfo != null) {
                        checkAndNormalizePhoneNum = loadMzAccountInfo.getPhone();
                        obj = "账户绑定号码";
                    }
                }
                if (!TextUtils.isEmpty(checkAndNormalizePhoneNum)) {
                    final JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("pNums", checkAndNormalizePhoneNum);
                        jSONObject.put("pName", obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    TrafficMainFragment.this.mUiHandler.post(new Runnable() {
                        public void run() {
                            if (TrafficMainFragment.this.mHybirdView != null && TrafficMainFragment.this.mHybirdView.getWebView() != null) {
                                jsCmd.setMethodArgs(jSONObject.toString()).execute(TrafficMainFragment.this.mHybirdView.getWebView());
                            }
                        }
                    });
                }
            }

            public void getPhoneImei(final JsCmd jsCmd) {
                final Object phoneImei = PhoneUtils.getPhoneImei(TrafficMainFragment.this.mActivity);
                if (!TextUtils.isEmpty(phoneImei)) {
                    TrafficMainFragment.this.mUiHandler.post(new Runnable() {
                        public void run() {
                            if (TrafficMainFragment.this.mHybirdView != null && TrafficMainFragment.this.mHybirdView.getWebView() != null) {
                                try {
                                    new JSONObject().put("imei", phoneImei);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                jsCmd.setMethodArgs(r1.toString()).execute(TrafficMainFragment.this.mHybirdView.getWebView());
                            }
                        }
                    });
                }
            }

            public void getPackageName(final JsCmd jsCmd) {
                TrafficMainFragment.this.mUiHandler.post(new Runnable() {
                    public void run() {
                        if (TrafficMainFragment.this.mHybirdView != null && TrafficMainFragment.this.mHybirdView.getWebView() != null) {
                            try {
                                new JSONObject().put("packageName", TrafficMainFragment.this.mActivity.getApplication().getPackageName());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            jsCmd.setMethodArgs(r1.toString()).execute(TrafficMainFragment.this.mHybirdView.getWebView());
                        }
                    }
                });
            }

            public void suggest(String str, JsCmd jsCmd) {
                TrafficMainFragment.this.mSearchContactThread = new SearchContactThread(TrafficMainFragment.this.mActivity, str, jsCmd);
                if (TrafficMainFragment.this.mUiHandler.hasMessages(10000)) {
                    TrafficMainFragment.this.mUiHandler.removeMessages(10000);
                }
                TrafficMainFragment.this.mUiHandler.sendEmptyMessageDelayed(10000, 500);
            }

            public void openContacts(JsCmd jsCmd) {
                TrafficMainFragment.this.mGetNumberCmd = jsCmd;
                TrafficMainFragment.this.mUiHandler.post(new Runnable() {
                    public void run() {
                        try {
                            if (TrafficMainFragment.this.isAdded()) {
                                TrafficMainFragment.this.startActivityForResult(new Intent("android.intent.action.PICK", Phone.CONTENT_URI), 1000);
                                UsageStatsProxy.a(TrafficMainFragment.this.mActivity, true).a("openContacts", TrafficMainFragment.TAG, "");
                                Log.v("UsageEvent", "eventName:openContacts");
                            }
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }
                });
            }

            public void getAreaMISP(String str, final JsCmd jsCmd) {
                final Object geocodedLocationFor = GeoUtil.getGeocodedLocationFor(TrafficMainFragment.this.mActivity, str);
                if (!TextUtils.isEmpty(geocodedLocationFor)) {
                    TrafficMainFragment.this.mUiHandler.post(new Runnable() {
                        public void run() {
                            if (TrafficMainFragment.this.mHybirdView != null && TrafficMainFragment.this.mHybirdView.getWebView() != null) {
                                jsCmd.setMethodArgs("'" + geocodedLocationFor + "'").execute(TrafficMainFragment.this.mHybirdView.getWebView());
                            }
                        }
                    });
                }
            }
        });
        this.mNativeInterface.setPayHandler(new PayHandler() {
            public void doPayAction(final TrafficOrder trafficOrder, final JsCmd jsCmd) {
                Map linkedHashMap = new LinkedHashMap();
                if (trafficOrder != null) {
                    linkedHashMap.put("totalFee", trafficOrder.getTotalFee());
                }
                UsageStatsProxy.a(TrafficMainFragment.this.mActivity, true).a("doPayAction", TrafficMainFragment.TAG, linkedHashMap);
                Log.v("UsageEvent", "eventName:doPayAction");
                TrafficMainFragment.this.mUiHandler.post(new Runnable() {
                    public void run() {
                        MzPayPlatform.pay(TrafficMainFragment.this.mActivity, trafficOrder, new PayListener() {
                            public void onPayResult(int i, OutTradeOrderInfo outTradeOrderInfo, String str) {
                                boolean z;
                                Map linkedHashMap;
                                switch (i) {
                                    case 0:
                                        TrafficMainFragment.this.showPaySuccessDialog();
                                        try {
                                            Intent intent = new Intent("sdk.meizu.traffic.DATA_CHANGE");
                                            intent.putExtra("ChangeType", "Trade");
                                            TrafficMainFragment.this.mActivity.sendBroadcast(intent);
                                            z = true;
                                            break;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            z = true;
                                            break;
                                        }
                                    case 2:
                                        linkedHashMap = new LinkedHashMap();
                                        linkedHashMap.put("reason", "cancel");
                                        UsageStatsProxy.a(TrafficMainFragment.this.mActivity, true).a("onPayFailed", TrafficMainFragment.TAG, linkedHashMap);
                                        Log.v("UsageEvent", "eventName:onPayFailed");
                                        z = false;
                                        break;
                                    default:
                                        linkedHashMap = new LinkedHashMap();
                                        linkedHashMap.put("reason", str);
                                        UsageStatsProxy.a(TrafficMainFragment.this.mActivity, true).a("onPayFailed", TrafficMainFragment.TAG, linkedHashMap);
                                        Log.v("UsageEvent", "eventName:onPayFailed");
                                        Toast.makeText(TrafficMainFragment.this.mActivity, str, 0).show();
                                        z = false;
                                        break;
                                }
                                if (TrafficMainFragment.this.mHybirdView != null && TrafficMainFragment.this.mHybirdView.getWebView() != null) {
                                    jsCmd.setMethodArgs(String.valueOf(z)).execute(TrafficMainFragment.this.mHybirdView.getWebView());
                                }
                            }
                        });
                    }
                });
            }
        });
        this.mNativeInterface.setImHandler(new ImHandler() {
            public void showInputMethod() {
                if (TrafficMainFragment.this.mHybirdView != null && TrafficMainFragment.this.mHybirdView.getWebView() != null) {
                    InputMethodHelper.showInputMethod(TrafficMainFragment.this.mActivity, TrafficMainFragment.this.mHybirdView.getWebView());
                }
            }

            public void closeInputMethod() {
                if (TrafficMainFragment.this.mHybirdView != null && TrafficMainFragment.this.mHybirdView.getWebView() != null) {
                    InputMethodHelper.closeInputMethod(TrafficMainFragment.this.mActivity, TrafficMainFragment.this.mHybirdView.getWebView());
                }
            }
        });
        this.mHybirdView = new HyBirdView(this.mActivity);
        this.mHybirdView.setNoNetworkClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (ang.b(TrafficMainFragment.this.mActivity)) {
                    TrafficMainFragment.this.getToken();
                    TrafficMainFragment.this.loadPage();
                    return;
                }
                Intent intent = new Intent("android.settings.SETTINGS");
                intent.setFlags(268435456);
                TrafficMainFragment.this.startActivity(intent);
            }
        });
        this.mHybirdView.getWebView().addJavascriptInterface(this.mNativeInterface.getJsToAndroidBridge(), "androidJs");
        this.mHybirdView.getWebView().setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
                if (TrafficMainFragment.this.mHybirdView != null) {
                    TrafficMainFragment.this.mHybirdView.stopLoading();
                }
            }

            public void onReceivedError(WebView webView, int i, String str, String str2) {
                super.onReceivedError(webView, i, str, str2);
                if (TrafficMainFragment.this.mHybirdView != null) {
                    TrafficMainFragment.this.mHybirdView.stopLoading();
                }
            }

            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                if (sslError.getUrl().startsWith("https://magneto.res.meizu.com")) {
                    sslErrorHandler.proceed();
                } else {
                    super.onReceivedSslError(webView, sslErrorHandler, sslError);
                }
                if (TrafficMainFragment.this.mHybirdView != null) {
                    TrafficMainFragment.this.mHybirdView.stopLoading();
                }
            }
        });
        return this.mHybirdView;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 100) {
            if (i2 != -1) {
                this.mActivity.finish();
            }
        } else if (i == 101) {
            if (i2 != -1) {
                this.mActivity.finish();
            } else {
                loadPage();
            }
        } else if (i != 1000) {
            super.onActivityResult(i, i2, intent);
        } else if (i2 == -1) {
            Pair loadPhoneData = ContactUtil.loadPhoneData(this.mActivity, intent.getData());
            if (loadPhoneData == null) {
                Toast.makeText(this.mActivity, getString(R.string.msg_empty_contact), 0).show();
            } else if (this.mGetNumberCmd != null && this.mHybirdView != null && this.mHybirdView.getWebView() != null) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("pName", loadPhoneData.first);
                    jSONObject.put("pNum", loadPhoneData.second);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                this.mGetNumberCmd.setMethodArgs(jSONObject.toString()).execute(this.mHybirdView.getWebView());
            }
        }
    }

    private void showPaySuccessDialog() {
        if (this.mActivity != null && !this.mActivity.isFinishing()) {
            Builder builder = new Builder(this.mActivity);
            builder.setMessage(R.string.msg_pay_success);
            builder.setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            builder.show();
        }
    }

    public void onStart() {
        super.onStart();
        UsageStatsProxy.a(this.mActivity, true).a(TAG);
        Log.v("UsageEvent", "onStart-" + TAG);
    }

    public void onStop() {
        super.onStop();
        Log.v("UsageEvent", "onStop-" + TAG);
        UsageStatsProxy.a(this.mActivity, true).b(TAG);
    }
}
