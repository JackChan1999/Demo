package com.meizu.account.pay.a;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;

public abstract class b extends Binder implements a {
    public b() {
        attachInterface(this, "com.meizu.account.pay.service.IMzSystemPayBridge");
    }

    public IBinder asBinder() {
        return this;
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        Bundle a;
        switch (i) {
            case 1:
                parcel.enforceInterface("com.meizu.account.pay.service.IMzSystemPayBridge");
                a = a(parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                if (a != null) {
                    parcel2.writeInt(1);
                    a.writeToParcel(parcel2, 1);
                    return true;
                }
                parcel2.writeInt(0);
                return true;
            case 2:
                parcel.enforceInterface("com.meizu.account.pay.service.IMzSystemPayBridge");
                a = a(parcel.readString());
                parcel2.writeNoException();
                if (a != null) {
                    parcel2.writeInt(1);
                    a.writeToParcel(parcel2, 1);
                    return true;
                }
                parcel2.writeInt(0);
                return true;
            case 1598968902:
                parcel2.writeString("com.meizu.account.pay.service.IMzSystemPayBridge");
                return true;
            default:
                return super.onTransact(i, parcel, parcel2, i2);
        }
    }
}
