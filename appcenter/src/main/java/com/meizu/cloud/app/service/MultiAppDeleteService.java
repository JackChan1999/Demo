package com.meizu.cloud.app.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import com.meizu.cloud.app.core.d;
import com.meizu.cloud.app.core.d.c;
import com.meizu.cloud.app.downlad.e;
import com.tencent.mm.sdk.modelbase.BaseResp.ErrCode;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class MultiAppDeleteService extends Service {
    private static final String a = MultiAppDeleteService.class.getSimpleName();
    private List<Pair<String, Messenger>> b = new ArrayList();
    private TreeSet<String> c = new TreeSet();
    private d d;
    private Handler e = new Handler(this) {
        final /* synthetic */ MultiAppDeleteService a;

        {
            this.a = r1;
        }

        public void handleMessage(Message msg) {
            int i;
            switch (msg.what) {
                case ErrCode.ERR_AUTH_DENIED /*-4*/:
                    Bundle stopData = msg.getData();
                    String tatClientPackage = stopData.getString("package");
                    boolean isModified = this.a.c.removeAll((ArrayList) stopData.getSerializable("apps"));
                    if (isModified) {
                        this.a.a(tatClientPackage, isModified);
                        break;
                    }
                    break;
                case -1:
                    String unbindPackageName = msg.getData().getString("package");
                    int index = -1;
                    for (i = 0; i < this.a.b.size(); i++) {
                        if (((String) ((Pair) this.a.b.get(i)).first).equals(unbindPackageName)) {
                            index = i;
                            if (index != -1) {
                                this.a.b.remove(index);
                            }
                            if (this.a.b.size() == 0 && this.a.g) {
                                this.a.stopSelf();
                                break;
                            }
                        }
                    }
                    if (index != -1) {
                        this.a.b.remove(index);
                    }
                    this.a.stopSelf();
                    break;
                case 1:
                    String bindPackageName = msg.getData().getString("package");
                    Messenger targetClient = msg.replyTo;
                    boolean isExist = false;
                    for (i = 0; i < this.a.b.size(); i++) {
                        if (((String) ((Pair) this.a.b.get(i)).first).equals(bindPackageName)) {
                            this.a.b.set(i, Pair.create(bindPackageName, targetClient));
                            isExist = true;
                        }
                    }
                    if (!isExist) {
                        this.a.b.add(Pair.create(bindPackageName, targetClient));
                    }
                    this.a.a();
                    break;
                case 2:
                    ArrayList<Pair<String, Messenger>> nullClients = new ArrayList();
                    for (Pair<String, Messenger> client : this.a.b) {
                        if (client.second != null) {
                            Message message = this.a.e.obtainMessage(-2);
                            message.what = -2;
                            Bundle info = new Bundle();
                            info.putInt("stay_task_count", this.a.c.size());
                            message.setData(info);
                            this.a.a((Messenger) client.second, message);
                        } else {
                            nullClients.add(client);
                        }
                    }
                    this.a.b.removeAll(nullClients);
                    nullClients.clear();
                    break;
                case 4:
                    Bundle startData = msg.getData();
                    this.a.c.addAll((ArrayList) startData.getSerializable("apps"));
                    if (this.a.c.size() > 0) {
                        this.a.a();
                        this.a.d.a((String) this.a.c.first(), this.a.h);
                        break;
                    }
                    break;
                case 10:
                    if (msg.obj != null) {
                        this.a.c.remove(msg.obj);
                        if (this.a.c.size() <= 0) {
                            this.a.b();
                            break;
                        }
                        this.a.d.a((String) this.a.c.first(), this.a.h);
                        break;
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private Messenger f = new Messenger(this.e);
    private boolean g;
    private c h = new c(this) {
        final /* synthetic */ MultiAppDeleteService a;

        {
            this.a = r1;
        }

        public void a(e downloadWrapper, int returnCode) {
        }

        public void a(String packageName, int returnCode) {
            Message message = this.a.e.obtainMessage();
            message.obj = packageName;
            message.what = 10;
            this.a.e.sendMessage(message);
        }
    };

    private void a() {
        ArrayList<Pair<String, Messenger>> nullClients = new ArrayList();
        for (Pair<String, Messenger> targetClient : this.b) {
            if (targetClient.second != null) {
                Message serviceState = this.e.obtainMessage(3);
                if (this.c.size() > 0) {
                    this.g = false;
                    serviceState.what = 3;
                    Bundle data = new Bundle();
                    ArrayList list = new ArrayList(this.c.size());
                    list.addAll(this.c);
                    data.putSerializable("stay_task", list);
                    serviceState.setData(data);
                } else {
                    this.g = true;
                    serviceState.what = 5;
                }
                a((Messenger) targetClient.second, serviceState);
            } else {
                nullClients.add(targetClient);
            }
        }
        this.b.removeAll(nullClients);
        nullClients.clear();
    }

    private void b() {
        ArrayList<Pair<String, Messenger>> nullClients = new ArrayList();
        if (this.c.size() <= 0) {
            for (Pair<String, Messenger> targetClient : this.b) {
                if (targetClient.second != null) {
                    Message message = this.e.obtainMessage(-3);
                    message.what = -3;
                    a((Messenger) targetClient.second, message);
                } else {
                    nullClients.add(targetClient);
                }
            }
            this.b.removeAll(nullClients);
            nullClients.clear();
        }
        stopSelf();
    }

    private void a(String target, boolean isModified) {
        ArrayList<Pair<String, Messenger>> nullClients = new ArrayList();
        for (Pair<String, Messenger> client : this.b) {
            if (((String) client.first).equals(target)) {
                if (client.second != null) {
                    Message message = this.e.obtainMessage(6);
                    message.what = 6;
                    Bundle bundle = new Bundle();
                    bundle.putString("package", target);
                    bundle.putBoolean("record", isModified);
                    a((Messenger) client.second, message);
                } else {
                    nullClients.add(client);
                }
            }
        }
        this.b.removeAll(nullClients);
        nullClients.clear();
    }

    private void a(Messenger targetClient, Message message) {
        try {
            targetClient.send(message);
        } catch (RemoteException e) {
            Log.w(a, "The client does not exist");
        }
    }

    public void onCreate() {
        super.onCreate();
        this.d = new d(this);
    }

    public void onDestroy() {
        super.onDestroy();
        this.e.removeCallbacksAndMessages(null);
        this.f = null;
        this.b.clear();
        this.c.clear();
    }

    public IBinder onBind(Intent intent) {
        return this.f.getBinder();
    }

    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
