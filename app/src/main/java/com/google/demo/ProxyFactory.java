package com.google.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/7/9 18:29
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class ProxyFactory {
    interface Before{
        void before();
    }

    interface After{
        void after();
    }

    class MyInvocationHandler implements InvocationHandler{

        Object target;

        public MyInvocationHandler(Object target){
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (mBefore != null){
                mBefore.before();
            }
            Object result = method.invoke(target, args);
            if (mAfter != null){
                mAfter.after();
            }
            return result;
        }
    }

    Object target;
    Before mBefore;
    After mAfter;

    public Object create(){
        ClassLoader classLoader = getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        MyInvocationHandler handler = new MyInvocationHandler(target);
        Object obj = Proxy.newProxyInstance(classLoader, interfaces, handler);
        return obj;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setBefore(Before before) {
        mBefore = before;
    }

    public void setAfter(After after) {
        mAfter = after;
    }

    public Object getTarget() {
        return target;
    }

    public Before getBefore() {
        return mBefore;
    }

    public After getAfter() {
        return mAfter;
    }
}
