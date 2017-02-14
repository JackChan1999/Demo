package com.meizu.a.a.a;

import android.os.Binder;
import android.os.Bundle;
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

            public void a(Bundle options, a response) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.account.pay.service.IMzSystemPayService");
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    this.a.transact(1, _data, _reply, 0);
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
            IInterface iin = obj.queryLocalInterface("com.meizu.account.pay.service.IMzSystemPayService");
            if (iin == null || !(iin instanceof b)) {
                return new a(obj);
            }
            return (b) iin;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    Bundle _arg0;
                    data.enforceInterface("com.meizu.account.pay.service.IMzSystemPayService");
                    if (data.readInt() != 0) {
                        _arg0 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    a(_arg0, com.meizu.a.a.a.a.a.a(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.meizu.account.pay.service.IMzSystemPayService");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void a(Bundle bundle, a aVar) throws RemoteException;
}
