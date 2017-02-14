package com.meizu.mstore.license;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

@Deprecated
public class SubProduct implements Parcelable {
    public static final Creator<SubProduct> CREATOR = new Creator<SubProduct>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }

        public SubProduct a(Parcel in) {
            return new SubProduct(in);
        }

        public SubProduct[] a(int size) {
            return new SubProduct[size];
        }
    };
    private String a;
    private int b;
    private int c;

    protected void a(String mProductId) {
        this.a = mProductId;
    }

    protected void a(int mQuantity) {
        this.b = mQuantity;
    }

    protected void b(int mDate) {
        this.c = mDate;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.a);
        dest.writeInt(this.b);
        dest.writeInt(this.c);
    }

    public void a(Parcel in) {
        this.a = in.readString();
        this.b = in.readInt();
        this.c = in.readInt();
    }

    private SubProduct(Parcel in) {
        a(in);
    }
}
