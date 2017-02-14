package com.google.demo3.download_demo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/14 13:05
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class ThreadPoolProxy {

    private int mCorePoolSize;
    private int mMaximumPoolSize;
    private long mKeepAliveTime;

    private ThreadPoolExecutor mExecutor;

    public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        mCorePoolSize = corePoolSize;
        mMaximumPoolSize = maximumPoolSize;
        mKeepAliveTime = keepAliveTime;
    }

    private ThreadPoolExecutor initThreadPoolExecutor() {
        if (mExecutor == null) {
            synchronized (ThreadPoolProxy.class) {
                if (mExecutor == null) {
                    TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                    BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<Runnable>(10);
                    ThreadFactory factory = Executors.defaultThreadFactory();
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
                    mExecutor = new ThreadPoolExecutor(mCorePoolSize, mMaximumPoolSize,
                            mKeepAliveTime, timeUnit, workQueue, factory, handler);
                }
            }
        }
        return mExecutor ;
    }

    public void execute(Runnable task){
        initThreadPoolExecutor();
        mExecutor.execute(task);
    }

    public Future<?> submit(Runnable task){
        initThreadPoolExecutor();
        return mExecutor.submit(task);
    }

    public void remove(Runnable task){
        initThreadPoolExecutor();
        mExecutor.remove(task);
    }
}
