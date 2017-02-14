package com.meizu.cloud.app.request.model;

import android.text.TextUtils;
import android.util.Log;
import com.google.gson.a.a;
import java.util.ArrayList;
import java.util.List;

public class DownloadInfo {
    @a
    public String digest;
    @a
    public String download_url;
    @a
    public List<DownloadUrlEx> download_urls;
    @a
    public String package_name;
    @a
    public long size;
    @a
    public int verify_mode;
    @a
    public int version_code;

    public static class DownloadUrlEx {
        public String source;
        public String url;
        public int weight;
    }

    public List<String> getAllDownloadUrlEx() {
        if (this.download_urls == null || this.download_urls.size() <= 0) {
            return null;
        }
        List<String> arrayList = new ArrayList();
        for (DownloadUrlEx downloadUrlEx : this.download_urls) {
            if (TextUtils.isEmpty(downloadUrlEx.url)) {
                Log.w(toString(), "DownloadUrlEx url is null: " + downloadUrlEx.source);
            } else {
                arrayList.add(downloadUrlEx.url);
            }
        }
        return arrayList;
    }

    public void toDownloadUrlEx(List<String> urls) {
        if (urls != null && urls.size() > 0) {
            this.download_urls = new ArrayList();
            for (String url : urls) {
                DownloadUrlEx downloadUrlEx = new DownloadUrlEx();
                downloadUrlEx.url = url;
                this.download_urls.add(downloadUrlEx);
            }
        }
    }
}
