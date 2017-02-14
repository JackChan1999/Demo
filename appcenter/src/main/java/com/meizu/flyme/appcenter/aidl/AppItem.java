package com.meizu.flyme.appcenter.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class AppItem implements Parcelable {
    public static final Creator<AppItem> CREATOR = new Creator<AppItem>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }

        public AppItem a(Parcel source) {
            return new AppItem(source);
        }

        public AppItem[] a(int size) {
            return new AppItem[size];
        }
    };
    public long a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;

    public AppItem(Parcel in) {
        this.a = in.readLong();
        this.b = in.readString();
        this.c = in.readString();
        this.d = in.readString();
        this.e = in.readString();
        this.f = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.a);
        dest.writeString(this.b);
        dest.writeString(this.c);
        dest.writeString(this.d);
        dest.writeString(this.e);
        dest.writeString(this.f);
    }
}
