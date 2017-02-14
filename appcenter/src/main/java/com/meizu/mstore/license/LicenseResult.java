package com.meizu.mstore.license;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.meizu.cloud.app.utils.c;
import java.util.ArrayList;

public class LicenseResult implements Parcelable {
    public static final Creator<LicenseResult> CREATOR = new Creator<LicenseResult>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }

        public LicenseResult a(Parcel source) {
            return new LicenseResult(source);
        }

        public LicenseResult[] a(int size) {
            return new LicenseResult[size];
        }
    };
    private int a;
    private int b;
    private byte[] c;
    private byte[] d;
    private int e;
    private ArrayList<SubProduct> f;

    public int a() {
        return this.a;
    }

    protected void a(int responseCode) {
        this.a = responseCode;
    }

    protected void b(int purchaseType) {
        this.b = purchaseType;
    }

    protected void a(byte[] signedDataBytes, int start, int length) {
        if (start >= 0 && length >= 0 && signedDataBytes != null && start + length <= signedDataBytes.length) {
            this.c = new byte[length];
            for (int i = start; i < start + length; i++) {
                this.c[i - start] = signedDataBytes[i];
            }
        } else if (c.b) {
            Log.e(c.a, "setSignedDataBytes length error!");
        }
    }

    protected void b(byte[] signatureBytes, int start, int length) {
        if (start >= 0 && length >= 0 && signatureBytes != null && start + length <= signatureBytes.length) {
            this.d = new byte[length];
            for (int i = start; i < start + length; i++) {
                this.d[i - start] = signatureBytes[i];
            }
        } else if (c.b) {
            Log.e(c.a, "setSignatureBytes length error!");
        }
    }

    protected void c(int startDate) {
        this.e = startDate;
    }

    public ArrayList<SubProduct> b() {
        return this.f;
    }

    public int describeContents() {
        return 0;
    }

    protected LicenseResult() {
        this.a = -1;
        this.b = 0;
        this.c = null;
        this.d = null;
        this.e = -1;
        this.f = new ArrayList();
    }

    public LicenseResult(Parcel parcel) {
        this.a = parcel.readInt();
        this.b = parcel.readInt();
        this.c = parcel.createByteArray();
        this.d = parcel.createByteArray();
        this.e = parcel.readInt();
        this.f = new ArrayList();
        parcel.readList(this.f, SubProduct.class.getClassLoader());
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.a);
        dest.writeInt(this.b);
        dest.writeByteArray(this.c);
        dest.writeByteArray(this.d);
        dest.writeInt(this.e);
        dest.writeList(this.f);
    }
}
