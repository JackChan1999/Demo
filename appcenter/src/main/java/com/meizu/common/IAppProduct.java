package com.meizu.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class IAppProduct implements Parcelable {
    public static final Creator<IAppProduct> CREATOR = new Creator<IAppProduct>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }

        public IAppProduct a(Parcel in) {
            return new IAppProduct(in);
        }

        public IAppProduct[] a(int size) {
            return new IAppProduct[size];
        }
    };
    private String a;
    private String b;
    private String c;
    private double d;

    public void a(String mProductId) {
        this.a = mProductId;
    }

    public void b(String mProductName) {
        this.b = mProductName;
    }

    public void c(String mProductDecription) {
        this.c = mProductDecription;
    }

    public void a(double mProductPrice) {
        this.d = mProductPrice;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.a);
        dest.writeString(this.b);
        dest.writeString(this.c);
        dest.writeDouble(this.d);
    }

    public void a(Parcel in) {
        this.a = in.readString();
        this.b = in.readString();
        this.c = in.readString();
        this.d = in.readDouble();
    }

    private IAppProduct(Parcel in) {
        a(in);
    }

    public int describeContents() {
        return 0;
    }
}
