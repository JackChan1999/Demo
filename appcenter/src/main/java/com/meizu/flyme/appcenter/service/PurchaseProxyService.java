package com.meizu.flyme.appcenter.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.meizu.cloud.base.app.BaseApplication;

@Deprecated
public class PurchaseProxyService extends Service {
    private Messenger a;
    private Messenger b;
    private Messenger c = new Messenger(new a());

    private class a extends Handler {
        final /* synthetic */ PurchaseProxyService a;

        private a(PurchaseProxyService purchaseProxyService) {
            this.a = purchaseProxyService;
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case -120:
                    Message payFailure = Message.obtain();
                    payFailure.what = -120;
                    payFailure.setData(msg.getData());
                    try {
                        this.a.a.send(payFailure);
                        break;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        break;
                    }
                case -21:
                    this.a.b = null;
                    break;
                case -11:
                    this.a.a = null;
                    break;
                case -10:
                    Message getProductErrorMsg = Message.obtain();
                    getProductErrorMsg.what = -10;
                    getProductErrorMsg.setData(msg.getData());
                    try {
                        this.a.a.send(getProductErrorMsg);
                        break;
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                        break;
                    }
                case 11:
                    this.a.a = msg.replyTo;
                    break;
                case 12:
                    Bundle data = msg.getData();
                    if (data != null) {
                        this.a.a(data);
                        break;
                    }
                    break;
                case 21:
                    this.a.b = msg.replyTo;
                    Message getProductMessage = Message.obtain();
                    getProductMessage.what = 10;
                    try {
                        msg.replyTo.send(getProductMessage);
                        break;
                    } catch (RemoteException e22) {
                        e22.printStackTrace();
                        break;
                    }
                case 23:
                    Message cancelMsg = Message.obtain();
                    cancelMsg.what = 23;
                    cancelMsg.setData(msg.getData());
                    try {
                        this.a.a.send(cancelMsg);
                        break;
                    } catch (RemoteException e222) {
                        e222.printStackTrace();
                        break;
                    }
                case 120:
                    Message paySuccess = Message.obtain();
                    paySuccess.what = 120;
                    paySuccess.setData(msg.getData());
                    try {
                        this.a.a.send(paySuccess);
                        break;
                    } catch (RemoteException e2222) {
                        e2222.printStackTrace();
                        break;
                    }
            }
            super.handleMessage(msg);
        }
    }

    public void onCreate() {
        super.onCreate();
        BaseApplication.a((Context) this);
    }

    private void a(Bundle data) {
        Intent intent = new Intent("android.meizu.MstorePurchase");
        intent.setFlags(268468224);
        intent.putExtras(data);
        startActivity(intent);
    }

    public IBinder onBind(Intent intent) {
        return this.c.getBinder();
    }

    public void onDestroy() {
        super.onDestroy();
        this.a = null;
        this.b = null;
    }
}
