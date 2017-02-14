package com.meizu.cloud.app.block.requestitem;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.meizu.cloud.app.request.structitem.AbstractStrcutItem;
import com.meizu.cloud.app.request.structitem.PropertyTag;
import java.util.ArrayList;
import java.util.List;

public class ContsRow1Col4StructItem extends AbstractStrcutItem {
    public static final Creator<ContsRow1Col4StructItem> CREATOR = new Creator<ContsRow1Col4StructItem>() {
        public ContsRow1Col4StructItem createFromParcel(Parcel source) {
            return new ContsRow1Col4StructItem(source);
        }

        public ContsRow1Col4StructItem[] newArray(int size) {
            return new ContsRow1Col4StructItem[size];
        }
    };
    public String icon;
    public int id;
    public List<PropertyTag> propertyTags;
    public List<PropertyTag> property_tags;

    public ContsRow1Col4StructItem(Parcel src) {
        readFromParcel(src);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeInt(this.id);
        dest.writeString(this.icon);
        if (this.propertyTags != null && this.propertyTags.size() > 0) {
            dest.writeInt(this.propertyTags.size());
            for (PropertyTag tag : this.propertyTags) {
                dest.writeInt(tag.id);
                dest.writeString(tag.name);
                dest.writeString(tag.url);
            }
        }
        if (this.property_tags != null && this.property_tags.size() > 0) {
            dest.writeInt(this.property_tags.size());
            for (PropertyTag tag2 : this.property_tags) {
                dest.writeInt(tag2.id);
                dest.writeString(tag2.name);
                dest.writeString(tag2.url);
            }
        }
    }

    public void readFromParcel(Parcel src) {
        int i;
        this.name = src.readString();
        this.type = src.readString();
        this.url = src.readString();
        this.id = src.readInt();
        this.icon = src.readString();
        int Gamesize = src.readInt();
        this.propertyTags = new ArrayList();
        for (i = 0; i < Gamesize; i++) {
            PropertyTag tag = new PropertyTag();
            tag.id = src.readInt();
            tag.name = src.readString();
            tag.url = src.readString();
            this.propertyTags.add(tag);
        }
        int AppSize = src.readInt();
        this.property_tags = new ArrayList();
        for (i = 0; i < AppSize; i++) {
            tag = new PropertyTag();
            tag.id = src.readInt();
            tag.name = src.readString();
            tag.url = src.readString();
            this.property_tags.add(tag);
        }
    }
}
