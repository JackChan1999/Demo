package com.meizu.account.pay.a;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

public abstract class e extends Binder implements d {
    public e() {
        attachInterface(this, "com.meizu.account.pay.service.IMzSystemPayResponse");
    }

    public static d a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.meizu.account.pay.service.IMzSystemPayResponse");
        return (queryLocalInterface == null || !(queryLocalInterface instanceof d)) ? new f(iBinder) : (d) queryLocalInterface;
    }

    public IBinder asBinder() {
        return this;
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        Bundle bundle = null;
        switch (i) {
            case 1:
                parcel.enforceInterface("com.meizu.account.pay.service.IMzSystemPayResponse");
                if (parcel.readInt() != 0) {
                    bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                }
                a(bundle);
                parcel2.writeNoException();
                return true;
            case 2:
                parcel.enforceInterface("com.meizu.account.pay.service.IMzSystemPayResponse");
                a(parcel.readInt(), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 3:
                parcel.enforceInterface("com.meizu.account.pay.service.IMzSystemPayResponse");
                if (parcel.readInt() != 0) {
                    bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                }
                b(bundle);
                parcel2.writeNoException();
                return true;
            case 1598968902:
                parcel2.writeString("com.meizu.account.pay.service.IMzSystemPayResponse");
                return true;
            default:
                return super.onTransact(i, parcel, parcel2, i2);
        }
    }
}
