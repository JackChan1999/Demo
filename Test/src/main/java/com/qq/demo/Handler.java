package com.qq.demo;

import android.os.*;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2016
 * Author：   陈冠杰
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChen1999
 * <p/>
 * Project_Name：Demo
 * Package_Name：com.qq.demo
 * Version：1.0
 * time：2016/8/2 16:32
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/
public class Handler {
    private Looper       mLooper;
    private MessageQueue mQueue;

    public Handler() {
        mLooper = Looper.myLooper();
        if (mLooper == null) {
            throw new RuntimeException(
                    "Can't create handler inside thread that has not called Looper.prepare()");
        }
        mQueue = mLooper.mQueue;
    }

    public void handleMessage() {

    }

    public void dispatchMessage(Message msg) {
        handleMessage();
    }

    public boolean sendMessage(Message msg) {
        return sendMessageDelayed(msg, 0)
    }

    public boolean sendEmptyMessage(int what) {
        return sendEmptyMessageDelayed(what, 0);
    }

    public boolean sendEmptyMessageDelayed(int what, long delayMillis) {
        Message msg = Message.obtain();
        msg.what = what;
        return sendMessageDelayed(msg, 0);
    }

    public boolean sendMessageDelayed(Message msg, long delayTime) {
        if (delayTime < 0)
            delayTime = 0;
        return sendMessageAtTime(msg, SystemClock.uptimeMillis() + delayTime);
    }

    public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
        MessageQueue queue = mQueue;
        return enqueueMessage();
    }

    public boolean enqueueMessage(MessageQueue queue, Message msg, long uptimeMillis) {
        msg.target = this;
        return queue.enqueueMessage(msg, uptimeMillis);
    }

    public void post(Runnable r) {
        sendMessageDelayed(getPostMessage(r), 0);
    }

    public static Message getPostMessage(Runnable r) {
        Message m = Message.obtain();
        m.callback = r;
        return m;
    }
}
