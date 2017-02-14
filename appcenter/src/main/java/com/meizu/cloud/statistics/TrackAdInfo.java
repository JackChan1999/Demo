package com.meizu.cloud.statistics;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

public class TrackAdInfo implements Parcelable, Serializable {
    public long a;
    public long b;
    public long c;
    public String d;
    public long e;
    public long f;
    public String g;
    public String h;
    public long i;
    public long j;
    public long k;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }
}
