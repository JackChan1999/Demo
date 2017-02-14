package com.meizu.cloud.download.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface f extends IInterface {

    public static abstract class a extends Binder implements f {

        private static class a implements f {
            private IBinder a;

            a(IBinder remote) {
                this.a = remote;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public long a(DownloadTaskInfo downloadTaskInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.cloud.download.service.IDownloadService");
                    if (downloadTaskInfo != null) {
                        _data.writeInt(1);
                        downloadTaskInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.a.transact(1, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void a() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.cloud.download.service.IDownloadService");
                    this.a.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void a(long taskId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.cloud.download.service.IDownloadService");
                    _data.writeLong(taskId);
                    this.a.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void b() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.cloud.download.service.IDownloadService");
                    this.a.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void b(long taskId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.cloud.download.service.IDownloadService");
                    _data.writeLong(taskId);
                    this.a.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void c() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.cloud.download.service.IDownloadService");
                    this.a.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void c(long taskId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.cloud.download.service.IDownloadService");
                    _data.writeLong(taskId);
                    this.a.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void d() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.cloud.download.service.IDownloadService");
                    this.a.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void a(long taskId, boolean bDelete) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.cloud.download.service.IDownloadService");
                    _data.writeLong(taskId);
                    if (bDelete) {
                        i = 1;
                    }
                    _data.writeInt(i);
                    this.a.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void a(boolean bDelete) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.cloud.download.service.IDownloadService");
                    if (bDelete) {
                        i = 1;
                    }
                    _data.writeInt(i);
                    this.a.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void a(g listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.cloud.download.service.IDownloadService");
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.a.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void b(g listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.cloud.download.service.IDownloadService");
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.a.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int e() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.cloud.download.service.IDownloadService");
                    this.a.transact(13, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int f() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.cloud.download.service.IDownloadService");
                    this.a.transact(14, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<DownloadTaskInfo> g() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.cloud.download.service.IDownloadService");
                    this.a.transact(15, _data, _reply, 0);
                    _reply.readException();
                    List<DownloadTaskInfo> _result = _reply.createTypedArrayList(DownloadTaskInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<DownloadTaskInfo> h() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.cloud.download.service.IDownloadService");
                    this.a.transact(16, _data, _reply, 0);
                    _reply.readException();
                    List<DownloadTaskInfo> _result = _reply.createTypedArrayList(DownloadTaskInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void a(int limit) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.cloud.download.service.IDownloadService");
                    _data.writeInt(limit);
                    this.a.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void b(boolean enable) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.cloud.download.service.IDownloadService");
                    if (enable) {
                        i = 1;
                    }
                    _data.writeInt(i);
                    this.a.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean i() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.cloud.download.service.IDownloadService");
                    this.a.transact(19, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public a() {
            attachInterface(this, "com.meizu.cloud.download.service.IDownloadService");
        }

        public static f a(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.meizu.cloud.download.service.IDownloadService");
            if (iin == null || !(iin instanceof f)) {
                return new a(obj);
            }
            return (f) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int _arg1 = 0;
            boolean _arg0;
            int _result;
            List<DownloadTaskInfo> _result2;
            switch (code) {
                case 1:
                    DownloadTaskInfo _arg02;
                    data.enforceInterface("com.meizu.cloud.download.service.IDownloadService");
                    if (data.readInt() != 0) {
                        _arg02 = (DownloadTaskInfo) DownloadTaskInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg02 = null;
                    }
                    long _result3 = a(_arg02);
                    reply.writeNoException();
                    reply.writeLong(_result3);
                    return true;
                case 2:
                    data.enforceInterface("com.meizu.cloud.download.service.IDownloadService");
                    a();
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface("com.meizu.cloud.download.service.IDownloadService");
                    a(data.readLong());
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface("com.meizu.cloud.download.service.IDownloadService");
                    b();
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface("com.meizu.cloud.download.service.IDownloadService");
                    b(data.readLong());
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface("com.meizu.cloud.download.service.IDownloadService");
                    c();
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface("com.meizu.cloud.download.service.IDownloadService");
                    c(data.readLong());
                    reply.writeNoException();
                    return true;
                case 8:
                    data.enforceInterface("com.meizu.cloud.download.service.IDownloadService");
                    d();
                    reply.writeNoException();
                    return true;
                case 9:
                    boolean _arg12;
                    data.enforceInterface("com.meizu.cloud.download.service.IDownloadService");
                    long _arg03 = data.readLong();
                    if (data.readInt() != 0) {
                        _arg12 = true;
                    }
                    a(_arg03, _arg12);
                    reply.writeNoException();
                    return true;
                case 10:
                    data.enforceInterface("com.meizu.cloud.download.service.IDownloadService");
                    if (data.readInt() != 0) {
                        _arg0 = true;
                    } else {
                        _arg0 = false;
                    }
                    a(_arg0);
                    reply.writeNoException();
                    return true;
                case 11:
                    data.enforceInterface("com.meizu.cloud.download.service.IDownloadService");
                    a(com.meizu.cloud.download.service.g.a.a(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 12:
                    data.enforceInterface("com.meizu.cloud.download.service.IDownloadService");
                    b(com.meizu.cloud.download.service.g.a.a(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 13:
                    data.enforceInterface("com.meizu.cloud.download.service.IDownloadService");
                    _result = e();
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 14:
                    data.enforceInterface("com.meizu.cloud.download.service.IDownloadService");
                    _result = f();
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 15:
                    data.enforceInterface("com.meizu.cloud.download.service.IDownloadService");
                    _result2 = g();
                    reply.writeNoException();
                    reply.writeTypedList(_result2);
                    return true;
                case 16:
                    data.enforceInterface("com.meizu.cloud.download.service.IDownloadService");
                    _result2 = h();
                    reply.writeNoException();
                    reply.writeTypedList(_result2);
                    return true;
                case 17:
                    data.enforceInterface("com.meizu.cloud.download.service.IDownloadService");
                    a(data.readInt());
                    reply.writeNoException();
                    return true;
                case 18:
                    data.enforceInterface("com.meizu.cloud.download.service.IDownloadService");
                    if (data.readInt() != 0) {
                        _arg0 = true;
                    } else {
                        _arg0 = false;
                    }
                    b(_arg0);
                    reply.writeNoException();
                    return true;
                case 19:
                    data.enforceInterface("com.meizu.cloud.download.service.IDownloadService");
                    boolean _result4 = i();
                    reply.writeNoException();
                    if (_result4) {
                        _arg1 = 1;
                    }
                    reply.writeInt(_arg1);
                    return true;
                case 1598968902:
                    reply.writeString("com.meizu.cloud.download.service.IDownloadService");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    long a(DownloadTaskInfo downloadTaskInfo) throws RemoteException;

    void a() throws RemoteException;

    void a(int i) throws RemoteException;

    void a(long j) throws RemoteException;

    void a(long j, boolean z) throws RemoteException;

    void a(g gVar) throws RemoteException;

    void a(boolean z) throws RemoteException;

    void b() throws RemoteException;

    void b(long j) throws RemoteException;

    void b(g gVar) throws RemoteException;

    void b(boolean z) throws RemoteException;

    void c() throws RemoteException;

    void c(long j) throws RemoteException;

    void d() throws RemoteException;

    int e() throws RemoteException;

    int f() throws RemoteException;

    List<DownloadTaskInfo> g() throws RemoteException;

    List<DownloadTaskInfo> h() throws RemoteException;

    boolean i() throws RemoteException;
}
