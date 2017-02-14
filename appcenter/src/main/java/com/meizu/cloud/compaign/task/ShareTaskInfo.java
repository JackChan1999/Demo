package com.meizu.cloud.compaign.task;

import android.text.TextUtils;
import java.util.ArrayList;

public class ShareTaskInfo extends TaskInfo {
    public String content = "";
    public ArrayList<String> imgUrls = new ArrayList();
    public String shareApp = "";
    public String subject = "";

    public String[] getImgUrls() {
        String[] urlArray = new String[this.imgUrls.size()];
        this.imgUrls.toArray(urlArray);
        return urlArray;
    }

    public boolean isReconizable() {
        return !TextUtils.isEmpty(this.shareApp) && (!TextUtils.isEmpty(this.content) || this.imgUrls.size() > 0);
    }
}
