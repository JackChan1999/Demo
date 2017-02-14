package com.qq.demo;

import android.os.Message;
import android.os.MessageQueue;

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
 * time：2016/8/2 14:02
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/
public class Looper {
    static final ThreadLocal<Looper> mThreadLocal = new InheritableThreadLocal<>();
    private        MessageQueue mQueue;
    private        Thread       mCurrentThread;
    private static Looper       sMainLooper;

    private Looper() {
        mQueue = new MessageQueue();
        mCurrentThread = Thread.currentThread();
    }

    public static void prepare() {
        if (mThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        mThreadLocal.set(new Looper());
    }

    public void prepareMainLooper() {
        prepare();
        sMainLooper = myLooper();
    }

    public static void loop() {
        Looper       me    = myLooper();
        MessageQueue queue = me.mQueue;
        for (; ; ) {
            Message msg = queue.next();//block
            msg.target.dispatchMessage(msg);
        }
    }

    public static Looper myLooper() {
        return mThreadLocal.get();
    }
}
