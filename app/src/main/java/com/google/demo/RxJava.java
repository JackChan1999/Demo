package com.google.demo;

import android.app.Activity;
import android.os.Bundle;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/7/10 20:14
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class RxJava extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //创建observable被观察者
        Observable.create(mOnSubscribe).subscribe(mObserver);

        Observable.just("hello","are you ok ?").subscribe(mObserver);

        String[] array = {"hello","java"};
        Observable.from(array).subscribe(subscribe);
        Observable.from(array)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable.from(array).subscribe(mAction1, mThrowable1, mAction0);


        Observable.just(6)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return integer+"";
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscribe.unsubscribe();//销毁
    }

    Observable.OnSubscribe<String> mOnSubscribe = new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("hello");//onNext()可以调用多次
            subscriber.onCompleted();//事件序列结束标记
            //subscriber.onError();事件错误的标记
        }
    };

    Action1 mAction1 = new Action1() {
        @Override
        public void call(Object o) {

        }
    };

    Action1<Throwable> mThrowable1 = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {

        }
    };

    Action0 mAction0 = new Action0() {
        @Override
        public void call() {

        }
    };


    Observer mObserver = new Observer() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(Object o) {

        }
    };

    public Subscriber<String> subscribe = new Subscriber<String>() {
        @Override
        public void onStart() {
            super.onStart();
        }



        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(String s) {

        }
    };
}
