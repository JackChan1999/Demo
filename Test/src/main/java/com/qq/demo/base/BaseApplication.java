package com.qq.demo.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/3/20 17:22
 * 描 述 ：定义一个全局的盒子.里面放置的对象,属性,方法都是全局可以调用
 * 修订历史 ：
 * ============================================================
 **/
public class BaseApplication extends Application {

    private static BaseApplication mApplication ;
    private static Context	mContext;
    private static Handler mHandler;
    private static long		mMainTreadId;

    @Override
    public void onCreate() {// 程序的入口
        super.onCreate();
        // 初始化化一些.常用属性.然后放到盒子里面来
        mApplication = this ;
        mHandler = new Handler();
        // 主线程Id
        mMainTreadId = android.os.Process.myTid();
        mContext = getApplicationContext();

    }

    public static Context getApplication(){
        return mApplication ;
    }

    public static Context getContext() {
        return mContext;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static long getMainTreadId() {
        return mMainTreadId;
    }

    public enum ToastMgr {
        builder;

        private View view;
        private TextView tv;
        private Toast toast;



        /**
         * 显示toast
         * @param content 显示的内容
         * @param duration 持续时间
         */
        public void display(CharSequence content, int duration){
            if (content.length() != 0){
                tv.setText(content);
                toast.setDuration(duration);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }
        }

    }

}

