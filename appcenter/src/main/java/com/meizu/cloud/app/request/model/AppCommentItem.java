package com.meizu.cloud.app.request.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Serializable;

public class AppCommentItem implements Parcelable, Serializable {
    public static final Creator<AppCommentItem> CREATOR = new Creator<AppCommentItem>() {
        public AppCommentItem createFromParcel(Parcel source) {
            AppCommentItem app = new AppCommentItem();
            app.readFromParcel(source);
            return app;
        }

        public AppCommentItem[] newArray(int size) {
            return new AppCommentItem[size];
        }
    };
    public String comment;
    public long create_time;
    public boolean donated;
    public int id;
    public int like;
    public AppCommentReply reply;
    public int star;
    public int type;
    public String uicon;
    public String user_icon;
    public String user_name;
    public int version_code;
    public String version_name;

    public class AppCommentReply implements Parcelable, Serializable {
        public String comment;
        public long create_time;
        public String user_name;

        public AppCommentReply(Parcel parcel) {
            readFromParcel(parcel);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.comment);
            dest.writeLong(this.create_time);
            dest.writeString(this.user_name);
        }

        public void readFromParcel(Parcel parcel) {
            this.comment = parcel.readString();
            this.create_time = parcel.readLong();
            this.user_name = parcel.readString();
        }
    }

    public AppCommentItem(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.comment);
        dest.writeLong(this.create_time);
        dest.writeInt(this.donated ? 1 : 0);
        dest.writeInt(this.star);
        dest.writeString(this.uicon);
        dest.writeString(this.user_name);
        dest.writeInt(this.version_code);
        dest.writeString(this.version_name);
        dest.writeInt(this.id);
        dest.writeInt(this.like);
        dest.writeInt(this.type);
        dest.writeString(this.user_icon);
    }

    public void readFromParcel(Parcel parcel) {
        boolean z = true;
        this.comment = parcel.readString();
        this.create_time = parcel.readLong();
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.donated = z;
        this.star = parcel.readInt();
        this.uicon = parcel.readString();
        this.user_name = parcel.readString();
        this.version_code = parcel.readInt();
        this.version_name = parcel.readString();
        this.id = parcel.readInt();
        this.like = parcel.readInt();
        this.type = parcel.readInt();
        this.user_icon = parcel.readString();
    }
}
