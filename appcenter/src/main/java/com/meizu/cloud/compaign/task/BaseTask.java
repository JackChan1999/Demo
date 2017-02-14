package com.meizu.cloud.compaign.task;

import com.meizu.cloud.app.downlad.f.m;
import meizu.sdk.compaign.b;

public abstract class BaseTask<T> extends b {
    private m mStateListener;
    protected T mTaskInfo = parseTaskInfo(getTaskData());

    public abstract boolean isReconizable();

    public abstract T parseTaskInfo(String str);

    public BaseTask(long compaignId, long taskId, String taskType, String taskData) {
        super(compaignId, taskId, taskType, taskData);
    }

    public T getTaskInfo() {
        return this.mTaskInfo;
    }

    public void setStateListener(m listener) {
        this.mStateListener = listener;
    }

    public m getStateListener() {
        return this.mStateListener;
    }
}
