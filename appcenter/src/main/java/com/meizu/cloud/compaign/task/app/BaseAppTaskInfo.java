package com.meizu.cloud.compaign.task.app;

import android.text.TextUtils;
import com.meizu.cloud.compaign.task.TaskInfo;

public class BaseAppTaskInfo extends TaskInfo {
    public int appId;
    public String pkgName = "";

    public boolean isReconizable() {
        return this.appId > 0 && !TextUtils.isEmpty(this.pkgName);
    }
}
