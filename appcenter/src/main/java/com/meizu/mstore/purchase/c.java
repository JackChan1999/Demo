package com.meizu.mstore.purchase;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.l;
import com.android.volley.s;
import com.meizu.a.a.d;
import com.meizu.cloud.app.core.i;
import com.meizu.cloud.app.request.FastJsonRequest;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.OrderResultModel.ProductHadPay;
import com.meizu.cloud.app.request.model.OrderResultModel.ProductNoPay;
import com.meizu.cloud.app.request.model.OrderResultModel.Receipt;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.utils.j;
import com.meizu.mstore.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

@Deprecated
public class c extends a {
    private b g;
    private Object h;
    private Thread i;
    private com.android.volley.n.a j = new com.android.volley.n.a(this) {
        final /* synthetic */ c a;

        {
            this.a = r1;
        }

        public void a(s error) {
            String msg = "";
            if (error.a == null) {
                msg = this.a.c.getString(R.string.lack_order_info);
            } else {
                msg = this.a.c.getString(R.string.access_server_error);
            }
            this.a.a(-11, msg);
        }
    };

    private class a extends Handler {
        final /* synthetic */ c a;

        private a(c cVar) {
            this.a = cVar;
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    if (!this.a.a.isShowing()) {
                        this.a.a.show();
                        break;
                    }
                    break;
                case 3:
                    if (this.a.a.isShowing()) {
                        this.a.a.cancel();
                        break;
                    }
                    break;
                case 10:
                    this.a.a(this.a.g.i, this.a.g.b);
                    break;
                case 13:
                    this.a.c.finish();
                    break;
            }
            super.handleMessage(msg);
        }
    }

    private class b {
        public String a;
        public String b;
        public int c;
        public String d;
        public String e;
        public String f;
        public int g;
        public int h;
        public String i;
        public int j;
        public String k;
        final /* synthetic */ c l;

        private b(c cVar) {
            this.l = cVar;
        }

        public void a() {
            this.a = null;
            this.b = null;
            this.c = 0;
            this.d = null;
            this.e = null;
            this.f = null;
            this.g = 0;
            this.h = 0;
            this.i = null;
            this.j = 0;
            this.k = null;
        }
    }

    public void a(Activity activity, Bundle bundle) {
        this.c = activity;
        b();
        this.g = new b();
        this.g.i = bundle.getString("package_name");
        this.g.b = bundle.getString("product_numbers");
        this.g.c = bundle.getInt("quantity");
        this.g.e = bundle.getString("sp_order_number", "");
        this.e = new a();
        this.d = new Messenger(this.e);
    }

    private void d() {
        if (this.i == null || !this.i.isAlive()) {
            this.i = new Thread(new Runnable(this) {
                final /* synthetic */ c a;

                {
                    this.a = r1;
                }

                public void run() {
                    try {
                        String token = com.meizu.cloud.a.b.b(this.a.c, false);
                        try {
                            this.a.h = this.a.c();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (this.a.h instanceof ProductNoPay) {
                            com.meizu.a.a.c orderInfo = this.a.a((ProductNoPay) this.a.h);
                            if (orderInfo != null) {
                                this.a.e.sendEmptyMessage(3);
                                com.meizu.a.a.b.a(this.a.c, orderInfo, new d(this) {
                                    final /* synthetic */ AnonymousClass1 a;

                                    {
                                        this.a = r1;
                                    }

                                    public void a(int code, com.meizu.a.a.c order, String errorMsg) {
                                        switch (code) {
                                            case 0:
                                                if (this.a.a.h instanceof ProductHadPay) {
                                                    this.a.a.a((ProductHadPay) this.a.a.h);
                                                    return;
                                                } else if (!(this.a.a.h instanceof ProductNoPay)) {
                                                    this.a.a.a(-120, null);
                                                    return;
                                                } else if (order != null) {
                                                    this.a.a.e.sendEmptyMessage(2);
                                                    this.a.a.a(order.d());
                                                    return;
                                                } else {
                                                    return;
                                                }
                                            default:
                                                this.a.a.a(-120, null);
                                                return;
                                        }
                                    }
                                });
                                return;
                            }
                            this.a.a(-120, this.a.c.getString(R.string.create_order_failure));
                        } else if (this.a.h instanceof ProductHadPay) {
                            this.a.a((ProductHadPay) this.a.h);
                        } else {
                            this.a.a(-120, this.a.c.getString(R.string.system_pay_can_not_use));
                        }
                    } catch (final com.meizu.c.c e2) {
                        this.a.a(-120, "");
                        this.a.e.post(new Runnable(this) {
                            final /* synthetic */ AnonymousClass1 b;

                            public void run() {
                                this.b.a.c.startActivity(e2.a);
                            }
                        });
                    } catch (com.meizu.c.b e3) {
                        this.a.a(-120, "");
                        this.a.a(e3.a);
                    }
                }
            });
            this.i.start();
        }
    }

    protected void a(final int errorCode) {
        this.e.post(new Runnable(this) {
            final /* synthetic */ c b;

            public void run() {
                if (errorCode == 1) {
                    com.meizu.cloud.app.utils.a.a(this.b.c, this.b.c.getString(R.string.access_account_info_error));
                } else if (errorCode != 4) {
                    com.meizu.cloud.app.utils.a.a(this.b.c, this.b.c.getString(R.string.access_account_info_out_date));
                }
            }
        });
    }

    public void a(String packageName, String productNum) {
        this.e.sendEmptyMessage(2);
        TypeReference typeReference = new TypeReference<ResultModel<ProductResultModel>>(this) {
            final /* synthetic */ c a;

            {
                this.a = r1;
            }
        };
        List paramPairs = new ArrayList();
        paramPairs.add(new com.meizu.volley.b.a("package_name", packageName));
        paramPairs.add(new com.meizu.volley.b.a("product_numbers", productNum));
        l jsonRequest = new FastJsonRequest(typeReference, 0, RequestConstants.getRuntimeDomainUrl(this.c, RequestConstants.GET_APP_PRODUCT), paramPairs, new com.android.volley.n.b<ResultModel<ProductResultModel>>(this) {
            final /* synthetic */ c a;

            {
                this.a = r1;
            }

            public void a(ResultModel<ProductResultModel> response) {
                if (response == null || response.getCode() != 200 || response.getValue() == null) {
                    this.a.a(-10, this.a.c.getString(R.string.get_product_info_error));
                    return;
                }
                AppSubProduct subProduct = (AppSubProduct) ((ProductResultModel) response.getValue()).products.get(0);
                if (subProduct != null) {
                    this.a.g.k = subProduct.name;
                    this.a.g.d = subProduct.description;
                    this.a.g.h = subProduct.price;
                }
                this.a.d();
            }
        }, new com.android.volley.n.a(this) {
            final /* synthetic */ c a;

            {
                this.a = r1;
            }

            public void a(s error) {
                this.a.a(-10, this.a.c.getString(R.string.get_product_info_error));
            }
        });
        jsonRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.c));
        com.meizu.volley.b.a(this.c).a().a(jsonRequest);
    }

    public void a(final String orderNumber) {
        TypeReference<ResultModel<ProductHadPay>> reference = new TypeReference<ResultModel<ProductHadPay>>(this) {
            final /* synthetic */ c a;

            {
                this.a = r1;
            }
        };
        List<com.meizu.volley.b.a> paramPairs = new ArrayList();
        paramPairs.add(new com.meizu.volley.b.a("order_number", orderNumber));
        paramPairs.add(new com.meizu.volley.b.a("package_name", this.g.i));
        paramPairs.add(new com.meizu.volley.b.a("version_code", String.valueOf(this.g.j)));
        l jsonRequest = new FastJsonRequest(reference, RequestConstants.getRuntimeDomainUrl(this.c, RequestConstants.CHECK_PRODUCT_ORDER), paramPairs, new com.android.volley.n.b<ResultModel<ProductHadPay>>(this) {
            final /* synthetic */ c b;

            public void a(ResultModel<ProductHadPay> response) {
                if (response == null) {
                    this.b.a(-120, this.b.c.getString(R.string.system_pay_can_not_use));
                }
                if (response.getCode() != 200 || response.getValue() == null) {
                    final Timer timer = new Timer();
                    timer.schedule(new TimerTask(this) {
                        final /* synthetic */ AnonymousClass7 b;

                        public void run() {
                            if (this.b.b.f < 5) {
                                c cVar = this.b.b;
                                cVar.f++;
                                j.c(c.class.getSimpleName(), "try count:" + this.b.b.f);
                                this.b.b.a(orderNumber);
                                return;
                            }
                            timer.purge();
                            timer.cancel();
                        }
                    }, 1000);
                    if (this.b.f >= 5) {
                        this.b.a(-120, this.b.c.getString(R.string.null_order_info));
                        return;
                    }
                    return;
                }
                ProductHadPay productHadPay = (ProductHadPay) response.getValue();
                if (productHadPay.license != null) {
                    this.b.g.g = productHadPay.status;
                    this.b.a(productHadPay);
                }
            }
        }, this.j);
        jsonRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.c));
        com.meizu.volley.b.a(this.c).a().a(jsonRequest);
    }

    public Object c() throws ExecutionException, InterruptedException {
        int versionCode = i.f(this.c, this.g.i);
        this.g.j = versionCode;
        com.android.volley.toolbox.j<ResultModel<String>> requestFuture = com.android.volley.toolbox.j.a();
        TypeReference<ResultModel<String>> typeReference = new TypeReference<ResultModel<String>>(this) {
            final /* synthetic */ c a;

            {
                this.a = r1;
            }
        };
        List<com.meizu.volley.b.a> paramPairs = new ArrayList();
        paramPairs.add(new com.meizu.volley.b.a("product_number", this.g.b));
        paramPairs.add(new com.meizu.volley.b.a("quantity", String.valueOf(this.g.c)));
        paramPairs.add(new com.meizu.volley.b.a("package_name", this.g.i));
        paramPairs.add(new com.meizu.volley.b.a("version_code", String.valueOf(versionCode)));
        paramPairs.add(new com.meizu.volley.b.a("sp_order_number", this.g.e));
        l jsonRequest = new FastJsonRequest(typeReference, RequestConstants.getRuntimeDomainUrl(this.c, RequestConstants.CREATE_APP_PRODUCT_ORDER), paramPairs, requestFuture, requestFuture);
        jsonRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.c));
        requestFuture.a(com.meizu.volley.b.a(this.c).a().a(jsonRequest));
        ResultModel<String> reslut = (ResultModel) requestFuture.get();
        if (reslut == null || reslut.getCode() != 200 || reslut.getValue() == null) {
            return null;
        }
        ProductNoPay productNoPay = (ProductNoPay) JSON.parseObject((String) reslut.getValue(), ProductNoPay.class);
        if (productNoPay.receipt == null) {
            ProductHadPay productHadPay = (ProductHadPay) JSON.parseObject((String) reslut.getValue(), ProductHadPay.class);
            if (productHadPay.license == null) {
                return null;
            }
            this.g.g = productHadPay.status;
            return productHadPay;
        }
        this.g.g = productNoPay.status;
        this.g.f = productNoPay.receipt.out_trade_no;
        return productNoPay;
    }

    private synchronized void a(int what, String msg) {
        this.e.sendEmptyMessage(3);
        Message message = Message.obtain();
        Bundle data = e();
        message.what = what;
        data.putInt("ResultCode", -1);
        data.putString("InfoReceipt", "");
        String messageInfo = "";
        if (TextUtils.isEmpty(msg)) {
            messageInfo = this.c.getString(R.string.create_order_failure);
        } else {
            messageInfo = msg;
        }
        data.putString("ResultInfo", messageInfo);
        message.setData(data);
        try {
            this.b.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        f();
        this.e.sendEmptyMessageDelayed(13, 100);
    }

    private synchronized void a(ProductHadPay productHadPay) {
        this.e.sendEmptyMessage(3);
        Message message = Message.obtain();
        message.what = 120;
        Bundle data = e();
        data.putInt("ResultCode", 1);
        data.putString("ResultInfo", productHadPay.license);
        data.putString("InfoReceipt", "");
        message.setData(data);
        try {
            this.b.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        f();
        this.e.sendEmptyMessageDelayed(13, 100);
    }

    private synchronized com.meizu.a.a.c a(ProductNoPay response) {
        com.meizu.a.a.c outTradeOrderInfo;
        Receipt receipt = response.receipt;
        if (receipt != null) {
            outTradeOrderInfo = new com.meizu.a.a.c();
            outTradeOrderInfo.e(receipt.notify_url).d(receipt.out_trade_no).a(receipt.partner).f(receipt.sign).g(receipt.sign_type).b(receipt.subject).c(receipt.total_fee).h(receipt.pay_accounts);
        } else {
            outTradeOrderInfo = null;
        }
        return outTradeOrderInfo;
    }

    private Bundle e() {
        Bundle bundle = new Bundle();
        bundle.putString("product_id", this.g.a);
        bundle.putString("product_number", this.g.b);
        bundle.putString("package_name", this.g.i);
        bundle.putString("order_number", this.g.f);
        bundle.putInt("status", this.g.g);
        return bundle;
    }

    private void f() {
        this.g.a();
        this.f = 0;
    }
}
