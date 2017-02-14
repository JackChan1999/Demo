package com.meizu.cloud.statistics;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Serializable;

public class UxipPageSourceInfo implements Parcelable, Serializable {
    public static final Creator<UxipPageSourceInfo> CREATOR = new Creator<UxipPageSourceInfo>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }

        public UxipPageSourceInfo a(Parcel source) {
            return new UxipPageSourceInfo(source);
        }

        public UxipPageSourceInfo[] a(int size) {
            return new UxipPageSourceInfo[size];
        }
    };
    public String a;
    public int b;
    public String c;
    public int d;
    public int e;
    public String f;
    public long g;

    public UxipPageSourceInfo(Parcel parcel) {
        a(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.a);
        dest.writeInt(this.b);
        dest.writeString(this.c);
        dest.writeInt(this.d);
        dest.writeInt(this.e);
        dest.writeString(this.f);
        dest.writeLong(this.g);
    }

    public void a(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readInt();
        this.c = parcel.readString();
        this.d = parcel.readInt();
        this.e = parcel.readInt();
        this.f = parcel.readString();
        this.g = parcel.readLong();
    }
}
