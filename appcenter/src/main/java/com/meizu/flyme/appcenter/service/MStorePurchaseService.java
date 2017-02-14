package com.meizu.flyme.appcenter.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.l;
import com.android.volley.s;
import com.meizu.cloud.app.request.FastJsonRequest;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.base.app.BaseApplication;
import com.meizu.common.IAppProduct;
import com.meizu.common.IProductResponse;
import com.meizu.mstore.purchase.AppSubProduct;
import com.meizu.mstore.purchase.ProductResultModel;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class MStorePurchaseService extends Service {
    private com.meizu.mstore.purchase.b a;
    private ArrayList<a> b;
    private Messenger c = new Messenger(new b());
    private Messenger d = null;
    private ServiceConnection e = new ServiceConnection(this) {
        final /* synthetic */ MStorePurchaseService a;

        {
            this.a = r1;
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            this.a.d = new Messenger(iBinder);
            Message message = Message.obtain();
            message.what = 11;
            message.replyTo = this.a.c;
            try {
                this.a.d.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            this.a.d = null;
        }
    };
    private final com.meizu.d.a.a f = new com.meizu.d.a.a(this) {
        final /* synthetic */ MStorePurchaseService a;

        {
            this.a = r1;
        }

        public void a(String productIdentifier, int quantity, String appIdentifier) throws RemoteException {
            this.a.a(productIdentifier, quantity, appIdentifier, "");
        }

        public IProductResponse a(String[] identifiers, String appIdentifier) throws RemoteException {
            if (identifiers.length == 0 || appIdentifier == null || appIdentifier.length() == 0) {
                return null;
            }
            return this.a.a(appIdentifier, identifiers);
        }

        public void a(String packageName, com.meizu.d.b cb) throws RemoteException {
            int i = 0;
            while (i < this.a.b.size()) {
                if (!((a) this.a.b.get(i)).a.equals(packageName) || ((a) this.a.b.get(i)).b.asBinder() != cb.asBinder()) {
                    i++;
                } else {
                    return;
                }
            }
            this.a.b.add(new a(this.a, packageName, cb));
        }

        public boolean a(String orderNum) throws RemoteException {
            return true;
        }

        public void b(String packageName, com.meizu.d.b cb) throws RemoteException {
            int i = 0;
            while (i < this.a.b.size()) {
                if (((a) this.a.b.get(i)).a.equals(packageName) && ((a) this.a.b.get(i)).b.asBinder() == cb.asBinder()) {
                    this.a.b.remove(i);
                    return;
                }
                i++;
            }
        }

        public void a(String productIdentifier, int quantity, String appIdentifier, String extraOrderInfo) throws RemoteException {
            this.a.a(productIdentifier, quantity, appIdentifier, extraOrderInfo);
        }
    };
    private boolean g = true;

    private final class a {
        String a;
        com.meizu.d.b b;
        final /* synthetic */ MStorePurchaseService c;

        a(MStorePurchaseService mStorePurchaseService, String packageName, com.meizu.d.b cb) {
            this.c = mStorePurchaseService;
            this.a = packageName;
            this.b = cb;
        }
    }

    private class b extends Handler {
        final /* synthetic */ MStorePurchaseService a;

        private b(MStorePurchaseService mStorePurchaseService) {
            this.a = mStorePurchaseService;
        }

        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            switch (msg.what) {
                case -120:
                case -10:
                case 23:
                    this.a.a(-1, data.getString("ResultInfo"), data.getString("package_name"), data.getString("product_number"), data.getString("order_number"));
                    break;
                case 120:
                    this.a.a(data.getString("package_name"), data.getString("ResultInfo"));
                    this.a.a(1, data.getString("InfoReceipt"), data.getString("ResultInfo"), data.getString("package_name"), data.getString("product_numbers"), data.getString("order_number"));
                    break;
            }
            super.handleMessage(msg);
        }
    }

    private void a(String packageName, String license) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("PACKAGE_NAME", packageName);
        contentValues.put("LICENSE_DATA", license);
        getApplicationContext().getContentResolver().insert(Uri.parse("content://com.meizu.flyme.appcenter.licenseprovider/license"), contentValues);
    }

    public void onCreate() {
        super.onCreate();
        BaseApplication.a((Context) this);
        this.b = new ArrayList();
        this.a = new com.meizu.mstore.purchase.b(this);
        bindService(new Intent(this, PurchaseProxyService.class), this.e, 1);
    }

    public void onDestroy() {
        super.onDestroy();
        unbindService(this.e);
    }

    public IBinder onBind(Intent intent) {
        return this.f;
    }

    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    private void a(String productIdentifier, int quantity, String appIdentifier, String extraOrderInfo) throws RemoteException {
        Bundle simpleDate = new Bundle();
        simpleDate.putString("package_name", appIdentifier);
        simpleDate.putString("product_numbers", productIdentifier);
        simpleDate.putInt("quantity", quantity);
        simpleDate.putString("sp_order_number", extraOrderInfo);
        Message message = Message.obtain();
        message.what = 12;
        message.setData(simpleDate);
        this.d.send(message);
    }

    public IProductResponse a(String packageName, String... productNum) {
        StringBuilder productNumArray = new StringBuilder();
        for (int i = 0; i < productNum.length; i++) {
            productNumArray.append(productNum[i]);
            if (i != productNum.length - 1) {
                productNumArray.append(",");
            }
        }
        TypeReference typeReference = new TypeReference<ResultModel<ProductResultModel>>(this) {
            final /* synthetic */ MStorePurchaseService a;

            {
                this.a = r1;
            }
        };
        List paramPairs = new ArrayList();
        paramPairs.add(new com.meizu.volley.b.a("package_name", packageName));
        if (!TextUtils.isEmpty(productNumArray.toString())) {
            paramPairs.add(new com.meizu.volley.b.a("product_numbers", productNumArray.toString()));
        }
        final IProductResponse productResponse = new IProductResponse();
        l jsonRequest = new FastJsonRequest(typeReference, 0, RequestConstants.getRuntimeDomainUrl(this, RequestConstants.GET_APP_PRODUCT), paramPairs, new com.android.volley.n.b<ResultModel<ProductResultModel>>(this) {
            final /* synthetic */ MStorePurchaseService b;

            public void a(ResultModel<ProductResultModel> response) {
                if (!(response == null || response.getCode() != 200 || response.getValue() == null)) {
                    for (AppSubProduct subProduct : ((ProductResultModel) response.getValue()).products) {
                        IAppProduct iAppProduct = new IAppProduct();
                        iAppProduct.c(subProduct.description);
                        iAppProduct.a((double) subProduct.price);
                        iAppProduct.a(subProduct.number);
                        iAppProduct.b(subProduct.name);
                        productResponse.a().add(iAppProduct);
                    }
                }
                this.b.g = false;
            }
        }, new com.android.volley.n.a(this) {
            final /* synthetic */ MStorePurchaseService a;

            {
                this.a = r1;
            }

            public void a(s error) {
                Log.e("PurchaseService", error.toString());
                this.a.g = false;
            }
        });
        jsonRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this));
        com.meizu.volley.b.a(this).a().a(jsonRequest);
        do {
        } while (this.g);
        return productResponse;
    }

    private void a(int errorCode, String errorMsg, String appIdentify, String productIdentify, String orderNum) {
        for (int i = 0; i < this.b.size(); i++) {
            if (((a) this.b.get(i)).a.equals(appIdentify)) {
                try {
                    ((a) this.b.get(i)).b.a(errorCode, errorMsg, appIdentify, productIdentify, orderNum);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void a(int result, String receipt, String license, String appIdentify, String productIdentify, String orderNum) {
        for (int i = 0; i < this.b.size(); i++) {
            if (((a) this.b.get(i)).a.equals(appIdentify)) {
                try {
                    ((a) this.b.get(i)).b.b(result, license, appIdentify, productIdentify, orderNum);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
