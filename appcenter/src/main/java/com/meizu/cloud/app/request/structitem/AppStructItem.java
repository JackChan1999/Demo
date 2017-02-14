package com.meizu.cloud.app.request.structitem;

import android.content.Context;
import android.os.Parcel;
import android.text.TextUtils;
import com.meizu.cloud.app.core.t.a;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.request.model.DownloadInfo;
import com.meizu.cloud.app.request.model.PreviewImage;
import com.meizu.cloud.app.request.structitem.AbstractStrcutItem.NotJsonColumn;
import com.meizu.cloud.download.c.a.b;
import com.meizu.cloud.download.c.a.c;
import com.meizu.cloud.statistics.TrackAdInfo;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@c(a = "download_app")
public class AppStructItem extends AbstractStrcutItem implements a {
    public static final b<AppStructItem> ENTRY_CREATOR = new b<AppStructItem>() {
        public AppStructItem create() {
            return new AppStructItem();
        }
    };
    public static final com.meizu.cloud.download.c.b SCHEMA = new com.meizu.cloud.download.c.b(AppStructItem.class);
    @com.meizu.cloud.download.c.a.a(a = "category_name", e = true)
    @com.google.gson.a.a
    public String category_name = "";
    public int click_hor_pos;
    public int click_pos;
    public String cpd_recommend;
    @com.meizu.cloud.download.c.a.a(a = "download_count", e = true)
    @com.google.gson.a.a
    public long download_count;
    @NotJsonColumn
    public int download_status = -1;
    @com.meizu.cloud.download.c.a.a(a = "evaluate_count", e = true)
    @com.google.gson.a.a
    public int evaluate_count;
    public boolean favorited = false;
    @com.meizu.cloud.download.c.a.a(a = "icon", e = true)
    @com.google.gson.a.a
    public String icon = "";
    @com.meizu.cloud.download.c.a.a(a = "id", e = true)
    @com.google.gson.a.a
    public int id;
    public ArrayList<PreviewImage> images = null;
    public int indentifier;
    public String install_page;
    public long install_time;
    public boolean is_Plugin_CPD = false;
    public boolean is_cpd_exposured;
    public boolean is_exposured;
    public int is_first;
    public boolean is_fromPlugin = false;
    @NotJsonColumn
    public String jump;
    public String kw;
    public long kw_id;
    @com.meizu.cloud.download.c.a.a(a = "check_digest", e = true)
    @com.google.gson.a.a
    private String mCheckDigest;
    @com.meizu.cloud.download.c.a.a(a = "check_packagename")
    @com.google.gson.a.a
    private String mCheckPackageName;
    @com.meizu.cloud.download.c.a.a(a = "check_size")
    @com.google.gson.a.a
    private long mCheckSize;
    @com.meizu.cloud.download.c.a.a(a = "check_url")
    @com.google.gson.a.a
    private String mCheckUrl = "";
    private List<String> mCheckUrls;
    @com.meizu.cloud.download.c.a.a(a = "check_verify_mode")
    @com.google.gson.a.a
    private int mCheckVerifyMode;
    @com.meizu.cloud.download.c.a.a(a = "check_versionCode")
    @com.google.gson.a.a
    private int mCheckVersionCode;
    public TrackAdInfo mTrackAdInfo;
    public String md5 = "";
    public int official;
    @com.meizu.cloud.download.c.a.a(a = "package_name", e = true)
    @com.google.gson.a.a
    public String package_name = "";
    public int[] page_info;
    public boolean paid = false;
    public int position_id;
    @com.meizu.cloud.download.c.a.a(a = "price", e = true)
    @com.google.gson.a.a
    public double price;
    @com.meizu.cloud.download.c.a.a(a = "publisher", e = true)
    @com.google.gson.a.a
    public String publisher = "";
    public String recommend_desc;
    public String request_id;
    public String search_id;
    @com.meizu.cloud.download.c.a.a(a = "size", e = true)
    @com.google.gson.a.a
    public long size;
    @com.meizu.cloud.download.c.a.a(a = "software_file", e = true)
    @com.google.gson.a.a
    public String software_file = "";
    public int source_expend;
    public String source_page;
    @com.meizu.cloud.download.c.a.a(a = "star", e = true)
    @com.google.gson.a.a
    public int star;
    public String style;
    public int superior;
    public Tags tags;
    public int theme;
    public int tracker_type;
    public int trial_days;
    public int unit_id;
    public int ver_id;
    public String version;
    @com.meizu.cloud.download.c.a.a(a = "version_code", e = true)
    @com.google.gson.a.a
    public int version_code;
    public long version_create_time;
    @com.meizu.cloud.download.c.a.a(a = "version_name", e = true)
    @com.google.gson.a.a
    public String version_name = "";

