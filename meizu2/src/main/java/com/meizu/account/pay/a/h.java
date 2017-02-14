package com.meizu.account.pay.a;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

public abstract class h extends Binder implements g {
    public static g a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.meizu.account.pay.service.IMzSystemPayService");
        return (queryLocalInterface == null || !(queryLocalInterface instanceof g)) ? new i(iBinder) : (g) queryLocalInterface;
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        a aVar = null;
        switch (i) {
            case 1:
                parcel.enforceInterface("com.meizu.account.pay.service.IMzSystemPayService");
                a(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, e.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 2:
                parcel.enforceInterface("com.meizu.account.pay.service.IMzSystemPayService");
                Bundle bundle = parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null;
                d a = e.a(parcel.readStrongBinder());
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.meizu.account.pay.service.IMzSystemPayBridge");
                    aVar = (queryLocalInterface == null || !(queryLocalInterface instanceof a)) ? new c(readStrongBinder) : (a) queryLocalInterface;
                }
                a(bundle, a, aVar);
                parcel2.writeNoException();
                return true;
            case 1598968902:
                parcel2.writeString("com.meizu.account.pay.service.IMzSystemPayService");
                return true;
            default:
                return super.onTransact(i, parcel, parcel2, i2);
        }
    }
}
