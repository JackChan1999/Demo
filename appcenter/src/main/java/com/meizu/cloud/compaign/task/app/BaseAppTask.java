package com.meizu.cloud.compaign.task.app;

import com.alibaba.fastjson.TypeReference;
import com.meizu.cloud.app.request.JSONUtils;
import com.meizu.cloud.compaign.task.BaseTask;
import com.meizu.cloud.compaign.task.TaskInfo;

public class BaseAppTask extends BaseTask<BaseAppTaskInfo> {
    public BaseAppTask(long compaignId, long taskId, String taskType, String taskData) {
        super(compaignId, taskId, taskType, taskData);
    }

    public BaseAppTaskInfo parseTaskInfo(String jsonStr) {
        return (BaseAppTaskInfo) JSONUtils.parseJSONObject(getTaskData(), new TypeReference<BaseAppTaskInfo>() {
        });
    }

    public int getAppId() {
        BaseAppTaskInfo taskInfo = (BaseAppTaskInfo) getTaskInfo();
        if (taskInfo != null) {
            return taskInfo.appId;
        }
        return 0;
    }

    public String getPkgName() {
        BaseAppTaskInfo taskInfo = (BaseAppTaskInfo) getTaskInfo();
        if (taskInfo == null || taskInfo.pkgName == null) {
            return "";
        }
        return taskInfo.pkgName;
    }

    public boolean isSimilar(BaseAppTask task) {
        if (task != null && task.getAppId() == getAppId() && task.getPkgName().equals(getPkgName())) {
            return true;
        }
        return false;
    }

    public boolean isReconizable() {
        TaskInfo taskInfo = (TaskInfo) getTaskInfo();
        return taskInfo != null && taskInfo.isReconizable();
    }
}
