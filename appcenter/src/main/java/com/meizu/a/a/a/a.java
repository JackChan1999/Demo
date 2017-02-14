package com.meizu.a.a.a;

import android.os.Binder;
import android.os.Bundle;
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

            public void a(Bundle value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.account.pay.service.IMzSystemPayResponse");
                    if (value != null) {
                        _data.writeInt(1);
                        value.writeToParcel(_data, 0);
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

            public void a(int errorCode, String errorMsg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.account.pay.service.IMzSystemPayResponse");
                    _data.writeInt(errorCode);
                    _data.writeString(errorMsg);
                    this.a.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void b(Bundle value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.account.pay.service.IMzSystemPayResponse");
                    if (value != null) {
                        _data.writeInt(1);
                        value.writeToParcel(_data, 0);
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
            attachInterface(this, "com.meizu.account.pay.service.IMzSystemPayResponse");
        }

        public static a a(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.meizu.account.pay.service.IMzSystemPayResponse");
            if (iin == null || !(iin instanceof a)) {
                return new a(obj);
            }
            return (a) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Bundle _arg0;
            switch (code) {
                case 1:
                    data.enforceInterface("com.meizu.account.pay.service.IMzSystemPayResponse");
                    if (data.readInt() != 0) {
                        _arg0 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    a(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface("com.meizu.account.pay.service.IMzSystemPayResponse");
                    a(data.readInt(), data.readString());
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface("com.meizu.account.pay.service.IMzSystemPayResponse");
                    if (data.readInt() != 0) {
                        _arg0 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    b(_arg0);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.meizu.account.pay.service.IMzSystemPayResponse");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void a(int i, String str) throws RemoteException;

    void a(Bundle bundle) throws RemoteException;

    void b(Bundle bundle) throws RemoteException;
}
