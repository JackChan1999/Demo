package com.meizu.flyme.appcenter.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

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

            public void a(List<AppItem> appItems) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.flyme.appcenter.aidl.IAppSearchCallback");
                    _data.writeTypedList(appItems);
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
                    _data.writeInterfaceToken("com.meizu.flyme.appcenter.aidl.IAppSearchCallback");
                    _data.writeInt(errorCode);
                    _data.writeString(errorMsg);
                    this.a.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static a a(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.meizu.flyme.appcenter.aidl.IAppSearchCallback");
            if (iin == null || !(iin instanceof a)) {
                return new a(obj);
            }
            return (a) iin;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface("com.meizu.flyme.appcenter.aidl.IAppSearchCallback");
                    a(data.createTypedArrayList(AppItem.CREATOR));
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface("com.meizu.flyme.appcenter.aidl.IAppSearchCallback");
                    a(data.readInt(), data.readString());
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.meizu.flyme.appcenter.aidl.IAppSearchCallback");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void a(int i, String str) throws RemoteException;

    void a(List<AppItem> list) throws RemoteException;
}
