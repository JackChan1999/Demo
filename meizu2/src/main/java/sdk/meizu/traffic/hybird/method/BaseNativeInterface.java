package sdk.meizu.traffic.hybird.method;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import org.json.JSONObject;
import sdk.meizu.traffic.TrafficOrder;
import sdk.meizu.traffic.hybird.JsCmd;

public class BaseNativeInterface implements INativeInterface {
    private static final String TAG = BaseNativeInterface.class.getSimpleName();
    private JsCmd mAuthCallBack;
    private AuthHandler mAuthHandler;
    private JsCmd mContactCallback;
    private EventHandler mEventHandler = null;
    private ImHandler mImHandler = null;
    private LoadingHandler mLoadingHandler;
    private PayHandler mPayHandler = null;
    private PhoneHandler mPhoneHandler;
    private SystemBarHandler mSystemBarHandler;
    private ToastHandler mToastHandler;
    private Handler mUiHandler = new Handler(Looper.getMainLooper());

    public interface ImHandler {
        void closeInputMethod();

        void showInputMethod();
    }

    public interface LoadingHandler {
        void startLoading(String str);

        void stopLoading();
    }

    public interface ToastHandler {
        void toast(String str);

        void toastError(String str);
    }

    public interface AuthHandler {
        void getAuthToken(boolean z, JsCmd jsCmd);
    }

    public interface EventHandler {
        void usageEvent(String str, JSONObject jSONObject);
    }

    public interface PhoneHandler {
        void getAreaMISP(String str, JsCmd jsCmd);

        void getDefaultNum(JsCmd jsCmd);

        void getPackageName(JsCmd jsCmd);

        void getPhoneImei(JsCmd jsCmd);

        void openContacts(JsCmd jsCmd);

        void suggest(String str, JsCmd jsCmd);
    }

    public interface PayHandler {
        void doPayAction(TrafficOrder trafficOrder, JsCmd jsCmd);
    }

    public interface PageHandler {
        void finishModule(boolean z, String str);

        void finishPage(String str, String str2);

        void handleBackPressed(String str);

        void handleHomePressed(String str);

        void startPage(String str, String str2, JsCmd jsCmd);
    }

    public interface SystemBarHandler {
        void addMenuItem(String str, String str2, boolean z, JsCmd jsCmd);

        void handleMarginCallback(JsCmd jsCmd);

        void setTitle(String str);

        void updateMenuItem(String str, String str2, boolean z);
    }

    public void reset() {
        this.mToastHandler = null;
        this.mLoadingHandler = null;
        this.mAuthHandler = null;
        this.mSystemBarHandler = null;
        this.mPhoneHandler = null;
        this.mAuthCallBack = null;
        this.mUiHandler = null;
        this.mEventHandler = null;
        this.mPayHandler = null;
    }

    @NativeMethod
    public void log(String str) {
        Log.v(TAG, str);
    }

    @NativeMethod
    public void logError(@Parameter("error") String str) {
        Log.e(TAG, str);
    }

    public void setToastHandler(ToastHandler toastHandler) {
        this.mToastHandler = toastHandler;
    }

    @NativeMethod
    public void toast(@Parameter("toastString") final String str) {
        this.mUiHandler.post(new Runnable() {
            public void run() {
                if (BaseNativeInterface.this.mToastHandler != null) {
                    BaseNativeInterface.this.mToastHandler.toast(str);
                }
            }
        });
    }

    @NativeMethod
    public void toastError(@Parameter("toastString") final String str) {
        this.mUiHandler.post(new Runnable() {
            public void run() {
                if (BaseNativeInterface.this.mToastHandler != null) {
                    BaseNativeInterface.this.mToastHandler.toastError(str);
                }
            }
        });
    }

    public void setLoadingHandler(LoadingHandler loadingHandler) {
        this.mLoadingHandler = loadingHandler;
    }

    @NativeMethod
    public void startLoading(@Parameter("mes") final String str) {
        this.mUiHandler.post(new Runnable() {
            public void run() {
                if (BaseNativeInterface.this.mLoadingHandler != null) {
                    BaseNativeInterface.this.mLoadingHandler.startLoading(str);
                }
            }
        });
    }

    @NativeMethod
    public void stopLoading() {
        this.mUiHandler.post(new Runnable() {
            public void run() {
                if (BaseNativeInterface.this.mLoadingHandler != null) {
                    BaseNativeInterface.this.mLoadingHandler.stopLoading();
                }
            }
        });
    }

    @NativeMethod
    public void removePullRefresh() {
        this.mUiHandler.post(new Runnable() {
            public void run() {
                if (BaseNativeInterface.this.mLoadingHandler == null) {
                }
            }
        });
    }

    @NativeMethod
    public void addPullRefresh() {
        if (this.mLoadingHandler == null) {
        }
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.mEventHandler = eventHandler;
    }

    @NativeMethod
    public void usageEvent(@Parameter("eventName") String str, @Parameter("eventData") JSONObject jSONObject) {
        if (this.mEventHandler != null) {
            this.mEventHandler.usageEvent(str, jSONObject);
        }
    }

