package com.meizu.flyme.appcenter.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface b extends IInterface {

    public static abstract class a extends Binder implements b {
        public a() {
            attachInterface(this, "com.meizu.flyme.appcenter.aidl.IAppSearchService");
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface("com.meizu.flyme.appcenter.aidl.IAppSearchService");
                    a(data.readInt() != 0, data.readString());
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface("com.meizu.flyme.appcenter.aidl.IAppSearchService");
                    a(com.meizu.flyme.appcenter.aidl.a.a.a(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface("com.meizu.flyme.appcenter.aidl.IAppSearchService");
                    b(com.meizu.flyme.appcenter.aidl.a.a.a(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.meizu.flyme.appcenter.aidl.IAppSearchService");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void a(a aVar) throws RemoteException;

    void a(boolean z, String str) throws RemoteException;

    void b(a aVar) throws RemoteException;
}