    public interface Columns extends com.meizu.cloud.app.request.structitem.AbstractStrcutItem.Columns {
        public static final String CATEGORY_NAME = "category_name";
        public static final String CHECK_DIGEST = "check_digest";
        public static final String CHECK_PACKAGENAME = "check_packagename";
        public static final String CHECK_SIZE = "check_size";
        public static final String CHECK_URL = "check_url";
        public static final String CHECK_VERIFY_MODE = "check_verify_mode";
        public static final String CHECK_VERSIONCODE = "check_versionCode";
        public static final String DOWNLOAD_COUNT = "download_count";
        public static final String EVALUATE_COUNT = "evaluate_count";
        public static final String ICON = "icon";
        public static final String ID = "id";
        public static final String INSTALL_STATUS = "install_status";
        public static final String PACKAGE_NAME = "package_name";
        public static final String PRICE = "price";
        public static final String PUBLISHER = "publisher";
        public static final String SIZE = "size";
        public static final String SOFTWARE_FILE = "software_file";
        public static final String STAR = "star";
        public static final String VERSION_CODE = "version_code";
        public static final String VERSION_NAME = "version_name";
    }

    public void setCheckInfo(String digest, int verifyMode, String packageName, long size, int versionCode, List<String> checkUrls) {
        this.mCheckDigest = digest;
        this.mCheckVerifyMode = verifyMode;
        this.mCheckPackageName = packageName;
        this.mCheckSize = size;
        this.mCheckVersionCode = versionCode;
        this.mCheckUrls = checkUrls;
        if (this.mCheckUrls != null && this.mCheckUrls.size() > 0) {
            this.mCheckUrl = "";
            for (String url : this.mCheckUrls) {
                if (!TextUtils.isEmpty(this.mCheckUrl)) {
                    this.mCheckUrl += ",";
                }
                this.mCheckUrl += url;
            }
        }
    }

    public List<String> getCheckUrls() {
        if (this.mCheckUrls == null && !TextUtils.isEmpty(this.mCheckUrl)) {
            String[] urls = this.mCheckUrl.split(",");
            if (urls.length > 0) {
                this.mCheckUrls = new ArrayList();
                for (String url : urls) {
                    this.mCheckUrls.add(url);
                }
            }
        }
        return this.mCheckUrls;
    }

