package com.meizu.cloud.app.request.structitem;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public class SpecialStructItem extends AbstractStrcutItem {
    public static final Creator<SpecialStructItem> CREATOR = new Creator<SpecialStructItem>() {
        public SpecialStructItem createFromParcel(Parcel source) {
            return new SpecialStructItem(source);
        }

        public SpecialStructItem[] newArray(int size) {
            return new SpecialStructItem[size];
        }
    };
    public String description;
    public int id;
    public String logo;
    public String publicity_img;
    public String subject;

    public SpecialStructItem(Parcel src) {
        readFromParcel(src);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.logo);
    }

    public void readFromParcel(Parcel src) {
        this.id = src.readInt();
        this.name = src.readString();
        this.url = src.readString();
        this.logo = src.readString();
    }
}
