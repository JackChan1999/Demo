package com.meizu.update.iresponse;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import defpackage.arl;
import defpackage.arl$a;
import defpackage.arm;

public class MzUpdateResponse implements Parcelable {
    public static final Creator<MzUpdateResponse> CREATOR = new arm();
    private arl a;

    public MzUpdateResponse(arl arl) {
        this.a = arl;
    }

    public MzUpdateResponse(Parcel parcel) {
        this.a = arl$a.a(parcel.readStrongBinder());
    }

    public void a(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("apk_path", str);
        a(0, bundle);
    }

    public void a() {
        a(2, null);
    }

    public void b() {
        a(1, null);
    }

    private void a(int i, Bundle bundle) {
        try {
            this.a.b(i, bundle);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void c() {
        a(0);
    }

    public void d() {
        a(2);
    }

    public void e() {
        a(3);
    }

    private void a(int i) {
        try {
            this.a.a(i, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.a.asBinder());
    }
}
