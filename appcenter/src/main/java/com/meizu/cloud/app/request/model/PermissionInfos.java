package com.meizu.cloud.app.request.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Serializable;

public class PermissionInfos implements Parcelable, Serializable {
    public static final Creator<PermissionInfos> CREATOR = new Creator<PermissionInfos>() {
        public PermissionInfos createFromParcel(Parcel source) {
            return new PermissionInfos(source);
        }

        public PermissionInfos[] newArray(int size) {
            return new PermissionInfos[size];
        }
    };
    public String permission_code;
    public String permission_group_code;

    public PermissionInfos(Parcel src) {
        readFromParcel(src);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.permission_code);
        dest.writeString(this.permission_group_code);
    }

    public void readFromParcel(Parcel src) {
        this.permission_code = src.readString();
        this.permission_group_code = src.readString();
    }
}
