package com.meizu.cloud.download.service;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.meizu.cloud.download.c.a;
import com.meizu.cloud.download.c.a.c;
import com.meizu.cloud.download.c.b;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@c(a = "download_task")
public class DownloadTaskInfo extends a implements Parcelable, Serializable {
    public static final Creator<DownloadTaskInfo> CREATOR = new Creator<DownloadTaskInfo>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }

        public DownloadTaskInfo a(Parcel in) {
            return new DownloadTaskInfo(in);
        }

        public DownloadTaskInfo[] a(int size) {
            return new DownloadTaskInfo[size];
        }
    };
    public static final b a = new b(DownloadTaskInfo.class);
    public static final a.b<DownloadTaskInfo> o = new a.b<DownloadTaskInfo>() {
        public /* synthetic */ a create() {
            return a();
        }

        public DownloadTaskInfo a() {
            return new DownloadTaskInfo();
        }
    };
    public static final long serialVersionUID = -7313235100588025391L;
    @a.a(a = "source_url", e = true)
    public String b;
    @a.a(a = "dest_file", e = true)
    public String c;
    @a.a(a = "file_size")
    public long d;
    @a.a(a = "downloaded_size")
    public long e;
    public int f;
    public int g;
    @a.a(a = "state")
    public int h;
    @a.a(a = "error")
    public int i;
    @a.a(a = "title")
    public String j;
    @a.a(a = "temp_file")
    public String k;
    public com.meizu.cloud.download.a.a<b> l;
    public boolean m = false;
    public boolean n = true;
    @a.a(a = "check_digest")
    private String p;
    @a.a(a = "check_verify_mode")
    private int q;
    @a.a(a = "check_packagename")
    private String r;
    @a.a(a = "check_size")
    private long s;
    @a.a(a = "check_versionCode")
    private int t;
    @a.a(a = "check_url")
    private String u = "";
    private List<String> v;

    public void a(DownloadTaskInfo checkInfo) {
        this.p = checkInfo.p;
        this.q = checkInfo.q;
        this.r = checkInfo.r;
        this.s = checkInfo.s;
        this.t = checkInfo.t;
        this.v = checkInfo.v;
        this.u = a(this.v);
    }

    public void a(String digest, int verifyMode, String packageName, long size, int versionCode, List<String> checkUrls) {
        this.p = digest;
        this.q = verifyMode;
        this.r = packageName;
        this.s = size;
        this.t = versionCode;
        this.v = checkUrls;
        this.u = a(this.v);
    }

    public String a() {
        return this.p;
    }

    public int b() {
        return this.q;
    }

    public String c() {
        return this.r;
    }

    public long d() {
        return this.s;
    }

    public int e() {
        return this.t;
    }

    public List<String> f() {
        if (this.v == null && !TextUtils.isEmpty(this.u)) {
            String[] urls = this.u.split(",");
            if (urls.length > 0) {
                this.v = new ArrayList();
                for (String url : urls) {
                    this.v.add(url);
                }
            }
        }
        return this.v;
    }

    private String a(List<String> checkUrls) {
        String checkUrl = "";
        if (checkUrls != null && checkUrls.size() > 0) {
            for (String url : checkUrls) {
                if (!TextUtils.isEmpty(checkUrl)) {
                    checkUrl = checkUrl + ",";
                }
                checkUrl = checkUrl + url;
            }
        }
        return checkUrl;
    }

    public DownloadTaskInfo(String sourceUrl, String destFile) {
        this.b = sourceUrl;
        this.c = destFile;
    }

    public DownloadTaskInfo(Parcel parcel) {
        a(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mId);
        dest.writeString(this.b);
        dest.writeString(this.c);
        dest.writeLong(this.d);
        dest.writeLong(this.e);
        dest.writeInt(this.f);
        dest.writeInt(this.g);
        dest.writeInt(this.h);
        dest.writeInt(this.i);
        dest.writeString(this.j);
        dest.writeString(this.p);
        dest.writeInt(this.q);
        dest.writeString(this.r);
        dest.writeLong(this.s);
        dest.writeInt(this.t);
        dest.writeStringList(f());
    }

    public void a(Parcel src) {
        this.mId = src.readLong();
        this.b = src.readString();
        this.c = src.readString();
        this.d = src.readLong();
        this.e = src.readLong();
        this.f = src.readInt();
        this.g = src.readInt();
        this.h = src.readInt();
        this.i = src.readInt();
        this.j = src.readString();
        String mCheckDigest = src.readString();
        int mCheckVerifyMode = src.readInt();
        String mCheckPackageName = src.readString();
        long mCheckSize = src.readLong();
        int mCheckVersionCode = src.readInt();
        List<String> urls = new ArrayList();
        src.readStringList(urls);
        a(mCheckDigest, mCheckVerifyMode, mCheckPackageName, mCheckSize, mCheckVersionCode, urls);
    }
}
