package com.meizu.cloud.app.request.structitem;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public class DiscoveryStructItem extends AbstractStrcutItem {
    public static final Creator<DiscoveryStructItem> CREATOR = new Creator<DiscoveryStructItem>() {
        public DiscoveryStructItem createFromParcel(Parcel source) {
            return new DiscoveryStructItem(source);
        }

        public DiscoveryStructItem[] newArray(int size) {
            return new DiscoveryStructItem[size];
        }
    };
    public long last_time;
    public String logo;

    public DiscoveryStructItem(Parcel src) {
        readFromParcel(src);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeString(this.logo);
        dest.writeLong(this.last_time);
    }

    public void readFromParcel(Parcel src) {
        this.name = src.readString();
        this.type = src.readString();
        this.url = src.readString();
        this.logo = src.readString();
        this.last_time = src.readLong();
    }
}
