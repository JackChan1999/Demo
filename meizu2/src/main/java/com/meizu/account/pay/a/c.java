package com.meizu.account.pay.a;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;

final class c implements a {
    private IBinder a;

    c(IBinder iBinder) {
        this.a = iBinder;
    }

    public final Bundle a(String str) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.meizu.account.pay.service.IMzSystemPayBridge");
            obtain.writeString(str);
            this.a.transact(2, obtain, obtain2, 0);
            obtain2.readException();
            Bundle bundle = obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
            obtain2.recycle();
            obtain.recycle();
            return bundle;
        } catch (Throwable th) {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public final Bundle a(String str, String str2) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.meizu.account.pay.service.IMzSystemPayBridge");
            obtain.writeString(str);
            obtain.writeString(str2);
            this.a.transact(1, obtain, obtain2, 0);
            obtain2.readException();
            Bundle bundle = obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
            obtain2.recycle();
            obtain.recycle();
            return bundle;
        } catch (Throwable th) {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public final IBinder asBinder() {
        return this.a;
    }
}
