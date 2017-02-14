package com.meizu.cloud.app.request.structitem;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tags implements Parcelable, Serializable {
    public static final Creator<Tags> CREATOR = new Creator<Tags>() {
        public Tags createFromParcel(Parcel source) {
            return new Tags(source);
        }

        public Tags[] newArray(int size) {
            return new Tags[size];
        }
    };
    public boolean hasgift;
    public String icon;
    public List<Name> names;

    public class Name {
        public String bg_color;
        public String text;
    }

    public Tags(Parcel src) {
        readFromParcel(src);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i;
        if (this.hasgift) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeInt(i);
        dest.writeString(this.icon);
        if (this.names == null || this.names.size() <= 0) {
            dest.writeInt(0);
            return;
        }
        dest.writeInt(this.names.size());
        for (int i2 = 0; i2 < this.names.size(); i2++) {
            dest.writeString(((Name) this.names.get(i2)).text);
            dest.writeString(((Name) this.names.get(i2)).bg_color);
        }
    }

    public void readFromParcel(Parcel src) {
        boolean z = true;
        if (src.readInt() != 1) {
            z = false;
        }
        this.hasgift = z;
        this.icon = src.readString();
        int count = src.readInt();
        if (count > 0) {
            this.names = new ArrayList();
            for (int i = 0; i < count; i++) {
                Name name1 = new Name();
                name1.text = src.readString();
                name1.bg_color = src.readString();
                this.names.add(name1);
            }
        }
    }
}