    public void setAuthHandler(AuthHandler authHandler) {
        this.mAuthHandler = authHandler;
    }

    @NativeMethod
    public void getToken(@Parameter("isForceUpdate") final String str, @CallBack String str2) {
        if (!TextUtils.isEmpty(str2)) {
            this.mAuthCallBack = JsCmd.from("").setMethod(str2);
            new Thread(new Runnable() {
                public void run() {
                    Looper.prepare();
                    if (BaseNativeInterface.this.mAuthHandler != null) {
                        BaseNativeInterface.this.mAuthHandler.getAuthToken(Boolean.valueOf(str).booleanValue(), BaseNativeInterface.this.mAuthCallBack);
                    } else {
                        Log.e(BaseNativeInterface.TAG, "getToken must have native handler");
                    }
                }
            }).start();
        }
    }

    public void handleAuthCallback(final WebView webView, final String str) {
        this.mUiHandler.post(new Runnable() {
            public void run() {
                if (BaseNativeInterface.this.mAuthCallBack != null) {
                    BaseNativeInterface.this.mAuthCallBack.setMethodArgs(str).execute(webView);
                }
            }
        });
    }

    public void setSystemBarHandler(SystemBarHandler systemBarHandler) {
        this.mSystemBarHandler = systemBarHandler;
    }

    @NativeMethod
    public void setTitle(@Parameter("title") final String str) {
        this.mUiHandler.post(new Runnable() {
            public void run() {
                if (BaseNativeInterface.this.mSystemBarHandler != null) {
                    BaseNativeInterface.this.mSystemBarHandler.setTitle(str);
                }
            }
        });
    }

    @NativeMethod
    public void addMenuItem(@Parameter("key") String str, @Parameter("text") String str2, @Parameter("enable") boolean z, @CallBack String str3) {
        final String str4 = str3;
        final String str5 = str;
        final String str6 = str2;
        final boolean z2 = z;
        this.mUiHandler.post(new Runnable() {
            public void run() {
                if (BaseNativeInterface.this.mSystemBarHandler != null) {
                    BaseNativeInterface.this.mSystemBarHandler.addMenuItem(str5, str6, z2, JsCmd.from("").setMethod(str4));
                }
            }
        });
    }

    @NativeMethod
    public void updateMenuItem(@Parameter("key") final String str, @Parameter("text") final String str2, @Parameter("enable") final boolean z) {
        this.mUiHandler.post(new Runnable() {
            public void run() {
                if (BaseNativeInterface.this.mSystemBarHandler != null) {
                    BaseNativeInterface.this.mSystemBarHandler.updateMenuItem(str, str2, z);
                }
            }
        });
    }

    public void setPhoneHandler(PhoneHandler phoneHandler) {
        this.mPhoneHandler = phoneHandler;
    }

    @NativeMethod
    public void getDefaultNumber(@CallBack String str) {
        if (this.mPhoneHandler != null) {
            this.mPhoneHandler.getDefaultNum(JsCmd.from("").setMethod(str));
        }
    }

    @NativeMethod
    public void getPhoneImei(@CallBack String str) {
        if (this.mPhoneHandler != null) {
            this.mPhoneHandler.getPhoneImei(JsCmd.from("").setMethod(str));
        }
    }

    @NativeMethod
    public void getPackageName(@CallBack String str) {
        if (this.mPhoneHandler != null) {
            this.mPhoneHandler.getPackageName(JsCmd.from("").setMethod(str));
        }
    }

    @NativeMethod
    public void getAreaMISP(@Parameter("phoneNum") String str, @Parameter("misp") String str2, @CallBack String str3) {
        if (this.mPhoneHandler != null) {
            this.mPhoneHandler.getAreaMISP(str, JsCmd.from("").setMethod(str3));
        }
    }

    @NativeMethod
    public void suggest(@Parameter("pNums") String str, @CallBack String str2) {
        if (this.mPhoneHandler != null) {
            this.mPhoneHandler.suggest(str, JsCmd.from("").setMethod(str2));
        }
    }

    @NativeMethod
    public void openContacts(@CallBack String str) {
        if (this.mPhoneHandler != null) {
            this.mPhoneHandler.openContacts(JsCmd.from("").setMethod(str));
        }
    }

    public void setPayHandler(PayHandler payHandler) {
        this.mPayHandler = payHandler;
    }

    @NativeMethod
    public void openBuyComp(@Parameter("order") JSONObject jSONObject, @CallBack String str) {
        if (this.mPayHandler != null) {
            this.mPayHandler.doPayAction(new TrafficOrder(jSONObject), JsCmd.from("").setMethod(str));
        }
    }

    public void setImHandler(ImHandler imHandler) {
        this.mImHandler = imHandler;
    }

    @NativeMethod
    public void showInputMethod() {
        if (this.mImHandler != null) {
            this.mImHandler.showInputMethod();
        }
    }

    @NativeMethod
    public void closeInputMethod() {
        if (this.mImHandler != null) {
            this.mImHandler.closeInputMethod();
        }
    }

    public JsToAndroidBridge getJsToAndroidBridge() {
        return new JsToAndroidBridge(this);
    }
}
