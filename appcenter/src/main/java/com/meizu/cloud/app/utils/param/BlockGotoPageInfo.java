package com.meizu.cloud.app.utils.param;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.request.structitem.PropertyTag;
import java.io.Serializable;
import java.util.List;

public class BlockGotoPageInfo implements Parcelable, Serializable {
    public static final Creator<BlockGotoPageInfo> CREATOR = new Creator<BlockGotoPageInfo>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }

        public BlockGotoPageInfo a(Parcel source) {
            return new BlockGotoPageInfo(source);
        }

        public BlockGotoPageInfo[] a(int size) {
            return new BlockGotoPageInfo[size];
        }
    };
    public String a;
    public String b;
    public String c;
    public AppStructItem d;
    public int e;
    public int f;
    public List<PropertyTag> g;
    public int h;
    public String i;
    public int j;
    public int k;
    public int l;
    public String m;
    public String n;
    public int o;
    public long p;

    public BlockGotoPageInfo(Parcel src) {
        a(src);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    public void a(Parcel src) {
    }
}
