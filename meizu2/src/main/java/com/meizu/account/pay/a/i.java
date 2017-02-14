package com.meizu.account.pay.a;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;

final class i implements g {
    private IBinder a;

    i(IBinder iBinder) {
        this.a = iBinder;
    }

    public final void a(Bundle bundle, d dVar) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.meizu.account.pay.service.IMzSystemPayService");
            if (bundle != null) {
                obtain.writeInt(1);
                bundle.writeToParcel(obtain, 0);
            } else {
                obtain.writeInt(0);
            }
            obtain.writeStrongBinder(dVar != null ? dVar.asBinder() : null);
            this.a.transact(1, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public final void a(Bundle bundle, d dVar, a aVar) {
        IBinder iBinder = null;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.meizu.account.pay.service.IMzSystemPayService");
            if (bundle != null) {
                obtain.writeInt(1);
                bundle.writeToParcel(obtain, 0);
            } else {
                obtain.writeInt(0);
            }
            obtain.writeStrongBinder(dVar != null ? dVar.asBinder() : null);
            if (aVar != null) {
                iBinder = aVar.asBinder();
            }
            obtain.writeStrongBinder(iBinder);
            this.a.transact(2, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public final IBinder asBinder() {
        return this.a;
    }
}
