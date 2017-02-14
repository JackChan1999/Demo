package com.meizu.mstore.license;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface a extends IInterface {

    public static abstract class a extends Binder implements a {
        public a() {
            attachInterface(this, "com.meizu.mstore.license.ILicensingService");
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            LicenseResult _result;
            switch (code) {
                case 1:
                    data.enforceInterface("com.meizu.mstore.license.ILicensingService");
                    _result = a(data.readString());
                    reply.writeNoException();
                    if (_result != null) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, 1);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case 2:
                    data.enforceInterface("com.meizu.mstore.license.ILicensingService");
                    _result = a(data.readString(), data.readInt());
                    reply.writeNoException();
                    if (_result != null) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, 1);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case 3:
                    data.enforceInterface("com.meizu.mstore.license.ILicensingService");
                    _result = a(data.readString(), data.readInt(), data.readString());
                    reply.writeNoException();
                    if (_result != null) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, 1);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case 1598968902:
                    reply.writeString("com.meizu.mstore.license.ILicensingService");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    LicenseResult a(String str) throws RemoteException;

    LicenseResult a(String str, int i) throws RemoteException;

    LicenseResult a(String str, int i, String str2) throws RemoteException;
}
