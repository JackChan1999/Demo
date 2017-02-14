package com.meizu.cloud.app.block.requestitem;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.meizu.cloud.app.request.structitem.RecommendAppStructItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameQualityStructItem extends RecommendAppStructItem {
    public static final Creator<GameQualityStructItem> CREATOR = new Creator<GameQualityStructItem>() {
        public GameQualityStructItem createFromParcel(Parcel source) {
            return new GameQualityStructItem(source);
        }

        public GameQualityStructItem[] newArray(int size) {
            return new GameQualityStructItem[size];
        }
    };
    public String desc_color;
    public String feed_img;
    public List<GameLayout> game_layout;

    public class GameLayout implements Parcelable, Serializable {
        public final Creator<GameLayout> CREATOR = new Creator<GameLayout>() {
            public GameLayout createFromParcel(Parcel source) {
                return new GameLayout(source);
            }

            public GameLayout[] newArray(int size) {
                return new GameLayout[size];
            }
        };
        public int count;
        public String name;
        public String type;
        public String url;

        public GameLayout(Parcel src) {
            readFromParcel(src);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.count);
            dest.writeString(this.name);
            dest.writeString(this.type);
            dest.writeString(this.url);
        }

        public void readFromParcel(Parcel src) {
            this.count = src.readInt();
            this.name = src.readString();
            this.type = src.readString();
            this.url = src.readString();
        }
    }

    public GameQualityStructItem(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.desc_color);
        dest.writeString(this.feed_img);
        if (this.game_layout != null) {
            dest.writeInt(this.game_layout.size());
            for (int i = 0; i < this.game_layout.size(); i++) {
                ((GameLayout) this.game_layout.get(i)).writeToParcel(dest, flags);
            }
            return;
        }
        dest.writeInt(0);
    }

    public void readFromParcel(Parcel src) {
        super.readFromParcel(src);
        this.desc_color = src.readString();
        this.feed_img = src.readString();
        int layoutCount = src.readInt();
        if (layoutCount > 0) {
            this.game_layout = new ArrayList();
            for (int i = 0; i < layoutCount; i++) {
                this.game_layout.add(new GameLayout(src));
            }
        }
    }
}
