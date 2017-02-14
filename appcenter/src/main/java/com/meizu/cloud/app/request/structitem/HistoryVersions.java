package com.meizu.cloud.app.request.structitem;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.a.a;
import com.meizu.cloud.app.request.structitem.AbstractStrcutItem.NotJsonColumn;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HistoryVersions implements Parcelable, Serializable {
    public static final Creator<HistoryVersions> CREATOR = new Creator<HistoryVersions>() {
        public HistoryVersions createFromParcel(Parcel source) {
            return new HistoryVersions(source);
        }

        public HistoryVersions[] newArray(int size) {
            return new HistoryVersions[size];
        }
    };
    public String name;
    public List<VersionItem> versions;

    public class VersionItem implements Parcelable, Serializable {
        public final Creator<VersionItem> CREATOR = new Creator<VersionItem>() {
            public VersionItem createFromParcel(Parcel source) {
                return new VersionItem(source);
            }

            public VersionItem[] newArray(int size) {
                return new VersionItem[size];
            }
        };
        @a
        public String description;
        @NotJsonColumn
        public boolean isFold = true;
        public int[] page_info;
        @NotJsonColumn
        public HistoryVersions parent;
        @a
        public long sale_time;
        @a
        public int size;
        @a
        public boolean smartbar;
        @a
        public int version_code;
        @a
        public long version_id;
        @a
        public String version_name;

        public VersionItem(Parcel src) {
            readFromParcel(src);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.description);
            dest.writeLong(this.sale_time);
            dest.writeInt(this.size);
            dest.writeInt(this.smartbar ? 1 : 0);
            dest.writeInt(this.version_code);
            dest.writeLong(this.version_id);
            dest.writeString(this.version_name);
            dest.writeIntArray(this.page_info);
        }

        public void readFromParcel(Parcel src) {
            boolean z = true;
            this.description = src.readString();
            this.sale_time = src.readLong();
            this.size = src.readInt();
            if (src.readInt() != 1) {
                z = false;
            }
            this.smartbar = z;
            this.version_code = src.readInt();
            this.version_id = src.readLong();
            this.version_name = src.readString();
            this.page_info = src.createIntArray();
        }
    }

    public HistoryVersions(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        if (this.versions != null) {
            dest.writeInt(this.versions.size());
            for (VersionItem item : this.versions) {
                item.writeToParcel(dest, flags);
            }
            return;
        }
        dest.writeInt(0);
    }

    public void readFromParcel(Parcel src) {
        this.name = src.readString();
        int count = src.readInt();
        this.versions = new ArrayList();
        for (int i = 0; i < count; i++) {
            this.versions.add(new VersionItem(src));
        }
    }
}
