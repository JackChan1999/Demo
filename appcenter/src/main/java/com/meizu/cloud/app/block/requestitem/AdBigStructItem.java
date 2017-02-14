package com.meizu.cloud.app.block.requestitem;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public class AdBigStructItem extends AppAdStructItem {
    public static final Creator<AdBigStructItem> CREATOR = new Creator<AdBigStructItem>() {
        public AdBigStructItem createFromParcel(Parcel source) {
            return new AdBigStructItem(source);
        }

        public AdBigStructItem[] newArray(int size) {
            return new AdBigStructItem[size];
        }
    };
    public String description;
    public int id;
    public int img_height = 452;
    public int img_width = 988;
    public String logo;

    public AdBigStructItem(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.description);
        dest.writeInt(this.id);
        dest.writeString(this.logo);
    }

    public void readFromParcel(Parcel src) {
        super.readFromParcel(src);
        this.description = src.readString();
        this.id = src.readInt();
        this.logo = src.readString();
    }
}
