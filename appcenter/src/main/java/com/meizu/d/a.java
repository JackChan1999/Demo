package com.meizu.d;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.meizu.common.IProductResponse;

public interface a extends IInterface {

    public static abstract class a extends Binder implements a {
        public a() {
            attachInterface(this, "com.meizu.interfaces.IMStorePurchaseService");
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int i = 0;
            switch (code) {
                case 1:
                    data.enforceInterface("com.meizu.interfaces.IMStorePurchaseService");
                    IProductResponse _result = a(data.createStringArray(), data.readString());
                    reply.writeNoException();
                    if (_result != null) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, 1);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case 2:
                    data.enforceInterface("com.meizu.interfaces.IMStorePurchaseService");
                    a(data.readString(), data.readInt(), data.readString());
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface("com.meizu.interfaces.IMStorePurchaseService");
                    a(data.readString(), data.readInt(), data.readString(), data.readString());
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface("com.meizu.interfaces.IMStorePurchaseService");
                    a(data.readString(), com.meizu.d.b.a.a(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface("com.meizu.interfaces.IMStorePurchaseService");
                    b(data.readString(), com.meizu.d.b.a.a(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface("com.meizu.interfaces.IMStorePurchaseService");
                    boolean _result2 = a(data.readString());
                    reply.writeNoException();
                    if (_result2) {
                        i = 1;
                    }
                    reply.writeInt(i);
                    return true;
                case 1598968902:
                    reply.writeString("com.meizu.interfaces.IMStorePurchaseService");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    IProductResponse a(String[] strArr, String str) throws RemoteException;

    void a(String str, int i, String str2) throws RemoteException;

    void a(String str, int i, String str2, String str3) throws RemoteException;

    void a(String str, b bVar) throws RemoteException;

    boolean a(String str) throws RemoteException;

    void b(String str, b bVar) throws RemoteException;
}
