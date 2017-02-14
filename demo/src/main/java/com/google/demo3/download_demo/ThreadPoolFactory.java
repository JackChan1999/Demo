package com.google.demo3.download_demo;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/14 13:13
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class ThreadPoolFactory {

    private static ThreadPoolProxy mNormalProxy ;
    private static ThreadPoolProxy mDownloadProxy ;

    public static ThreadPoolProxy getNormalProxy(){
        if (mNormalProxy == null){
            synchronized (ThreadPoolProxy.class){
                if (mNormalProxy == null){
                    mNormalProxy = new ThreadPoolProxy(5,5,1000);
                }
            }
        }
        return mNormalProxy ;
    }

    public static ThreadPoolProxy getDownloadProxy(){
        if (mDownloadProxy == null){
            synchronized (ThreadPoolProxy.class){
                if (mDownloadProxy == null){
                    mDownloadProxy = new ThreadPoolProxy(3,3,1000);
                }
            }
        }
        return mDownloadProxy ;
    }
}
