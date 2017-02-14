package com.google.demo3.observer_demo;

import java.util.LinkedList;
import java.util.List;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/1 19:50
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class Teacher {

    public String message;

    public void publishMessage(String message) {
        this.message = message;
        notifyObservers(message);
    }

    public interface MyObserver {
        void update(String message);
    }

    List<MyObserver> observers = new LinkedList<>();

    //添加观察者
    public void addObserver(MyObserver observer) {
        if (observer == null) {
            throw new NullPointerException("observer is null");
        } else
            synchronized (this) {
                if (!observers.contains(observer)) {
                    observers.add(observer);
                }
            }
    }
    //删除观察者

    public synchronized void deleteObserver(MyObserver observer) {
        observers.remove(observer);
    }

    //通知观察者数据改变
    public void notifyObservers(String message) {
        for (MyObserver observer : observers) {
            observer.update(message);
        }
    }


}
