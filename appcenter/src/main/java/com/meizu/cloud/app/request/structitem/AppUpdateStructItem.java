package com.meizu.cloud.app.request.structitem;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.meizu.cloud.app.request.structitem.AbstractStrcutItem.NotJsonColumn;

public class AppUpdateStructItem extends AppStructItem {
    public static final Creator<AppUpdateStructItem> CREATOR = new Creator<AppUpdateStructItem>() {
        public AppUpdateStructItem createFromParcel(Parcel source) {
            return new AppUpdateStructItem(source);
        }

        public AppUpdateStructItem[] newArray(int size) {
            return new AppUpdateStructItem[size];
        }
    };
    @NotJsonColumn
    public AdContent adContent;
    @NotJsonColumn
    public boolean hideDivider;
    @NotJsonColumn
    public int index;
    @NotJsonColumn
    public boolean isDownload;
    @NotJsonColumn
    public long patchSize;

    public AppUpdateStructItem(Parcel src) {
        super(src);
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(this.patchSize);
        dest.writeInt(this.isDownload ? 1 : 0);
    }

    public void readFromParcel(Parcel src) {
        boolean z = true;
        super.readFromParcel(src);
        this.patchSize = src.readLong();
        if (src.readInt() != 1) {
            z = false;
        }
        this.isDownload = z;
    }

    public boolean isAdStruct() {
        return this.adContent != null;
    }
}
