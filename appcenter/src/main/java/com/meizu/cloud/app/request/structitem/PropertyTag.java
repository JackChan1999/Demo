package com.meizu.cloud.app.request.structitem;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class PropertyTag implements Parcelable, Serializable {

    public static final Creator<PropertyTag> CREATOR = new Creator<PropertyTag>() {
        public PropertyTag createFromParcel(Parcel source) {
            return new PropertyTag(source);
        }

        public PropertyTag[] newArray(int size) {
            return new PropertyTag[size];
        }
    };

    public String description;
    public boolean hide;
    public int id;
    public String name;
    public String url;

    public PropertyTag(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.description);
        dest.writeInt(this.hide ? 1 : 0);
    }

    public void readFromParcel(Parcel src) {
        boolean z = true;
        this.id = src.readInt();
        this.name = src.readString();
        this.url = src.readString();
        this.description = src.readString();
        if (src.readInt() != 1) {
            z = false;
        }
        this.hide = z;
    }
}
