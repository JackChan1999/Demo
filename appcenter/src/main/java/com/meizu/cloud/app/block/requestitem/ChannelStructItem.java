package com.meizu.cloud.app.block.requestitem;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.meizu.cloud.app.request.structitem.AbstractStrcutItem;

public class ChannelStructItem extends AbstractStrcutItem {
    public static final Creator<ChannelStructItem> CREATOR = new Creator<ChannelStructItem>() {
        public ChannelStructItem createFromParcel(Parcel source) {
            return new ChannelStructItem(source);
        }

        public ChannelStructItem[] newArray(int size) {
            return new ChannelStructItem[size];
        }
    };
    public long last_time;
    public String logo;

    public ChannelStructItem(Parcel src) {
        readFromParcel(src);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeLong(this.last_time);
        dest.writeString(this.logo);
    }

    public void readFromParcel(Parcel src) {
        this.name = src.readString();
        this.type = src.readString();
        this.url = src.readString();
        this.last_time = src.readLong();
        this.logo = src.readString();
    }
}
