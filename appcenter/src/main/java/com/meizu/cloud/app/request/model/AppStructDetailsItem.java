package com.meizu.cloud.app.request.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.meizu.cloud.app.block.requestitem.RollMessageStructItem;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.request.structitem.AppUpdateStructItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AppStructDetailsItem extends AppUpdateStructItem {
    public static final Creator<AppStructDetailsItem> CREATOR = new Creator<AppStructDetailsItem>() {
        public AppStructDetailsItem createFromParcel(Parcel source) {
            AppStructDetailsItem app = new AppStructDetailsItem();
            app.readFromParcel(source);
            return app;
        }

        public AppStructDetailsItem[] newArray(int size) {
            return new AppStructDetailsItem[size];
        }
    };
    public boolean accept_donate;
    public ActivityPageInfo activity;
    public List<AppTags> app_tags;
    public double avg_score;
    public boolean can_comment;
    public String description;
    public int developer_id;
    public String forum_url;
    public boolean free;
    public int gift_count;
    public String h5_detail_url;
    public long install_count;
    public boolean is_from_hot_search;
    public List<GameSurroundTrainItem> layouts;
    public int newsCount;
    public Notice notice;
    public List<PermissionInfos> permissions;
    public QualityLabel quality_label;
    public QualityLabelDesc quality_label_desc;
    public int reviewCount;
    public String review_url;
    public List<RollMessageStructItem> rollMsgs;
    public SafeLabel safe_label;
    public SafeLabelDesc safe_label_desc;
    public List<Sources> sources;
    public int[] star_percent;
    public int strategryCount;
    public String update_description;
    public int vedioCount;
    public long version_create_time;
    public long version_time;
    public String web_detail_url;

    public class ActivityPageInfo implements Parcelable, Serializable {
        public String subject;
        public String url;

        public ActivityPageInfo(Parcel src) {
            readFromParcel(src);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.url);
            dest.writeString(this.subject);
        }

        public void readFromParcel(Parcel src) {
            this.url = src.readString();
            this.subject = src.readString();
        }
    }

    public class AppTags implements Parcelable, Serializable {
        public int id;
        public String title;
        public String url;

        public AppTags(Parcel src) {
            readFromParcel(src);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.title);
            dest.writeString(this.url);
        }

        public void readFromParcel(Parcel src) {
            this.id = src.readInt();
            this.title = src.readString();
            this.url = src.readString();
        }
    }

    public class Notice implements Parcelable, Serializable {
        public int gift_count;
        public String image;
        public String title;
        public String type;
        public String url;

        public Notice(Parcel src) {
            readFromParcel(src);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.type);
            dest.writeString(this.image);
            dest.writeString(this.url);
            dest.writeString(this.title);
            dest.writeInt(this.gift_count);
        }

        public void readFromParcel(Parcel src) {
            this.type = src.readString();
            this.image = src.readString();
            this.url = src.readString();
            this.title = src.readString();
            this.gift_count = src.readInt();
        }
    }

    public class QualityLabel implements Parcelable, Serializable {
        public int official;
        public int superior;

        public QualityLabel(Parcel src) {
            readFromParcel(src);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.official);
            dest.writeInt(this.superior);
        }

        public void readFromParcel(Parcel src) {
            this.official = src.readInt();
            this.superior = src.readInt();
        }
    }

    public class QualityLabelDesc implements Parcelable, Serializable {
        public String official;
        public String superior;
        public String title;

        public QualityLabelDesc(Parcel src) {
            readFromParcel(src);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.title);
            dest.writeString(this.official);
            dest.writeString(this.superior);
        }

        public void readFromParcel(Parcel src) {
            this.title = src.readString();
            this.official = src.readString();
            this.superior = src.readString();
        }
    }

    public class SafeLabel implements Parcelable, Serializable {
        public int ads;
        public int security;

        public SafeLabel(Parcel src) {
            readFromParcel(src);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.ads);
            dest.writeInt(this.security);
        }

        public void readFromParcel(Parcel src) {
            this.ads = src.readInt();
            this.security = src.readInt();
        }
    }

    public class SafeLabelDesc implements Parcelable, Serializable {
        public String ad1;
        public String ad2;
        public String ad3;
        public String ad4;
        public String safe;
        public String title;

        public SafeLabelDesc(Parcel src) {
            readFromParcel(src);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.title);
            dest.writeString(this.safe);
            dest.writeString(this.ad1);
            dest.writeString(this.ad2);
            dest.writeString(this.ad3);
            dest.writeString(this.ad4);
        }

        public void readFromParcel(Parcel src) {
            this.title = src.readString();
            this.safe = src.readString();
            this.ad1 = src.readString();
            this.ad2 = src.readString();
            this.ad3 = src.readString();
            this.ad4 = src.readString();
        }
    }

    public class Sources implements Parcelable, Serializable {
        public String description;
        public String logo;
        public String name;
        public String url;

        public Sources(Parcel src) {
            readFromParcel(src);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.description);
            dest.writeString(this.logo);
            dest.writeString(this.url);
            dest.writeString(this.name);
        }

        public void readFromParcel(Parcel src) {
            this.description = src.readString();
            this.logo = src.readString();
            this.url = src.readString();
            this.name = src.readString();
        }
    }

    public AppStructDetailsItem(Parcel src) {
        super(src);
        readFromParcel(src);
    }

    public AppStructItem getAppStructItem() {
        return this;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i;
        int i2;
        super.writeToParcel(dest, flags);
        if (this.accept_donate) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeInt(i);
        dest.writeDouble(this.avg_score);
        dest.writeInt(this.developer_id);
        if (this.free) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeInt(i);
        dest.writeString(this.description);
        dest.writeLong(this.install_count);
        dest.writeIntArray(this.star_percent);
        dest.writeInt(this.trial_days);
        dest.writeString(this.update_description);
        dest.writeLong(this.version_time);
        if (this.can_comment) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeInt(i);
        dest.writeLong(this.version_create_time);
        dest.writeInt(this.gift_count);
        dest.writeString(this.forum_url);
        dest.writeString(this.review_url);
        if (this.sources != null) {
            dest.writeInt(this.sources.size());
            for (i2 = 0; i2 < this.sources.size(); i2++) {
                ((Sources) this.sources.get(i2)).writeToParcel(dest, flags);
            }
        } else {
            dest.writeInt(0);
        }
        dest.writeString(this.web_detail_url);
        if (this.quality_label != null) {
            dest.writeInt(1);
            this.quality_label.writeToParcel(dest, flags);
        } else {
            dest.writeInt(0);
        }
        if (this.safe_label != null) {
            dest.writeInt(1);
            this.safe_label.writeToParcel(dest, flags);
        } else {
            dest.writeInt(0);
        }
        if (this.quality_label_desc != null) {
            dest.writeInt(1);
            this.quality_label_desc.writeToParcel(dest, flags);
        } else {
            dest.writeInt(0);
        }
        if (this.safe_label_desc != null) {
            dest.writeInt(1);
            this.safe_label_desc.writeToParcel(dest, flags);
        } else {
            dest.writeInt(0);
        }
        if (this.activity != null) {
            dest.writeInt(1);
            this.activity.writeToParcel(dest, flags);
        } else {
            dest.writeInt(0);
        }
        if (this.is_from_hot_search) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeInt(i);
        if (this.app_tags != null) {
            dest.writeInt(this.app_tags.size());
            for (i2 = 0; i2 < this.app_tags.size(); i2++) {
                ((AppTags) this.app_tags.get(i2)).writeToParcel(dest, flags);
            }
        } else {
            dest.writeInt(0);
        }
        if (this.permissions != null) {
            dest.writeInt(this.permissions.size());
            for (i2 = 0; i2 < this.permissions.size(); i2++) {
                ((PermissionInfos) this.permissions.get(i2)).writeToParcel(dest, flags);
            }
        } else {
            dest.writeInt(0);
        }
        if (this.notice != null) {
            dest.writeInt(1);
            new Notice().writeToParcel(dest, flags);
        } else {
            dest.writeInt(0);
        }
        if (this.rollMsgs != null) {
            dest.writeInt(this.rollMsgs.size());
            for (i2 = 0; i2 < this.rollMsgs.size(); i2++) {
                ((RollMessageStructItem) this.rollMsgs.get(i2)).writeToParcel(dest, flags);
            }
        } else {
            dest.writeInt(0);
        }
        dest.writeInt(this.newsCount);
        dest.writeInt(this.reviewCount);
        dest.writeInt(this.strategryCount);
        dest.writeInt(this.vedioCount);
    }

    public void readFromParcel(Parcel src) {
        boolean z;
        int i;
        boolean z2 = false;
        super.readFromParcel(src);
        if (src.readInt() == 1) {
            z = true;
        } else {
            z = false;
        }
        this.accept_donate = z;
        this.avg_score = src.readDouble();
        this.developer_id = src.readInt();
        if (src.readInt() == 1) {
            z = true;
        } else {
            z = false;
        }
        this.free = z;
        this.description = src.readString();
        this.install_count = src.readLong();
        this.star_percent = src.createIntArray();
        this.trial_days = src.readInt();
        this.update_description = src.readString();
        this.version_time = src.readLong();
        if (src.readInt() == 1) {
            z = true;
        } else {
            z = false;
        }
        this.can_comment = z;
        this.version_create_time = src.readLong();
        this.gift_count = src.readInt();
        this.forum_url = src.readString();
        this.review_url = src.readString();
        int sourcesCount = src.readInt();
        if (sourcesCount != 0) {
            this.sources = new ArrayList();
            for (i = 0; i < sourcesCount; i++) {
                this.sources.add(new Sources(src));
            }
        }
        this.web_detail_url = src.readString();
        if (src.readInt() == 1) {
            this.quality_label = new QualityLabel(src);
        }
        if (src.readInt() == 1) {
            this.safe_label = new SafeLabel(src);
        }
        if (src.readInt() == 1) {
            this.quality_label_desc = new QualityLabelDesc(src);
        }
        if (src.readInt() == 1) {
            this.safe_label_desc = new SafeLabelDesc(src);
        }
        if (src.readInt() == 1) {
            this.activity = new ActivityPageInfo(src);
        }
        if (src.readInt() == 1) {
            z2 = true;
        }
        this.is_from_hot_search = z2;
        int appTagsCount = src.readInt();
        if (appTagsCount != 0) {
            this.app_tags = new ArrayList();
            for (i = 0; i < appTagsCount; i++) {
                this.app_tags.add(new AppTags(src));
            }
        }
        int permissionCount = src.readInt();
        if (permissionCount != 0) {
            this.permissions = new ArrayList();
            for (i = 0; i < permissionCount; i++) {
                this.permissions.add(new PermissionInfos(src));
            }
        }
        if (src.readInt() == 1) {
            this.notice = new Notice(src);
        }
        int rollMsgCount = src.readInt();
        if (rollMsgCount != 0) {
            this.rollMsgs = new ArrayList();
            for (i = 0; i < rollMsgCount; i++) {
                this.rollMsgs.add(new RollMessageStructItem(src));
            }
        }
        this.newsCount = src.readInt();
        this.reviewCount = src.readInt();
        this.strategryCount = src.readInt();
        this.vedioCount = src.readInt();
    }
}
