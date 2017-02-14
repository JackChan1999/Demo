package com.meizu.cloud.d;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.l;
import com.android.volley.n;
import com.android.volley.s;
import com.android.volley.toolbox.j;
import com.meizu.a.a.d;
import com.meizu.cloud.a.b;
import com.meizu.cloud.a.c;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.request.FastJsonRequest;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.RequestManager;
import com.meizu.cloud.app.request.model.DownloadInfo;
import com.meizu.cloud.app.request.model.OrderResultModel.HadPay;
import com.meizu.cloud.app.request.model.OrderResultModel.NoPay;
import com.meizu.cloud.app.request.model.OrderResultModel.Receipt;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.utils.g;
import com.meizu.cloud.app.utils.k;
import com.meizu.cloud.app.utils.t;
import com.meizu.cloud.b.a.i;
import com.meizu.common.app.SlideNotice;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class a implements Callback {
    private static final String a = a.class.getSimpleName();
    private FragmentActivity b;
    private a c;
    private b d;
    private int e;
    private ProgressDialog f;
    private Handler g;
    private Thread h;
    private int i = -1;
    private int j = -1;
    private int k = Integer.MAX_VALUE;
    private String l;
    private String m;
    private int n = 0;

    private class a {
        public boolean a;
        public int b;
        public double c;
        public String d;
        public int e;
        final /* synthetic */ a f;

        private a(a aVar) {
            this.f = aVar;
        }

        public void a() {
            this.a = false;
            this.b = 0;
            this.c = 0.0d;
            this.d = null;
            this.e = 0;
        }
    }

    public boolean handleMessage(Message message) {
        switch (message.what) {
            case 10:
                if (this.b == null || this.b.isFinishing() || this.f.isShowing()) {
                    return true;
                }
                this.f.show();
                return true;
            case 11:
                if (this.b == null || this.b.isFinishing() || !this.f.isShowing()) {
                    return true;
                }
                this.f.cancel();
                return true;
            default:
                return false;
        }
    }

    public a(FragmentActivity activity, int requestCode, b payListener) {
        this.b = activity;
        this.d = payListener;
        this.e = requestCode;
        c();
        this.g = new Handler(Looper.getMainLooper(), this);
        this.c = new a();
    }

    public void a(int btnStyle, int editTextStyle) {
        this.i = btnStyle;
        this.j = editTextStyle;
    }

    public synchronized void a(boolean hadPaid, int appId, double prices, String packageName, int versionCode) {
        this.c.a = hadPaid;
        this.c.b = appId;
        this.c.c = prices;
        this.c.d = packageName;
        this.c.e = versionCode;
        if (this.f == null || !this.f.isShowing()) {
            this.g.sendEmptyMessage(10);
            if (this.h == null || !this.h.isAlive()) {
                this.h = new Thread(new Runnable(this) {
                    final /* synthetic */ a a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        boolean z = true;
                        if (this.a.c.a) {
                            this.a.b(null, null);
                            return;
                        }
                        int authResult = this.a.a(RequestConstants.getRuntimeDomainUrl(this.a.b, RequestConstants.CHECK_DEVICE_QUALIFICATION), this.a.b(this.a.c.b));
                        k.d(this.a.b.getApplicationContext(), a.a, "device qualification:" + authResult);
                        if (authResult != -1) {
                            a a = this.a.c;
                            if (authResult != 1) {
                                z = false;
                            }
                            a.a = z;
                            if (this.a.c.a) {
                                this.a.b(null, null);
                                return;
                            } else {
                                this.a.b();
                                return;
                            }
                        }
                        this.a.g.sendEmptyMessage(11);
                        this.a.a(200, this.a.b.getString(i.access_server_error));
                    }
                });
                this.h.start();
            }
        }
    }

    private void b() {
        try {
            final String token = b.b(this.b, false);
            if (!TextUtils.isEmpty(token)) {
                this.l = c.a(this.b);
                if (TextUtils.isEmpty(this.l)) {
                    this.l = c.b(this.b);
                }
                this.k = a(RequestConstants.getRuntimeDomainUrl(this.b, RequestConstants.CHECK_USER_QUALIFICATION), a(this.c.b, this.l, a(this.l)));
                k.d(this.b.getApplicationContext(), a, "user qualification:" + this.k);
                switch (this.k) {
                    case -1:
                        a(100, this.b.getString(i.system_pay_can_not_use));
                        return;
                    case 0:
                        String str;
                        NoPay noPay = null;
                        try {
                            noPay = a(this.l, token);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        final com.meizu.a.a.c order = a(noPay);
                        Context applicationContext = this.b.getApplicationContext();
                        String str2 = a;
                        if (order == null) {
                            str = "create order failure";
                        } else {
                            str = "create order success : " + order.d();
                        }
                        k.d(applicationContext, str2, str);
                        this.g.sendEmptyMessage(11);
                        if (order != null) {
                            this.g.post(new Runnable(this) {
                                final /* synthetic */ a c;

                                public void run() {
                                    com.meizu.a.a.b.a(this.c.b, order, new d(this) {
                                        final /* synthetic */ AnonymousClass6 a;

                                        {
                                            this.a = r1;
                                        }

                                        public void a(int code, com.meizu.a.a.c order, String errorMsg) {
                                            k.d(this.a.c.b.getApplicationContext(), a.a, "pay result code:" + code);
                                            switch (code) {
                                                case 0:
                                                    if (this.a.c.k == 1) {
                                                        this.a.c.b(this.a.c.l, token);
                                                        return;
                                                    } else if (this.a.c.k == 0 && order != null) {
                                                        this.a.c.b(order.d());
                                                        return;
                                                    } else {
                                                        return;
                                                    }
                                                case 2:
                                                    this.a.c.a((int) SlideNotice.SHOW_ANIMATION_DURATION, "");
                                                    return;
                                                default:
                                                    this.a.c.a(100, errorMsg);
                                                    return;
                                            }
                                        }
                                    });
                                }
                            });
                            return;
                        } else {
                            a((int) SlideNotice.SHOW_ANIMATION_DURATION, this.b.getString(i.create_order_failure));
                            return;
                        }
                    case 1:
                        b(this.l, token);
                        return;
                    default:
                        a((int) SlideNotice.SHOW_ANIMATION_DURATION, this.b.getString(i.unknow_error_type));
                        return;
                }
            }
        } catch (final com.meizu.c.c e2) {
            a((int) SlideNotice.SHOW_ANIMATION_DURATION, "");
            this.g.post(new Runnable(this) {
                final /* synthetic */ a b;

                public void run() {
                    this.b.g.sendEmptyMessage(11);
                    this.b.b.startActivity(e2.a);
                }
            });
        } catch (com.meizu.c.b e3) {
            a((int) SlideNotice.SHOW_ANIMATION_DURATION, "");
            a(e3.a);
        }
    }

    protected void a(final int errorCode) {
        this.g.post(new Runnable(this) {
            final /* synthetic */ a b;

            public void run() {
                if (errorCode == 1) {
                    t.a(this.b.b, this.b.b.getString(i.access_account_info_error), 0, 0);
                } else if (errorCode != 4) {
                    t.a(this.b.b, this.b.b.getString(i.access_account_info_out_date), 0, 0);
                }
            }
        });
    }

    private void c() {
        this.f = new ProgressDialog(this.b);
        this.f.setMessage(this.b.getString(i.please_wait));
        this.f.setCancelable(false);
    }

    private synchronized com.meizu.a.a.c a(NoPay<Receipt> response) {
        com.meizu.a.a.c outTradeOrderInfo;
        if (response != null) {
            Receipt receipt = response.receipt;
            if (receipt != null) {
                outTradeOrderInfo = new com.meizu.a.a.c();
                outTradeOrderInfo.e(receipt.notify_url).d(receipt.out_trade_no).a(receipt.partner).f(receipt.sign).g(receipt.sign_type).b(receipt.subject).c(receipt.total_fee).h(receipt.pay_accounts);
            } else {
                k.d(this.b.getApplicationContext(), a, "nopay receipt null");
                outTradeOrderInfo = null;
            }
        } else {
            k.d(this.b.getApplicationContext(), a, "nopay null");
            outTradeOrderInfo = null;
        }
        return outTradeOrderInfo;
    }

    private synchronized NoPay<Receipt> a(String account, String token) throws Exception {
        NoPay<Receipt> noPay;
        j<ResultModel<NoPay<Receipt>>> requestFuture = j.a();
        l noPayFastJsonRequest = new FastJsonRequest(new TypeReference<ResultModel<NoPay<Receipt>>>(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }
        }, RequestConstants.getRuntimeDomainUrl(this.b, RequestConstants.CREATE_ORDER), a(this.c.b, account, token, a(account)), requestFuture, requestFuture);
        noPayFastJsonRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.b));
        requestFuture.a(com.meizu.volley.b.a(this.b).a().a(noPayFastJsonRequest));
        ResultModel<NoPay<Receipt>> resultModel = (ResultModel) requestFuture.get();
        if (resultModel == null || resultModel.getCode() != 200 || resultModel.getValue() == null) {
            noPay = null;
        } else {
            noPay = (NoPay) resultModel.getValue();
        }
        return noPay;
    }

    private synchronized void b(String account, String token) {
        List orderDownloadParamPairs = b(this.c.b);
        if (!TextUtils.isEmpty(account)) {
            orderDownloadParamPairs = a(this.c.b, account, a(account));
        }
        if (!TextUtils.isEmpty(token)) {
            orderDownloadParamPairs = a(this.c.b, account, token, a(account));
        }
        a(orderDownloadParamPairs, this.c.d);
    }

    private synchronized void a(List<com.meizu.volley.b.a> orderDownloadParamPairs, final String packageName) {
        l hadPayAuthJsonRequest = new FastJsonRequest(new TypeReference<ResultModel<HadPay>>(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }
        }, RequestConstants.getRuntimeDomainUrl(this.b, RequestConstants.CREATE_ORDER), orderDownloadParamPairs, new n.b<ResultModel<HadPay>>(this) {
            final /* synthetic */ a b;

            public void a(ResultModel<HadPay> response) {
                if (response == null) {
                    this.b.a(100, this.b.b.getString(i.system_pay_can_not_use));
                    this.b.g.sendEmptyMessage(11);
                    return;
                }
                if (response.getCode() != 200 || response.getValue() == null) {
                    this.b.a(response.getCode(), response.getMessage());
                } else {
                    if (!TextUtils.isEmpty(((HadPay) response.getValue()).license)) {
                        this.b.a((ResultModel) response, packageName);
                    }
                    if (TextUtils.isEmpty(((HadPay) response.getValue()).download_url)) {
                        this.b.a(200, this.b.b.getString(i.get_download_url_failed));
                    } else {
                        this.b.a(this.b.a((ResultModel) response));
                    }
                }
                this.b.g.sendEmptyMessage(11);
            }
        }, new com.android.volley.n.a(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public void a(s error) {
                if (error.a != null) {
                    this.a.a(error.a.a, error.getMessage());
                } else {
                    this.a.a(-1, error.getMessage());
                }
                this.a.g.sendEmptyMessage(11);
            }
        });
        hadPayAuthJsonRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.b));
        com.meizu.volley.b.a(this.b).a().a(hadPayAuthJsonRequest);
    }

    private DownloadInfo a(ResultModel<HadPay> response) {
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.download_url = g.b(((HadPay) response.getValue()).download_url);
        downloadInfo.digest = ((HadPay) response.getValue()).digest;
        downloadInfo.verify_mode = ((HadPay) response.getValue()).verify_mode;
        downloadInfo.package_name = ((HadPay) response.getValue()).package_name;
        downloadInfo.size = ((HadPay) response.getValue()).size;
        downloadInfo.version_code = ((HadPay) response.getValue()).version_code;
        downloadInfo.download_urls = ((HadPay) response.getValue()).download_urls;
        return downloadInfo;
    }

    private synchronized void b(final String orderNumber) {
        if (this.n < 5 && !this.f.isShowing()) {
            this.g.sendEmptyMessage(10);
        }
        TypeReference typeReference = new TypeReference<ResultModel<HadPay>>(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }
        };
        List orderDownloadParamPairs = new ArrayList();
        orderDownloadParamPairs.add(new com.meizu.volley.b.a("order_number", orderNumber));
        orderDownloadParamPairs.add(new com.meizu.volley.b.a("package_name", this.c.d));
        orderDownloadParamPairs.add(new com.meizu.volley.b.a("version_code", String.valueOf(this.c.e)));
        l jsonRequest = new FastJsonRequest(typeReference, 0, RequestConstants.getRuntimeDomainUrl(this.b, RequestConstants.CHECK_ORDER), orderDownloadParamPairs, new n.b<ResultModel<HadPay>>(this) {
            final /* synthetic */ a b;

            public void a(final ResultModel<HadPay> response) {
                if (response == null) {
                    this.b.a(100, this.b.b.getString(i.system_pay_can_not_use));
                    this.b.g.sendEmptyMessage(11);
                } else if (response.getCode() != 200 || response.getValue() == null) {
                    new Timer().schedule(new TimerTask(this) {
                        final /* synthetic */ AnonymousClass2 b;

                        public void run() {
                            if (this.b.b.n < 5) {
                                com.meizu.cloud.app.utils.j.c(a.a, "try count:" + this.b.b.n);
                                k.d(this.b.b.b.getApplicationContext(), a.a, "check PayResult try count:" + this.b.b.n);
                                this.b.b.b(orderNumber);
                            } else {
                                this.b.b.g.sendEmptyMessage(11);
                                this.b.b.a(response.getCode(), response.getMessage());
                            }
                            this.b.b.n = this.b.b.n + 1;
                        }
                    }, 1000);
                } else {
                    this.b.a((ResultModel) response, this.b.c.d);
                    this.b.a(this.b.a((ResultModel) response));
                    this.b.g.sendEmptyMessage(11);
                }
            }
        }, new com.android.volley.n.a(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public void a(s error) {
                if (error.a != null) {
                    this.a.a(error.a.a, error.getMessage());
                } else {
                    this.a.a(-1, error.getMessage());
                }
                this.a.g.sendEmptyMessage(11);
            }
        });
        jsonRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.b));
        com.meizu.volley.b.a(this.b).a().a(jsonRequest);
    }

    private synchronized int a(String url, List<com.meizu.volley.b.a> paramPairs) {
        int i;
        n.b requestFuture = j.a();
        l fastJsonRequest = new FastJsonRequest(new TypeReference<ResultModel<JSONObject>>(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }
        }, 0, url, (List) paramPairs, requestFuture, (com.android.volley.n.a) requestFuture);
        fastJsonRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.b));
        requestFuture.a(com.meizu.volley.b.a(this.b).a().a(fastJsonRequest));
        try {
            ResultModel<JSONObject> result = (ResultModel) requestFuture.get();
            if (result == null || result.getCode() != 200 || result.getValue() == null) {
                i = -1;
            } else {
                JSONObject value = (JSONObject) result.getValue();
                if (value.containsKey("status")) {
                    i = value.getBoolean("status").booleanValue() ? 1 : 0;
                }
                i = -1;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e2) {
            e2.printStackTrace();
        }
        return i;
    }

    private List<com.meizu.volley.b.a> a(int appId, String account, String token, int accountType) {
        List<com.meizu.volley.b.a> paramPairs = a(appId, account, accountType);
        paramPairs.add(new com.meizu.volley.b.a("token", token));
        return paramPairs;
    }

    private List<com.meizu.volley.b.a> a(int appId, String account, int accountType) {
        List<com.meizu.volley.b.a> paramPairs = b(appId);
        paramPairs.add(new com.meizu.volley.b.a("flyme", account));
        paramPairs.add(new com.meizu.volley.b.a("account_type", String.valueOf(accountType)));
        return paramPairs;
    }

    private List<com.meizu.volley.b.a> b(int appId) {
        List<com.meizu.volley.b.a> paramPairs = d();
        if (x.b(this.b)) {
            paramPairs.add(new com.meizu.volley.b.a("game_id", String.valueOf(appId)));
        } else {
            paramPairs.add(new com.meizu.volley.b.a("app_id", String.valueOf(appId)));
        }
        return paramPairs;
    }

    private List<com.meizu.volley.b.a> d() {
        List<com.meizu.volley.b.a> paramPairs = new ArrayList();
        paramPairs.add(new com.meizu.volley.b.a(RequestManager.IMEI, com.meizu.cloud.app.utils.d.a(this.b)));
        paramPairs.add(new com.meizu.volley.b.a(RequestManager.SN, com.meizu.cloud.app.utils.d.b(this.b)));
        return paramPairs;
    }

    private synchronized void a(ResultModel<HadPay> response, String packageName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("PACKAGE_NAME", packageName);
        contentValues.put("LICENSE_DATA", ((HadPay) response.getValue()).license);
        this.b.getContentResolver().insert(Uri.parse("content://com.meizu.flyme.appcenter.licenseprovider/license"), contentValues);
    }

    public int a(String account) {
        if (g.c(account)) {
            return 1;
        }
        return 0;
    }

    private void e() {
        this.c.a();
        this.l = null;
        this.m = null;
        this.n = 0;
    }

    private void a(DownloadInfo downloadInfo) {
        this.d.a(downloadInfo);
        e();
    }

    private void a(final int errorCode, final String errorMsg) {
        this.g.post(new Runnable(this) {
            final /* synthetic */ a c;

            public void run() {
                this.c.g.sendEmptyMessage(11);
                if (errorCode != SlideNotice.SHOW_ANIMATION_DURATION) {
                    t.a(this.c.b, errorMsg, 1, 0);
                }
                this.c.d.a(errorCode, errorMsg);
                this.c.e();
            }
        });
    }
}
