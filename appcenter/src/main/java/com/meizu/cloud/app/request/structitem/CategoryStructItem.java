package com.meizu.cloud.app.request.structitem;

import android.os.Parcel;
import android.os.Parcelable;
import com.meizu.cloud.download.c.a.b;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class CategoryStructItem extends AbstractStrcutItem implements Parcelable, Serializable {
    public static final b<CategoryStructItem> CREATOR = new b<CategoryStructItem>() {
        public CategoryStructItem create() {
            return new CategoryStructItem();
        }
    };
    public String bg;
    public String icon;
    public ArrayList<PropertyTag> property_tags;
    public String title;
    public int title_color;

    public CategoryStructItem(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.icon);
        dest.writeString(this.title);
        dest.writeInt(this.title_color);
        if (this.property_tags == null || this.property_tags.size() <= 0) {
            dest.writeInt(0);
            return;
        }
        dest.writeInt(this.property_tags.size());
        Iterator i$ = this.property_tags.iterator();
        while (i$.hasNext()) {
            PropertyTag tag = (PropertyTag) i$.next();
            dest.writeInt(tag.id);
            dest.writeString(tag.name);
            dest.writeString(tag.url);
        }
    }

    public void readFromParcel(Parcel src) {
        this.name = src.readString();
        this.url = src.readString();
        this.icon = src.readString();
        this.title = src.readString();
        this.title_color = src.readInt();
        int size = src.readInt();
        this.property_tags = new ArrayList();
        for (int i = 0; i < size; i++) {
            PropertyTag tag = new PropertyTag();
            tag.id = src.readInt();
            tag.name = src.readString();
            tag.url = src.readString();
            tag.description = src.readString();
            this.property_tags.add(tag);
        }
    }
}
