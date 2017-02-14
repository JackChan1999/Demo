package com.google.demo3.observer;

import java.util.LinkedList;
import java.util.List;
import java.util.Observer;


public class Teacher {
	/***
	addObserver(Observer)//添加观察者
	countObservers()//统计观察者数量
	deleteObserver(Observer)//删除观察者
	deleteObservers()//删除所有的观察者
	hasChanged()
	notifyObservers()//通知所有的观察者
	notifyObservers(Object)//通知所有的观察者,带参数
	 */
	public String	message;

	public void pulishMessage(String messsage) {
		this.message = messsage;
		notifyObservers(messsage);
	}

	public interface MyObserver {
		void update(String message);
	}

	List<MyObserver>	observers	= new LinkedList<MyObserver>();

	/**添加观察者*/
	public void addObserver(MyObserver observer) {
		if (observer == null) {
			throw new NullPointerException("observer == null");
		}
		synchronized (this) {
			if (!observers.contains(observer))
				observers.add(observer);
		}
	}

	/**删除观察者*/
	public synchronized void deleteObserver(Observer observer) {
		observers.remove(observer);
	}

	/**通知观察者数据改变*/
	public void notifyObservers(String message) {
		for (MyObserver observer : observers) {
			observer.update(message);
		}
	}
}
