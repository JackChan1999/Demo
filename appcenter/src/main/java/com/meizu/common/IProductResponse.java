package com.meizu.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;

public class IProductResponse implements Parcelable {
    public static final Creator<IProductResponse> CREATOR = new Creator<IProductResponse>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }

        public IProductResponse a(Parcel in) {
            return new IProductResponse(in);
        }

        public IProductResponse[] a(int size) {
            return new IProductResponse[size];
        }
    };
    private ArrayList<IAppProduct> a;
    private ArrayList<String> b;

    public IProductResponse() {
        this.a = new ArrayList();
        this.b = new ArrayList();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.a);
        dest.writeList(this.b);
    }

    public void a(Parcel in) {
        in.readList(this.a, IAppProduct.class.getClassLoader());
        in.readList(this.b, null);
    }

    public ArrayList<IAppProduct> a() {
        return this.a;
    }

    private IProductResponse(Parcel in) {
        this.a = new ArrayList();
        this.b = new ArrayList();
        a(in);
    }

    public int describeContents() {
        return 0;
    }
}
