package com.meizu.cloud.compaign.task;

import com.alibaba.fastjson.TypeReference;
import com.meizu.cloud.app.request.JSONUtils;

public class ShareTask extends BaseTask<ShareTaskInfo> {
    public ShareTask(long compaignId, long taskId, String taskType, String taskData) {
        super(compaignId, taskId, taskType, taskData);
    }

    public ShareTaskInfo parseTaskInfo(String jsonStr) {
        return (ShareTaskInfo) JSONUtils.parseJSONObject(getTaskData(), new TypeReference<ShareTaskInfo>() {
        });
    }

    public boolean isReconizable() {
        ShareTaskInfo taskInfo = (ShareTaskInfo) getTaskInfo();
        return taskInfo != null && taskInfo.isReconizable();
    }
}