    public DownloadInfo getDownloadInfo() {
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.digest = this.mCheckDigest;
        downloadInfo.download_url = this.software_file;
        downloadInfo.size = this.mCheckSize;
        downloadInfo.package_name = this.mCheckPackageName;
        downloadInfo.verify_mode = this.mCheckVerifyMode;
        downloadInfo.version_code = this.mCheckVersionCode;
        downloadInfo.toDownloadUrlEx(getCheckUrls());
        return downloadInfo;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i;
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.package_name);
        dest.writeString(this.version_name);
        dest.writeInt(this.version_code);
        dest.writeLong(this.size);
        dest.writeString(this.category_name);
        dest.writeString(this.icon);
        dest.writeLong(this.download_count);
        dest.writeInt(this.evaluate_count);
        dest.writeDouble(this.price);
        dest.writeString(this.publisher);
        dest.writeInt(this.star);
        dest.writeString(this.url);
        dest.writeInt(this.ver_id);
        dest.writeString(this.software_file);
        if (this.paid) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeInt(i);
        if (this.favorited) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeInt(i);
        dest.writeLong(this.version_create_time);
        if (this.tags != null) {
            dest.writeInt(1);
            this.tags.writeToParcel(dest, flags);
        } else {
            dest.writeInt(0);
        }
        dest.writeIntArray(this.page_info);
        dest.writeInt(this.block_id);
        dest.writeInt(this.official);
        dest.writeInt(this.superior);
        dest.writeInt(this.is_first);
        dest.writeInt(this.click_pos);
        dest.writeString(this.search_id);
        dest.writeString(this.recommend_desc);
        if (this.images != null) {
            dest.writeInt(this.images.size());
            for (int i2 = 0; i2 < this.images.size(); i2++) {
                ((PreviewImage) this.images.get(i2)).writeToParcel(dest, flags);
            }
            return;
        }
        dest.writeInt(0);
    }

    public void readFromParcel(Parcel src) {
        boolean z;
        boolean z2 = false;
        this.id = src.readInt();
        this.name = src.readString();
        this.package_name = src.readString();
        this.version_name = src.readString();
        this.version_code = src.readInt();
        this.size = src.readLong();
        this.category_name = src.readString();
        this.icon = src.readString();
        this.download_count = src.readLong();
        this.evaluate_count = src.readInt();
        this.price = src.readDouble();
        this.publisher = src.readString();
        this.star = src.readInt();
        this.url = src.readString();
        this.ver_id = src.readInt();
        this.software_file = src.readString();
        if (src.readInt() == 1) {
            z = true;
        } else {
            z = false;
        }
        this.paid = z;
        if (src.readInt() == 1) {
            z2 = true;
        }
        this.favorited = z2;
        this.version_create_time = src.readLong();
        if (src.readInt() == 1) {
            this.tags = new Tags();
            this.tags.readFromParcel(src);
        }
        this.page_info = src.createIntArray();
        this.block_id = src.readInt();
        this.official = src.readInt();
        this.superior = src.readInt();
        this.is_first = src.readInt();
        this.click_pos = src.readInt();
        this.search_id = src.readString();
        this.recommend_desc = src.readString();
        int count = src.readInt();
        if (count != 0) {
            this.images = new ArrayList();
            for (int i = 0; i < count; i++) {
                this.images.add(new PreviewImage(src));
            }
        }
    }

    public AppStructItem(Parcel src) {
        readFromParcel(src);
    }

    public boolean isDownloaded(Context context) {
        if (x.d(context).a(this.package_name, this.version_code) != com.meizu.cloud.app.core.c.UPGRADE) {
            this.download_status = 0;
        } else if (this.download_status < 0) {
            File file = new File(com.meizu.cloud.app.downlad.a.a(this.package_name, this.version_code));
            if (file.exists() && file.isFile()) {
                this.download_status = 1;
            } else {
                this.download_status = 0;
            }
        }
        if (this.download_status > 0) {
            return true;
        }
        return false;
    }

    public boolean isDownloaded(Context context, boolean bFored) {
        if (bFored) {
            this.download_status = -1;
        }
        return isDownloaded(context);
    }

    public void resetDownloadState() {
        this.download_status = 0;
    }

    public boolean isSuperior() {
        return this.superior == 1;
    }

    public boolean isOfficial() {
        return this.official == 1;
    }

    public boolean isDebut() {
        return this.is_first == 1;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof AppStructItem)) {
            return false;
        }
        AppStructItem app = (AppStructItem) o;
        if (this.id == app.id) {
            return true;
        }
        if (this.package_name.equals(app.package_name) && this.version_code == app.version_code) {
            return true;
        }
        return false;
    }
}
