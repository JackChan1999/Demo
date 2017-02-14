package com.meizu.cloud.app.request.structitem;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public class RecommendAppStructItem extends AppUpdateStructItem {
    public static final Creator<RecommendAppStructItem> CREATOR = new Creator<RecommendAppStructItem>() {
        public RecommendAppStructItem createFromParcel(Parcel in) {
            return new RecommendAppStructItem(in);
        }

        public RecommendAppStructItem[] newArray(int size) {
            return new RecommendAppStructItem[size];
        }
    };
    public boolean isChecked = true;

    public RecommendAppStructItem(Parcel src) {
        readFromParcel(src);
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.recommend_desc);
    }

    public void readFromParcel(Parcel src) {
        super.readFromParcel(src);
        this.recommend_desc = src.readString();
    }
}
