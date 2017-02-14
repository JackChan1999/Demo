package com.meizu.cloud.download.service;

import android.os.RemoteCallbackList;
import android.os.RemoteException;

public class d {
    private RemoteCallbackList<g> a = new RemoteCallbackList();

    public void a(g listener) {
        synchronized (this) {
            if (listener != null) {
                this.a.register(listener);
            }
        }
    }

    public void b(g listener) {
        synchronized (this) {
            if (listener != null) {
                this.a.unregister(listener);
            }
        }
    }

    public void a() {
        synchronized (this) {
            this.a.kill();
            this.a = null;
        }
    }

    public void a(DownloadTaskInfo downloadTaskInfo) {
        synchronized (this) {
            int n = this.a.beginBroadcast();
            int i = 0;
            while (i < n) {
                try {
                    ((g) this.a.getBroadcastItem(i)).a(downloadTaskInfo);
                    i++;
                } catch (RemoteException e) {
                    e.printStackTrace();
                    this.a.finishBroadcast();
                } catch (Throwable th) {
                    this.a.finishBroadcast();
                }
            }
            this.a.finishBroadcast();
        }
    }

    public void b(DownloadTaskInfo downloadTaskInfo) {
        synchronized (this) {
            int n = this.a.beginBroadcast();
            int i = 0;
            while (i < n) {
                try {
                    ((g) this.a.getBroadcastItem(i)).b(downloadTaskInfo);
                    i++;
                } catch (RemoteException e) {
                    e.printStackTrace();
                    this.a.finishBroadcast();
                } catch (Throwable th) {
                    this.a.finishBroadcast();
                }
            }
            this.a.finishBroadcast();
        }
    }

    public void c(DownloadTaskInfo downloadTaskInfo) {
        synchronized (this) {
            int n = this.a.beginBroadcast();
            int i = 0;
            while (i < n) {
                try {
                    ((g) this.a.getBroadcastItem(i)).c(downloadTaskInfo);
                    i++;
                } catch (RemoteException e) {
                    e.printStackTrace();
                    this.a.finishBroadcast();
                } catch (Throwable th) {
                    this.a.finishBroadcast();
                }
            }
            this.a.finishBroadcast();
        }
    }
}
