package com.meizu.cloud.app.block.requestitem;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.meizu.cloud.app.request.structitem.AppStructItem;

public class AppAdBigStructItem extends AppStructItem {
    public static final Creator<AppAdBigStructItem> CREATOR = new Creator<AppAdBigStructItem>() {
        public AppAdBigStructItem createFromParcel(Parcel source) {
            return new AppAdBigStructItem(source);
        }

        public AppAdBigStructItem[] newArray(int size) {
            return new AppAdBigStructItem[size];
        }
    };
    public String back_image;
    public String img_url;
    public String tag;
    public String tag_color;

    public AppAdBigStructItem(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.img_url);
        dest.writeString(this.back_image);
    }

    public void readFromParcel(Parcel src) {
        super.readFromParcel(src);
        this.img_url = src.readString();
        this.back_image = src.readString();
    }
}
