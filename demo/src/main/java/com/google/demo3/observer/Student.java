package com.google.demo3.observer;

import com.google.demo3.observer.Teacher.MyObserver;

public class Student implements MyObserver {
	@Override
	public void update(String message) {
		System.out.println(this.getClass().getSimpleName() + "  收到  " + message);
	}

}
