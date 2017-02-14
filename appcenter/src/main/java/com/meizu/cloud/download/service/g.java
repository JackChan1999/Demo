package com.meizu.cloud.download.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface g extends IInterface {

    public static abstract class a extends Binder implements g {

        private static class a implements g {
            private IBinder a;

            a(IBinder remote) {
                this.a = remote;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public void a(DownloadTaskInfo downloadTaskInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.cloud.download.service.IDownloadStateListener");
                    if (downloadTaskInfo != null) {
                        _data.writeInt(1);
                        downloadTaskInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.a.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void b(DownloadTaskInfo downloadTaskInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.cloud.download.service.IDownloadStateListener");
                    if (downloadTaskInfo != null) {
                        _data.writeInt(1);
                        downloadTaskInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.a.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void c(DownloadTaskInfo downloadTaskInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.cloud.download.service.IDownloadStateListener");
                    if (downloadTaskInfo != null) {
                        _data.writeInt(1);
                        downloadTaskInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.a.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public a() {
            attachInterface(this, "com.meizu.cloud.download.service.IDownloadStateListener");
        }

        public static g a(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.meizu.cloud.download.service.IDownloadStateListener");
            if (iin == null || !(iin instanceof g)) {
                return new a(obj);
            }
            return (g) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            DownloadTaskInfo _arg0;
            switch (code) {
                case 1:
                    data.enforceInterface("com.meizu.cloud.download.service.IDownloadStateListener");
                    if (data.readInt() != 0) {
                        _arg0 = (DownloadTaskInfo) DownloadTaskInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    a(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface("com.meizu.cloud.download.service.IDownloadStateListener");
                    if (data.readInt() != 0) {
                        _arg0 = (DownloadTaskInfo) DownloadTaskInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    b(_arg0);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface("com.meizu.cloud.download.service.IDownloadStateListener");
                    if (data.readInt() != 0) {
                        _arg0 = (DownloadTaskInfo) DownloadTaskInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    c(_arg0);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.meizu.cloud.download.service.IDownloadStateListener");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void a(DownloadTaskInfo downloadTaskInfo) throws RemoteException;

    void b(DownloadTaskInfo downloadTaskInfo) throws RemoteException;

    void c(DownloadTaskInfo downloadTaskInfo) throws RemoteException;
}
