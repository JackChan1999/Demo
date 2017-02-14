package com.google.demo3.interface_demo;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/1 18:50
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class ClassMate {

    //定义接口对象
    private  OnTeacherComeListener mTeacherComeListener ;

    public ClassMate(){
        super();
    }

    //暴露接口方式1：构造方法
    public ClassMate(OnTeacherComeListener listener){
        super();
        mTeacherComeListener = listener ;
    }

    //暴露接口方法2：setter方法
    public void serOnTeacherComeListener(OnTeacherComeListener listener){
        mTeacherComeListener = listener ;
    }

    public void doTeacherCome(String name){
        //接口对象invoke接口方法
        mTeacherComeListener.onTeacherCome(name);
    }

    //定义接口和接口方法
    interface OnTeacherComeListener {
        void onTeacherCome(String name);
        void onTeacherCome();
    }
}
