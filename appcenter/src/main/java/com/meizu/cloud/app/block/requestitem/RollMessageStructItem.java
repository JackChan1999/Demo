package com.meizu.cloud.app.block.requestitem;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.meizu.cloud.app.request.structitem.AbstractStrcutItem;

public class RollMessageStructItem extends AbstractStrcutItem {
    public static final Creator<RollMessageStructItem> CREATOR = new Creator<RollMessageStructItem>() {
        public RollMessageStructItem createFromParcel(Parcel source) {
            return new RollMessageStructItem(source);
        }

        public RollMessageStructItem[] newArray(int size) {
            return new RollMessageStructItem[size];
        }
    };
    public String bg_color;
    public String color;
    public long content_id;
    public String message;
    public String tag;
    public String tag_color;

    public RollMessageStructItem(Parcel src) {
        readFromParcel(src);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeString(this.bg_color);
        dest.writeString(this.color);
        dest.writeString(this.message);
        dest.writeString(this.tag);
        dest.writeString(this.tag_color);
    }

    public void readFromParcel(Parcel src) {
        this.name = src.readString();
        this.type = src.readString();
        this.bg_color = src.readString();
        this.color = src.readString();
        this.message = src.readString();
        this.tag = src.readString();
        this.tag_color = src.readString();
    }
}
