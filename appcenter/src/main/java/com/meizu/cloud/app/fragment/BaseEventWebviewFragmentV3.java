package com.meizu.cloud.app.fragment;

import android.content.Intent;
import android.webkit.JavascriptInterface;
import com.android.volley.n;
import com.android.volley.s;
import com.meizu.cloud.a.b;
import com.meizu.cloud.app.request.model.DownloadInfo;
import com.meizu.cloud.d.a;

public abstract class BaseEventWebviewFragmentV3 extends BaseEventWebviewFragmentV2 {
    protected static final int INVALIDE_TOKEN_COUNT = 3;
    protected static final int OAUTH_TOKEN = 2;
    private boolean mHasTokenRequest = false;
    private int mInvalideCount = 0;
    private b mTokenHelper;

    public void onDestroy() {
        super.onDestroy();
        if (this.mTokenHelper != null) {
            this.mTokenHelper.a();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 2:
                this.mTokenHelper.a(requestCode, resultCode, data);
                return;
            default:
                return;
        }
    }

    @JavascriptInterface
    public void payApp(boolean hasPaid, int appId, double price, String pkgName, int version_code) {
        final int i = appId;
        final String str = pkgName;
        final boolean z = hasPaid;
        final double d = price;
        final int i2 = version_code;
        runOnUi(new Runnable(this) {
            final /* synthetic */ BaseEventWebviewFragmentV3 f;

            public void run() {
                new a(this.f.mActivity, i, new com.meizu.cloud.d.b(this) {
                    final /* synthetic */ AnonymousClass1 a;

                    {
                        this.a = r1;
                    }

                    public void a(DownloadInfo downloadInfo) {
                        this.a.f.loadJavaScript(this.a.f.mEventJsInterface.a(i, str));
                    }

                    public void a(int errorCode, String errorMsg) {
                        this.a.f.loadJavaScript(this.a.f.mEventJsInterface.a(i, str, errorCode, errorMsg));
                    }
                }).a(z, i, d, str, i2);
            }
        });
    }

    @JavascriptInterface
    public boolean oauthRequest(String tag, String url, String paramsJsonObj) {
        return requestTokenForAouth(tag, url, paramsJsonObj, false);
    }

    private boolean requestTokenForAouth(final String tag, final String url, final String paramsJsonObj, boolean invalidToken) {
        if (this.mHasTokenRequest) {
            return false;
        }
        this.mHasTokenRequest = true;
        this.mTokenHelper = new b(this, 2, new com.meizu.cloud.a.a(this) {
            final /* synthetic */ BaseEventWebviewFragmentV3 d;

            public void a(String token, boolean isFromLogin) {
                if (this.d.getActivity() != null) {
                    this.d.loadJavaScript(this.d.mEventJsInterface.a(tag, isFromLogin));
                    if (!isFromLogin) {
                        this.d.postOauthRequest(tag, url, paramsJsonObj);
                    }
                    this.d.mHasTokenRequest = false;
                }
            }

            public void a(int errorCode) {
                this.d.loadJavaScript(this.d.mEventJsInterface.a(tag, errorCode));
                this.d.mHasTokenRequest = false;
            }
        });
        this.mTokenHelper.a(invalidToken);
        return true;
    }

    private void postOauthRequest(final String tag, final String url, final String paramsJsonObj) {
        this.mEventJsInterface.a(this.mActivity, url, paramsJsonObj, new n.b<String>(this) {
            final /* synthetic */ BaseEventWebviewFragmentV3 b;

            public void a(String response) {
                if (this.b.getActivity() != null) {
                    this.b.loadJavaScript(this.b.mEventJsInterface.a(tag, response));
                    this.b.mInvalideCount = 0;
                }
            }
        }, new n.a(this) {
            final /* synthetic */ BaseEventWebviewFragmentV3 d;

            public void a(s error) {
                if (this.d.getActivity() != null) {
                    if (!(error instanceof com.android.volley.a)) {
                        this.d.loadJavaScript(this.d.mEventJsInterface.b(tag, error.getClass().getSimpleName()));
                        this.d.mInvalideCount = 0;
                    } else if (this.d.mInvalideCount >= 3) {
                        this.d.loadJavaScript(this.d.mEventJsInterface.b(tag, error.getClass().getSimpleName()));
                    } else {
                        this.d.mInvalideCount = this.d.mInvalideCount + 1;
                        this.d.requestTokenForAouth(tag, url, paramsJsonObj, true);
                    }
                }
            }
        });
    }
}
