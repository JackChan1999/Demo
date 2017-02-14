package com.meizu.d;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface b extends IInterface {

    public static abstract class a extends Binder implements b {

        private static class a implements b {
            private IBinder a;

            a(IBinder remote) {
                this.a = remote;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public void a(int errorCode, String errorMsg, String packageName, String productIdentify, String orderNum) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.interfaces.IMStorePurchaseServiceCallback");
                    _data.writeInt(errorCode);
                    _data.writeString(errorMsg);
                    _data.writeString(packageName);
                    _data.writeString(productIdentify);
                    _data.writeString(orderNum);
                    this.a.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void b(int result, String license, String packageName, String productIdentify, String orderNum) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.interfaces.IMStorePurchaseServiceCallback");
                    _data.writeInt(result);
                    _data.writeString(license);
                    _data.writeString(packageName);
                    _data.writeString(productIdentify);
                    _data.writeString(orderNum);
                    this.a.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static b a(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.meizu.interfaces.IMStorePurchaseServiceCallback");
            if (iin == null || !(iin instanceof b)) {
                return new a(obj);
            }
            return (b) iin;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface("com.meizu.interfaces.IMStorePurchaseServiceCallback");
                    a(data.readInt(), data.readString(), data.readString(), data.readString(), data.readString());
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface("com.meizu.interfaces.IMStorePurchaseServiceCallback");
                    b(data.readInt(), data.readString(), data.readString(), data.readString(), data.readString());
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.meizu.interfaces.IMStorePurchaseServiceCallback");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void a(int i, String str, String str2, String str3, String str4) throws RemoteException;

    void b(int i, String str, String str2, String str3, String str4) throws RemoteException;
}
