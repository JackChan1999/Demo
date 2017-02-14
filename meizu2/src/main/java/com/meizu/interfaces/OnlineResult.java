package com.meizu.interfaces;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import defpackage.anl;

public class OnlineResult implements Parcelable {
    public static final Creator<OnlineResult> CREATOR = new anl();
    private String a;
    private int b;
    private int c;
    private int d;

    private String a(int i) {
        String str = "unknow";
        switch (i) {
            case -1:
                return "NOTEXISTS";
            case 0:
                return "OFFLINE";
            case 1:
                return "ONLINE";
            default:
                return str;
        }
    }

    public String toString() {
        return "OnlineResult [Number=" + this.a + ", Status=" + a(this.b) + ", Version=" + this.c + ", Ability=" + this.d + "]";
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeInt(this.b);
        parcel.writeInt(this.c);
        parcel.writeInt(this.d);
    }

    public void a(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readInt();
        this.c = parcel.readInt();
        this.d = parcel.readInt();
    }

    private OnlineResult(Parcel parcel) {
        a(parcel);
    }
}
