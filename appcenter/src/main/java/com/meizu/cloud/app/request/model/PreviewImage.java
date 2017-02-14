package com.meizu.cloud.app.request.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Serializable;

public class PreviewImage implements Parcelable, Serializable {
    public static final Creator<PreviewImage> CREATOR = new Creator<PreviewImage>() {
        public PreviewImage createFromParcel(Parcel source) {
            PreviewImage app = new PreviewImage();
            app.readFromParcel(source);
            return app;
        }

        public PreviewImage[] newArray(int size) {
            return new PreviewImage[size];
        }
    };
    public int height;
    public String image;
    public String small;
    public int width;
    public int x;
    public int y;

    public PreviewImage(Parcel src) {
        readFromParcel(src);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.height);
        dest.writeInt(this.width);
        dest.writeInt(this.x);
        dest.writeInt(this.y);
        dest.writeString(this.image);
        dest.writeString(this.small);
    }

    public void readFromParcel(Parcel src) {
        this.height = src.readInt();
        this.width = src.readInt();
        this.x = src.readInt();
        this.y = src.readInt();
        this.image = src.readString();
        this.small = src.readString();
    }
}
