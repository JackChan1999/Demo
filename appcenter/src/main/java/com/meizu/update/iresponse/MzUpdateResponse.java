package com.meizu.update.iresponse;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.meizu.update.iresponse.a.a;

public class MzUpdateResponse implements Parcelable {
    public static final Creator<MzUpdateResponse> CREATOR = new Creator<MzUpdateResponse>() {
        public /* synthetic */ Object createFromParcel(Parcel x0) {
            return a(x0);
        }

        public /* synthetic */ Object[] newArray(int x0) {
            return a(x0);
        }

        public MzUpdateResponse a(Parcel source) {
            return new MzUpdateResponse(source);
        }

        public MzUpdateResponse[] a(int size) {
            return new MzUpdateResponse[size];
        }
    };
    private a a;

    public MzUpdateResponse(a response) {
        this.a = response;
    }

    protected MzUpdateResponse(Parcel parcel) {
        this.a = a.a(parcel.readStrongBinder());
    }

    public void a(String path) {
        Bundle extras = new Bundle();
        extras.putString("apk_path", path);
        a(0, extras);
    }

    public void a() {
        a(2, null);
    }

    public void b() {
        a(1, null);
    }

    private void a(int code, Bundle extras) {
        try {
            this.a.b(code, extras);
        } catch (RemoteException ignore) {
            ignore.printStackTrace();
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

    private void a(int code) {
        try {
            this.a.a(code, null);
        } catch (RemoteException ignore) {
            ignore.printStackTrace();
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongBinder(this.a.asBinder());
    }
}
