package com.meizu.cloud.app.block.requestitem;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.meizu.cloud.app.request.structitem.AbstractStrcutItem;
import com.meizu.cloud.app.request.structitem.AbstractStrcutItem.NotJsonColumn;

public class AppAdStructItem extends AbstractStrcutItem {
    public static final Creator<AppAdStructItem> CREATOR = new Creator<AppAdStructItem>() {
        public AppAdStructItem createFromParcel(Parcel source) {
            return new AppAdStructItem(source);
        }

        public AppAdStructItem[] newArray(int size) {
            return new AppAdStructItem[size];
        }
    };
    @NotJsonColumn
    public int ad_position;
    @NotJsonColumn
    public int ad_wdm_pos;
    @NotJsonColumn
    public int ad_wdm_type;
    public int aid;
    public int content_id;
    public int img_height = 200;
    public int img_size;
    public String img_url;
    public int img_width = 400;
    public String package_name;
    public int page_id;
    public String tag;
    public String tag_color;

    public AppAdStructItem(Parcel src) {
        readFromParcel(src);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeInt(this.aid);
        dest.writeInt(this.content_id);
        dest.writeString(this.img_url);
        dest.writeInt(this.img_width);
        dest.writeInt(this.img_height);
        dest.writeInt(this.img_size);
        dest.writeString(this.url);
        dest.writeInt(this.page_id);
    }

    public void readFromParcel(Parcel src) {
        this.type = src.readString();
        this.aid = src.readInt();
        this.content_id = src.readInt();
        this.img_url = src.readString();
        this.img_width = src.readInt();
        this.img_height = src.readInt();
        this.img_size = src.readInt();
        this.url = src.readString();
        this.page_id = src.readInt();
    }
}
