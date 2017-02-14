package com.meizu.flyme.appcenter.activitys;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.meizu.flyme.appcenter.service.PurchaseProxyService;
import com.meizu.mstore.purchase.a;
import com.meizu.mstore.purchase.c;

@Deprecated
public class MStorePurchaseActivity extends Activity {
    private static final String a = MStorePurchaseActivity.class.getSimpleName();
    private a b;
    private Messenger c;
    private ServiceConnection d = new ServiceConnection(this) {
        final /* synthetic */ MStorePurchaseActivity a;

        {
            this.a = r1;
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            this.a.c = new Messenger(iBinder);
            this.a.b.a(this.a.c);
            try {
                Message message = Message.obtain();
                message.what = 21;
                message.replyTo = this.a.b.a();
                this.a.c.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            try {
                Message message = Message.obtain();
                message.what = -21;
                message.replyTo = this.a.b.a();
                this.a.c.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            this.a.c = null;
            this.a.b.a(null);
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.b = new c();
        this.b.a(this, getIntent().getExtras());
        if (this.c == null) {
            bindService(new Intent(getApplicationContext(), PurchaseProxyService.class), this.d, 1);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        unbindService(this.d);
    }
}
