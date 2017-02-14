package com.meizu.statsapp;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface a extends IInterface {

    public static abstract class a extends Binder implements a {

        private static class a implements a {
            private IBinder a;

            a(IBinder remote) {
                this.a = remote;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public void a(String packageName, boolean start, String name, long time) throws RemoteException {
                int i = 1;
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                try {
                    data.writeInterfaceToken("com.meizu.stats.IUsageStatsManager");
                    data.writeString(packageName);
                    if (!start) {
                        i = 0;
                    }
                    data.writeInt(i);
                    data.writeString(name);
                    data.writeLong(time);
                    this.a.transact(1, data, reply, 0);
                    reply.readException();
                } finally {
                    reply.recycle();
                    data.recycle();
                }
            }

            public void onEvent(Event event) throws RemoteException {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                try {
                    data.writeInterfaceToken("com.meizu.stats.IUsageStatsManager");
                    event.writeToParcel(data, 0);
                    this.a.transact(2, data, reply, 0);
                    reply.readException();
                } finally {
                    reply.recycle();
                    data.recycle();
                }
            }

            public void onEventRealtime(Event event) throws RemoteException {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                try {
                    data.writeInterfaceToken("com.meizu.stats.IUsageStatsManager");
                    event.writeToParcel(data, 0);
                    this.a.transact(3, data, reply, 0);
                    reply.readException();
                } finally {
                    reply.recycle();
                    data.recycle();
                }
            }

            public void a(boolean upload) throws RemoteException {
                int i = 0;
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                try {
                    data.writeInterfaceToken("com.meizu.stats.IUsageStatsManager");
                    if (upload) {
                        i = 1;
                    }
                    data.writeInt(i);
                    this.a.transact(4, data, reply, 0);
                    reply.readException();
                } finally {
                    reply.recycle();
                    data.recycle();
                }
            }
        }

        public a() {
            attachInterface(this, "com.meizu.stats.IUsageStatsManager");
        }

        public static a a(IBinder binder) {
            if (binder == null) {
                return null;
            }
            IInterface iin = binder.queryLocalInterface("com.meizu.stats.IUsageStatsManager");
            if (iin == null || !(iin instanceof a)) {
                return new a(binder);
            }
            return (a) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            boolean start = false;
            switch (code) {
                case 1:
                    data.enforceInterface("com.meizu.stats.IUsageStatsManager");
                    String packageName = data.readString();
                    if (data.readInt() != 0) {
                        start = true;
                    }
                    a(packageName, start, data.readString(), data.readLong());
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface("com.meizu.stats.IUsageStatsManager");
                    onEvent(new Event(data));
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface("com.meizu.stats.IUsageStatsManager");
                    onEventRealtime(new Event(data));
                    reply.writeNoException();
                    return true;
                case 4:
                    boolean upload;
                    data.enforceInterface("com.meizu.stats.IUsageStatsManager");
                    if (data.readInt() == 0) {
                        upload = false;
                    } else {
                        upload = true;
                    }
                    a(upload);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.meizu.stats.IUsageStatsManager");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void a(String str, boolean z, String str2, long j) throws RemoteException;

    void a(boolean z) throws RemoteException;

    void onEvent(Event event) throws RemoteException;

    void onEventRealtime(Event event) throws RemoteException;
}
